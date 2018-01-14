package com.tomlocksapps.servicecontroller;


/**
 * Created by walczewski on 05.09.2016.
 */
public interface IServiceAsyncManagerr<K extends IServiceCommunication> {
    void onCreateAsync();
    void onStartAsync(K serviceCommunication);
    void onRestartAsync();
    void onDestroyAsync();

    void onLowMemoryAsync();
}
