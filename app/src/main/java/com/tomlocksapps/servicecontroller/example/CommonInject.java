package com.tomlocksapps.servicecontroller.example;

import com.tomlocksapps.servicecontroller.example.engine.CommunicationEngine;

/**
 * Created by walczewski on 09.12.2017.
 */

public class CommonInject {
    private static CommunicationEngine communicationEngine;

    public static void setCommunicationEngine(CommunicationEngine communicationEngine) {
        CommonInject.communicationEngine = communicationEngine;
    }

    public static CommunicationEngine getCommunicationEngine() {
        return communicationEngine;
    }
}