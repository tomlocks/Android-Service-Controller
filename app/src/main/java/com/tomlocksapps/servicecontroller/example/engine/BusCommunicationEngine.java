package com.tomlocksapps.servicecontroller.example.engine;

import com.squareup.otto.Bus;
import com.tomlocksapps.servicecontroller.example.engine.communication.ServiceCommunicator;
import com.tomlocksapps.servicecontroller.example.engine.event.ServiceEventWrapper;

/**
 * Created by walczewski on 09.12.2017.
 */

public class BusCommunicationEngine extends CommunicationEngine<Bus> {

    public BusCommunicationEngine(Bus object) {
        super(object);
    }

    @Override
    protected ServiceCommunicator provideServiceCommunication(Bus object) {
        return new BusServiceCommunicator(object);
    }

    @Override
    protected ServiceEventWrapper provideServiceEventWrapper(Bus object) {
        return new BusServiceEventWrapper(object);
    }
}
