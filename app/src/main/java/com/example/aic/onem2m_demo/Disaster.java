package com.example.aic.onem2m_demo;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Disaster extends AppCompatActivity {

    private ViewController button = new ViewController();
    private DataController dataController = new DataController();
    private SharedPreferences sp;
    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disaster);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        final String deviceID = sp.getString(getString(R.string.deviceID),"");
        //register catergory under device
        categoryRegistration(deviceID);
        button.initializeDisasterButton(this);
        button.dGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataController.sendToServer("/server/"+deviceID+"/disaster/sensor1",4,"{\"m2m:cin\":{\"con\":\"OFF\"}}","Cae_device"+deviceID);
            }
        });
    }

    private void categoryRegistration(String deviceID){
        String msg = dataController.sendToServer("/server/"+deviceID,3,"{\"m2m:cnt\":{\"rn\":\"disaster\"}}","Cae_device"+deviceID);
        if(msg.equals("Created")){
            dataController.sendToServer("/server/"+deviceID+"/disaster",3,"{\"m2m:cnt\":{\"rn\":\"sensor1\"}}","Cae_device"+deviceID);
        }
    }
}
