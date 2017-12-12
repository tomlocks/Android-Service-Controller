package com.tomlocksapps.servicecontroller.example.services;

import android.os.Looper;
import android.util.Log;

import com.tomlocksapps.servicecontroller.manager.AbstractServiceManager;
import com.tomlocksapps.servicecontroller.example.services.communication.ExampleServiceCommunication;

import java.util.Random;

/**
 * Created by walczewski on 09.12.2017.
 */

public abstract class ExampleServiceManager<T extends ExampleServiceCommunication> extends AbstractServiceManager<T> {

    @Override
    public void onCreateAsync() {
        super.onCreateAsync();

        Log.d(uniqueServiceTag, "onCreateAsync - isMainThread: " + isMainThread());
    }

    @Override
    public void onStartAsync(T serviceCommunication) {
        super.onStartAsync(serviceCommunication);

        Log.d(uniqueServiceTag, "onStartAsync - isMainThread: " + isMainThread());

        doHeavyStuff(serviceCommunication.getNumber(), serviceCommunication.getCallback());
    }

    @Override
    public void onDestroyAsync() {
        super.onDestroyAsync();

        Log.d(uniqueServiceTag, "onDestroyAsync - isMainThread: " + isMainThread());
    }

    private boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }

    private void doHeavyStuff(int number, ExampleServiceCommunication.Callback callback) {
        if(callback != null)
            callback.onComputationStarted();

        //do sth heavy here
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(callback != null)
            callback.onComputationResult((new Random()).nextInt());

        stopSelf();
    }

}
