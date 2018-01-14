package com.tomlocksapps.servicecontroller.manager;

import android.os.Looper;

/**
 * Created by turowski on 20.10.17.
 */

public interface IHandler {

    boolean postDelayed(Runnable r, long delay);

    boolean post(Runnable r);

    void run(Runnable runnable);

    void removeCallbacks(Runnable r);

    void removeCallbacksAndMessages(Object token);

    Looper getLooper();
}
