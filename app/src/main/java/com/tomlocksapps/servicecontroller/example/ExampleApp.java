package com.tomlocksapps.servicecontroller.example;

import android.app.Application;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;
import com.tomlocksapps.servicecontroller.bus.engine.BusCommunicationEngine;


/**
 * Created by walczewski on 09.12.2017.
 */

public class ExampleApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        CommonInject.setCommunicationEngine(new BusCommunicationEngine(new Bus(ThreadEnforcer.ANY)));
    }
}
