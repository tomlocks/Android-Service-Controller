package com.tomlocksapps.servicecontroller.example.services.communication;

import com.tomlocksapps.servicecontroller.IServiceCommunication;

/**
 * Created by walczewski on 09.12.2017.
 */

public class RepetitiveTaskServiceCommunication implements IServiceCommunication {

    private final long timestamp;

    public RepetitiveTaskServiceCommunication(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
