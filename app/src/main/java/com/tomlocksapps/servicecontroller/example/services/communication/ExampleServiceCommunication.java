package com.tomlocksapps.servicecontroller.example.services.communication;

import com.tomlocksapps.servicecontroller.IServiceCommunication;

import java.lang.ref.WeakReference;

/**
 * Created by walczewski on 09.12.2017.
 */

public abstract class ExampleServiceCommunication implements IServiceCommunication {
    private final int number;
    private final WeakReference<Callback> callback;

    public ExampleServiceCommunication(int number, Callback callback) {
        this.number = number;
        this.callback = new WeakReference<>(callback);
    }

    public Callback getCallback() {
        return callback.get();
    }

    public int getNumber() {
        return number;
    }

    public interface Callback {
        void onComputationStarted();
        void onComputationResult(int number);
    }
}
