package com.tomlocksapps.servicecontroller.example.engine;

import com.squareup.otto.Bus;
import com.tomlocksapps.servicecontroller.IServiceCommunication;
import com.tomlocksapps.servicecontroller.example.engine.communication.ServiceCommunicationEvent;
import com.tomlocksapps.servicecontroller.example.engine.communication.ServiceCommunicator;
import com.tomlocksapps.servicecontroller.example.engine.communication.ServiceLifecycleEvent;
import com.tomlocksapps.servicecontroller.manager.AbstractServiceManager;

/**
 * Created by walczewski on 09.12.2017.
 */

public class BusServiceCommunicator implements ServiceCommunicator {

    private final Bus bus;

    public BusServiceCommunicator(Bus bus) {
        this.bus = bus;
    }

    @Override
    public void sendMessageToService(IServiceCommunication serviceCommunication) {
        bus.post(new ServiceCommunicationEvent(serviceCommunication, 0));
    }

    @Override
    public void sendMessageToService(IServiceCommunication serviceCommunication, long delay) {
        bus.post(new ServiceCommunicationEvent(serviceCommunication, delay));
    }

    @Override
    public void startService(Class<? extends AbstractServiceManager> clazz, IServiceCommunication serviceCommunication) {
        bus.post(new ServiceLifecycleEvent(true, clazz, new Object[]{}, serviceCommunication, 0));
    }

    @Override
    public void startService(Class<? extends AbstractServiceManager> clazz, Object[] args) {
        bus.post(new ServiceLifecycleEvent(true, clazz, args, null, 0));
    }

    @Override
    public void startService(Class<? extends AbstractServiceManager> clazz, Object[] args, IServiceCommunication serviceCommunication) {
        bus.post(new ServiceLifecycleEvent(true, clazz, args, serviceCommunication, 0));
    }

    @Override
    public void startService(Class<? extends AbstractServiceManager> clazz, Object[] args, IServiceCommunication serviceCommunication, long actionDelay) {
        bus.post(new ServiceLifecycleEvent(true, clazz, args, serviceCommunication, actionDelay));
    }

    @Override
    public void stopService(Class<? extends AbstractServiceManager> clazz) {
        bus.post(new ServiceLifecycleEvent(false, clazz, new Object[]{}, null, 0));
    }
}
