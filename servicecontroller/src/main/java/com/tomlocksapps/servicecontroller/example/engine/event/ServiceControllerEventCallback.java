package com.tomlocksapps.servicecontroller.example.engine.event;

import com.tomlocksapps.servicecontroller.example.engine.communication.ServiceCommunicationEvent;
import com.tomlocksapps.servicecontroller.example.engine.communication.ServiceLifecycleEvent;

/**
 * Created by jastrzebski on 07.09.17.
 */

public interface ServiceControllerEventCallback {
    void onNewServiceCommunicationEvent(ServiceCommunicationEvent serviceCommunicationEvent);
    void onNewServiceLifecycleEvent(ServiceLifecycleEvent serviceLifecycleEvent);
}
