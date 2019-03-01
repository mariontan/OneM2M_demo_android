package com.example.aic.onem2m_demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Disaster extends AppCompatActivity {

    private ViewController button = new ViewController();
    private DataController dataController = new DataController();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disaster);

        //register catergory under device
        categoryRegistration();
        button.initializeDisasterButton(this);
        button.dGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataController.sendToServer("/server/mydevice11/disaster/sensor1",4,"{\"m2m:cin\":{\"con\":\"OFF\"}}","Cae_device11");
            }
        });
    }

    private void categoryRegistration(){
        String msg = dataController.sendToServer("/server/mydevice11",3,"{\"m2m:cnt\":{\"rn\":\"disaster\"}}","Cae_device11");
        if(msg.equals("Created")){
            dataController.sendToServer("/server/mydevice11/disaster",3,"{\"m2m:cnt\":{\"rn\":\"sensor1\"}}","Cae_device11");
        }
    }
}
