package com.tomlocksapps.servicecontroller.example.engine.communication;

import com.tomlocksapps.servicecontroller.IServiceCommunication;
import com.tomlocksapps.servicecontroller.manager.AbstractServiceManager;


/**
 * Created by paczos on 20.07.17
 */

public class ServiceLifecycleEvent {
    private final boolean startFlag;
    private final Class<? extends AbstractServiceManager> clazz;
    private final Object[] args;
    private final IServiceCommunication serviceCommunication;
    private final long actionDelay;

    public ServiceLifecycleEvent(boolean startFlag, Class<? extends AbstractServiceManager> clazz, Object[] args, IServiceCommunication serviceCommunication, long actionDelay) {
        this.startFlag = startFlag;
        this.clazz = clazz;
        this.args = args;
        this.serviceCommunication = serviceCommunication;
        this.actionDelay = actionDelay;
    }

    public boolean getStartFlag() {
        return startFlag;
    }

    public Class<? extends AbstractServiceManager> getClazz() {
        return clazz;
    }

    public Object[] getArgs() {
        return args;
    }

    public IServiceCommunication getServiceCommunication() {
        return serviceCommunication;
    }

    public long getActionDelay() {
        return actionDelay;
    }
}
