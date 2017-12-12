package com.tomlocksapps.servicecontroller.example;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tomlocksapps.servicecontroller.example.engine.communication.ServiceCommunicator;
import com.tomlocksapps.servicecontroller.example.services.BackgroundThreadExampleServiceManager;
import com.tomlocksapps.servicecontroller.example.services.MainThreadExampleServiceManager;
import com.tomlocksapps.servicecontroller.example.services.communication.BackgroundThreadServiceCommunication;
import com.tomlocksapps.servicecontroller.example.services.communication.ExampleServiceCommunication;
import com.tomlocksapps.servicecontroller.example.services.communication.MainThreadServiceCommunication;

public class MainActivity extends AppCompatActivity {

    private final ServiceCommunicator serviceCommunicator;
    private TextView resultTv;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;

    public MainActivity() {
        serviceCommunicator = CommonInject.getCommunicationEngine().getServiceCommunication();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        resultTv = findViewById(R.id.textView_computation_result);
        progressBar = findViewById(R.id.progress_indeterminate);
    }

    @Override
    protected void onStart() {
        super.onStart();

        ExampleService.startService(getApplicationContext());
    }

    @Override
    protected void onStop() {
        super.onStop();

        ExampleService.stopService(getApplicationContext());
    }

    public void onUiComputationClick(View view) {
        serviceCommunicator.startService(MainThreadExampleServiceManager.class, new MainThreadServiceCommunication(100, callback));
//        serviceCommunicator.sendMessageToService(new MainThreadServiceCommunication(100, callback));
    }

    public void onBackgroundThreadComputationClick(View view) {
        serviceCommunicator.startService(BackgroundThreadExampleServiceManager.class, new BackgroundThreadServiceCommunication(150, callback));
//        serviceCommunicator.sendMessageToService(new BackgroundThreadServiceCommunication(150, callback));
    }

    private ExampleServiceCommunication.Callback callback = new ExampleServiceCommunication.Callback() {
        @Override
        public void onComputationStarted() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    swipeRefreshLayout.setRefreshing(true);
                }
            });
        }

        @Override
        public void onComputationResult(final int number) {
            runOnUiThread(new Runnable() { // result may come on background thread
                @Override
                public void run() {
                    swipeRefreshLayout.setRefreshing(false);
                    resultTv.setText("Computation result: " + number);
                }
            });
        }
    };

}
