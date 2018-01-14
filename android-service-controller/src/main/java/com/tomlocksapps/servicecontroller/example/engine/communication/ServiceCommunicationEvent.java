package com.tomlocksapps.servicecontroller.example.engine.communication;

import com.tomlocksapps.servicecontroller.IServiceCommunication;

/**
 * Created by paczos on 03.07.17
 */

public class ServiceCommunicationEvent {
    private final IServiceCommunication serviceCommunication;
    private final long delay;

    public ServiceCommunicationEvent(IServiceCommunication serviceCommunication, long delay) {
        this.serviceCommunication = serviceCommunication;
        this.delay = delay;
    }

    public IServiceCommunication getServiceCommunication() {
        return serviceCommunication;
    }

    public long getDelay() {
        return delay;
    }
}
