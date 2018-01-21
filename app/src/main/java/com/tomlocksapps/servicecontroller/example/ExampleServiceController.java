package com.tomlocksapps.servicecontroller.example;

import android.content.Context;

import com.tomlocksapps.servicecontroller.IServiceCommunication;
import com.tomlocksapps.servicecontroller.IServiceManager;
import com.tomlocksapps.servicecontroller.ServiceController;
import com.tomlocksapps.servicecontroller.example.engine.CommunicationEngine;
import com.tomlocksapps.servicecontroller.example.services.ExampleAppStartServiceManager;
import com.tomlocksapps.servicecontroller.example.services.RepetitiveTaskServiceManager;
import com.tomlocksapps.servicecontroller.example.services.BackgroundThreadExampleServiceManager;
import com.tomlocksapps.servicecontroller.example.services.MainThreadExampleServiceManager;

import java.util.Map;
import java.util.Set;

/**
 * Created by walczewski on 09.12.2017.
 */

public class ExampleServiceController extends ServiceController {

    public ExampleServiceController(CommunicationEngine engine, Context context) {
        super(engine);

        addService(new RepetitiveTaskServiceManager(context));
        addService(new ExampleAppStartServiceManager(context));
    }

    @Override
    protected void fillOnAppStartActionsMap(Map<Class<? extends IServiceManager>, IServiceCommunication> onAppStartActionsMap) {
        super.fillOnAppStartActionsMap(onAppStartActionsMap);

        onAppStartActionsMap.put(ExampleAppStartServiceManager.class, new ExampleAppStartServiceManager.AppStartServiceCommunicator());
    }
}
