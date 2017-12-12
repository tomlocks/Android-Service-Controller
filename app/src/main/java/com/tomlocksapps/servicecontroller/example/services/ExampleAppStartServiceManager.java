package com.tomlocksapps.servicecontroller.example.services;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.tomlocksapps.servicecontroller.IServiceCommunication;
import com.tomlocksapps.servicecontroller.manager.AbstractServiceManager;

/**
 * Created by walczewski on 12.12.2017.
 */

public class ExampleAppStartServiceManager extends AbstractServiceManager<ExampleAppStartServiceManager.AppStartServiceCommunicator> {

    private Handler handler = new Handler(Looper.getMainLooper());
    private final Context context;

    public ExampleAppStartServiceManager(Context context) {
        this.context = context;
    }

    @Override
    protected boolean shouldRunOnUiThread() {
        return false;
    }

    @Override
    public void onCreateAsync() {
        super.onCreateAsync();

        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, "On app start service started", Toast.LENGTH_SHORT).show();
            }
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        stopSelf();

    }

    @Override
    public void onDestroyAsync() {
        super.onDestroyAsync();

        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, "On app start service stopped", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected String provideUniqueServiceTag() {
        return "ExampleAppStartServiceManager";
    }

    public static class AppStartServiceCommunicator implements IServiceCommunication {}
}
