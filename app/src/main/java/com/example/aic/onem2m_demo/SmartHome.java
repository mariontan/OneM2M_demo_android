package com.example.aic.onem2m_demo;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SmartHome extends AppCompatActivity {

    private ViewController button = new ViewController();
    private DataController dataController = new DataController();
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_home);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        String regFlag = sp.getString(getString(R.string.deviceSmrtRegFlag),"");
        final String deviceID = sp.getString(getString(R.string.deviceID),"");
        if(!regFlag.equals("Registered")){
            categoryRegistration(deviceID);
        }
        button.initializeSmrtButton(this);
        button.smrtTemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataController.sendToServer("/server/"+deviceID+"/smarthome/temp",4,"{\"m2m:cin\":{\"con\":\"OFF\"}}","Cae_device"+deviceID);
            }
        });
    }

    private void categoryRegistration(String deviceID){
        SharedPreferences.Editor editor = sp.edit();
        String msg = dataController.sendToServer("/server/"+deviceID,3,"{\"m2m:cnt\":{\"rn\":\"smarthome\"}}","Cae_device"+deviceID);
        if(msg.equals("Created")){
            dataController.sendToServer("/server/"+deviceID+"/smarthome",3,"{\"m2m:cnt\":{\"rn\":\"temp\"}}","Cae_device"+deviceID);
            editor.putString(getString(R.string.deviceSmrtRegFlag),"Registered");
            editor.commit();
        }
    }
}
