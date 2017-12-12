package com.tomlocksapps.servicecontroller.example;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.tomlocksapps.servicecontroller.ServiceController;

public class ExampleService extends Service {

    private ServiceController serviceController;

    public ExampleService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        serviceController = new ExampleServiceController(CommonInject.getCommunicationEngine(), getApplicationContext());
        serviceController.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent == null)
            serviceController.onRestart();

        return START_STICKY;
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);

        if(level == TRIM_MEMORY_RUNNING_MODERATE)
            serviceController.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        serviceController.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static void startService(Context context) {
        Intent i = new Intent(context, ExampleService.class);
        context.startService(i);
    }

    public static void stopService(Context context) {
        Intent i = new Intent(context, ExampleService.class);
        context.stopService(i);
    }

}
