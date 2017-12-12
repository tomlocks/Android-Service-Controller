package com.tomlocksapps.servicecontroller.example.engine;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.tomlocksapps.servicecontroller.example.engine.communication.ServiceCommunicationEvent;
import com.tomlocksapps.servicecontroller.example.engine.communication.ServiceLifecycleEvent;
import com.tomlocksapps.servicecontroller.example.engine.event.ServiceEventWrapper;

/**
 * Created by walczewski on 09.12.2017.
 */

public class BusServiceEventWrapper extends ServiceEventWrapper {

    private final Bus bus;

    public BusServiceEventWrapper(Bus bus) {
        this.bus = bus;
    }

    @Override
    public void initialize() {
        super.initialize();
        bus.register(this);
    }

    @Override
    public void uninitialize() {
        super.uninitialize();
        bus.unregister(this);
    }


    @Subscribe
    public void onNewServiceCommunicationEvent(final ServiceCommunicationEvent serviceCommunicationEvent) {
        getCallback().onNewServiceCommunicationEvent(serviceCommunicationEvent);
    }

    @Subscribe
    public void onNewServiceLifecycleEvent(final ServiceLifecycleEvent serviceLifecycleEvent) {
        getCallback().onNewServiceLifecycleEvent(serviceLifecycleEvent);
    }

}
