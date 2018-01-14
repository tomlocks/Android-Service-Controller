package com.tomlocksapps.servicecontroller;

import android.os.Bundle;

import com.tomlocksapps.servicecontroller.example.engine.event.ServiceControllerEventCallback;
import com.tomlocksapps.servicecontroller.manager.AbstractServiceManager;

/**
 * Created by walczewski on 05.09.2016.
 */
public interface IServiceManager<K extends IServiceCommunication> {
    void onCreate();
    void onCreate(long delay);
    void onStart(K serviceCommunication, long delay);
    void onRestart();
    void onDestroy();

    void onLowMemory();

    void onNewExtras(Bundle extras);

    long getServiceCreateDelay();

    void onTrimMemory(int level);
    void setServiceControllerCallback(ServiceManagerCallback callback);

    interface ServiceManagerCallback {
        void stopSelf(Class<? extends AbstractServiceManager> aClazz);
    }
}
