package com.example.aic.onem2m_demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Disaster extends AppCompatActivity {
    ViewController button = new ViewController();
    DataController dataController = new DataController();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disaster);
        button.initializeDisasterButton(this);
        button.dGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataController.sendToServer("/server/mydevice9/led",4,"{\"m2m:cin\":{\"con\":\"OFF\"}}");
            }
        });
    }
}
