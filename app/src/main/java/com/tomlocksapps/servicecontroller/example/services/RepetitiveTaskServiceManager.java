package com.tomlocksapps.servicecontroller.example.services;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.widget.Toast;

import com.tomlocksapps.servicecontroller.example.services.communication.RepetitiveTaskServiceCommunication;
import com.tomlocksapps.servicecontroller.manager.AbstractServiceManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by walczewski on 09.12.2017.
 */

public class RepetitiveTaskServiceManager extends AbstractServiceManager<RepetitiveTaskServiceCommunication> {

    private static final long DELAY = TimeUnit.SECONDS.toMillis(10);
    private final Context context;

    private Handler handler = new Handler(Looper.getMainLooper());

    private long timestamp = System.currentTimeMillis();

    public RepetitiveTaskServiceManager(Context context) {
        this.context = context;
    }

    @Override
    protected String provideUniqueServiceTag() {
        return "AllTimeExampleServiceManager";
    }

    @Override
    public void onCreateAsync() {
        super.onCreateAsync();

        repetitiveTask.run();
    }

    @Override
    public void onStartAsync(RepetitiveTaskServiceCommunication serviceCommunication) {
        super.onStartAsync(serviceCommunication);

        this.timestamp = serviceCommunication.getTimestamp();

        repetitiveTask.run();
    }

    private final Runnable repetitiveTask = new Runnable() {
        @Override
        public void run() {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

                    Toast.makeText(context, "Repetitive task fired - " + sdf.format(new Date(timestamp)), Toast.LENGTH_SHORT).show();
                }
            });

            getHandler().postDelayed(repetitiveTask, DELAY);
        }
    };

    @Override
    public void onDestroyAsync() {
        super.onDestroyAsync();

        handler.removeCallbacksAndMessages(null);
    }

    @Override
    protected long provideServiceCreateDelay() {
        return TimeUnit.SECONDS.toMillis(8);
    }

    @Override
    protected boolean shouldRunOnUiThread() {
        return false;
    }

    @Override
    protected int provideThreadPriority() {
        return Process.THREAD_PRIORITY_LOWEST;
    }


}
