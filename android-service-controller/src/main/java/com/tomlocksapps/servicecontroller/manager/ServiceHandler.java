package com.tomlocksapps.servicecontroller.manager;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by paczos on 14.09.17
 */

public class ServiceHandler extends Handler implements IHandler{

    public ServiceHandler() {
        super();
    }

    public ServiceHandler(Callback callback) {
        super(callback);
    }

    public ServiceHandler(Looper looper) {
        super(looper);
    }

    public ServiceHandler(Looper looper, Callback callback) {
        super(looper, callback);
    }

    @Override
    public void run(Runnable runnable) {
        if (getLooper() == this.getLooper())
            runnable.run();
        else
            this.post(runnable);
    }

}