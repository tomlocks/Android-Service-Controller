package com.tomlocksapps.servicecontroller.manager;

import android.os.Build;
import android.os.Bundle;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Process;

import com.tomlocksapps.servicecontroller.IServiceAsyncManagerr;
import com.tomlocksapps.servicecontroller.IServiceCommunication;
import com.tomlocksapps.servicecontroller.IServiceManager;

import java.lang.reflect.ParameterizedType;


/**
 * Created by walczewski on 05.09.2016.
 */
public abstract class AbstractServiceManager<K extends IServiceCommunication> implements IServiceManager<K>, IServiceAsyncManagerr<K> {

    private static final String TAG = "AbstractServiceManager";

    private Class<K> communicationClass;
    protected IHandler handler;

    protected final String uniqueServiceTag;
    private final int threadPriority;

    private final long serviceCreateDelay;

    private State state = State.DESTROYED;
    private ServiceManagerCallback callback;

    public AbstractServiceManager() {
        if (!(getClass().getGenericSuperclass().equals(AbstractServiceManager.class))) { // gdy  jest generics
            this.communicationClass = (Class<K>) ((ParameterizedType) getClass()
                    .getGenericSuperclass()).getActualTypeArguments()[0];
        }

        uniqueServiceTag = provideUniqueServiceTag();
        threadPriority = provideThreadPriority();
        serviceCreateDelay = provideServiceCreateDelay();

    }

    @Override
    public final void onCreate() {
        onCreate(serviceCreateDelay);
    }

    @Override
    public final void onCreate(long delay) {
        handler = provideHandler();

        if (delay == 0) {
            handler.run(new Runnable() {
                @Override
                public void run() {
                    state = State.CREATED;
                    onCreateAsync();
                }
            });
        } else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    state = State.CREATED;
                    onCreateAsync();
                }
            }, delay);
        }
    }

    @Override
    public final void onStart(final K serviceCommunication, long delay) {
        if (serviceCommunication.getClass().equals(communicationClass)) {
            long startDelay = 0;

            if (state == State.DESTROYED) {
                startDelay = serviceCreateDelay;
            }

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (state == State.DESTROYED)
                        return;

                    state = State.STARTED;
                    onStartAsync(serviceCommunication);
                }
            }, startDelay + delay);
        }
    }

    @Override
    public final void onRestart() {
        long startDelay = 0;

        if (state == State.DESTROYED) {
            startDelay = serviceCreateDelay;
        }

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                state = State.RESTARTED;
                onRestartAsync();
            }
        }, startDelay);
    }

    @Override
    public final void onDestroy() {
        handler.removeCallbacksAndMessages(null);

        if (state != State.DESTROYED) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    state = State.DESTROYED;
                    onDestroyAsync();
                    killHandlerThread();
                }
            });
        }
    }

    private void killHandlerThread() {
        Looper looper = handler.getLooper();
        if (looper == Looper.getMainLooper())
            return;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            if (looper != null)
                looper.quitSafely();
        } else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Looper looper = handler.getLooper();
                    if (looper != null)
                        looper.quit();
                }
            }, 1000);
        }
    }

    @Override
    public final void onLowMemory() {
        long startDelay = 0;

        if (state == State.DESTROYED) {
            startDelay = serviceCreateDelay;
        }

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                onLowMemoryAsync();
            }
        }, startDelay);
    }

    @Override
    public void onNewExtras(Bundle extras) {

    }

    @Override
    public void onCreateAsync() {

    }

    @Override
    public void onStartAsync(K serviceCommunication) {

    }

    @Override
    public void onRestartAsync() {

    }

    @Override
    public void onDestroyAsync() {

    }

    @Override
    public void onLowMemoryAsync() {

    }

    protected abstract String provideUniqueServiceTag();

    private ServiceHandler provideHandler() {
        if (shouldRunOnUiThread()) {
            return new ServiceHandler(Looper.getMainLooper());
        } else {
            HandlerThread handlerThread = new HandlerThread(uniqueServiceTag, threadPriority);
            handlerThread.start();
            return new ServiceHandler(handlerThread.getLooper());
        }
    }

    protected boolean shouldRunOnUiThread() {
        return true;
    }

    protected int provideThreadPriority() {
        return Process.THREAD_PRIORITY_DEFAULT;
    }

    protected long provideServiceCreateDelay() {
        return 100;
    }

    @Override
    public long getServiceCreateDelay() {
        return serviceCreateDelay;
    }

    public enum State {
        CREATED, STARTED, RESTARTED, DESTROYED;
    }

    public IHandler getHandler() {
        return handler;
    }

    protected void stopSelf() {
        callback.stopSelf(getClass());
    }

    @Override
    public void onTrimMemory(int level) {

    }

    @Override
    public void setServiceControllerCallback(ServiceManagerCallback callback) {
        this.callback = callback;
    }
}
