package com.tomlocksapps.servicecontroller.example.engine.communication;

import com.tomlocksapps.servicecontroller.IServiceCommunication;
import com.tomlocksapps.servicecontroller.manager.AbstractServiceManager;


/**
 * Created by walczewski on 06.09.2016.
 */
public interface ServiceCommunicator {
    void sendMessageToService(IServiceCommunication serviceCommunication);

    void sendMessageToService(IServiceCommunication serviceCommunication, long delay);

    void startService(Class<? extends AbstractServiceManager> clazz, IServiceCommunication serviceCommunication);

    void startService(Class<? extends AbstractServiceManager> clazz, Object[] args);

    void startService(Class<? extends AbstractServiceManager> clazz, Object[] args, IServiceCommunication serviceCommunication);

    void startService(Class<? extends AbstractServiceManager> clazz, Object[] args, IServiceCommunication serviceCommunication, long actionDelay);

    void stopService(Class<? extends AbstractServiceManager> clazz);
}
