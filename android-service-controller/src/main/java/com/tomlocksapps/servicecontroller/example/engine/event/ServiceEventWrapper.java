package com.tomlocksapps.servicecontroller.example.engine.event;

/**
 * Created by walczewski on 09.12.2017.
 */

public class ServiceEventWrapper {

    private ServiceControllerEventCallback callback;

    public void setCallback(ServiceControllerEventCallback callback) {
        this.callback = callback;
    }

    public void initialize() {}

    public void uninitialize() {}

    protected ServiceControllerEventCallback getCallback() {
        return callback;
    }
}
