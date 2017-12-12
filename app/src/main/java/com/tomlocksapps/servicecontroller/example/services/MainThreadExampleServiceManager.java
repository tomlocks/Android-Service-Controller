package com.tomlocksapps.servicecontroller.example.services;

import com.tomlocksapps.servicecontroller.example.services.communication.MainThreadServiceCommunication;

/**
 * Created by walczewski on 09.12.2017.
 */

public class MainThreadExampleServiceManager extends ExampleServiceManager<MainThreadServiceCommunication> {

    @Override
    protected String provideUniqueServiceTag() {
        return "MainThreadExampleServiceManager";
    }

}
