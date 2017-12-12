package com.tomlocksapps.servicecontroller.example.services;

import android.os.Process;

import com.tomlocksapps.servicecontroller.example.services.communication.BackgroundThreadServiceCommunication;

/**
 * Created by walczewski on 09.12.2017.
 */

public class BackgroundThreadExampleServiceManager extends ExampleServiceManager<BackgroundThreadServiceCommunication> {

    @Override
    protected String provideUniqueServiceTag() {
        return "BackgroundThreadExampleServiceManager";
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
