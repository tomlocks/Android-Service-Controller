package com.tomlocksapps.servicecontroller.example.engine;

import com.tomlocksapps.servicecontroller.example.engine.communication.ServiceCommunicator;
import com.tomlocksapps.servicecontroller.example.engine.event.ServiceEventWrapper;

/**
 * Created by walczewski on 09.12.2017.
 */

public abstract class CommunicationEngine<T> {

    private final ServiceCommunicator serviceCommunication;
    private final ServiceEventWrapper serviceEventWrapper;

    public CommunicationEngine(T object) {
        serviceEventWrapper = provideServiceEventWrapper(object);
        serviceCommunication = provideServiceCommunication(object);
    }

    protected abstract ServiceCommunicator provideServiceCommunication(T object);

    protected abstract ServiceEventWrapper provideServiceEventWrapper(T object);

    public ServiceCommunicator getServiceCommunication() {
        return serviceCommunication;
    }

    public ServiceEventWrapper getServiceEventWrapper() {
        return serviceEventWrapper;
    }
}
