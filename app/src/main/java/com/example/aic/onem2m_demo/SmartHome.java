package com.example.aic.onem2m_demo;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.Random;

public class SmartHome extends AppCompatActivity {

    private ViewController button = new ViewController();
    private DataController dataController = new DataController();
    private SharedPreferences sp;
    private String[] sensors = new String[]{"temp","humidity"};
    private String category = "smarthome";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_home);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        String regFlag = sp.getString(getString(R.string.deviceSmrtRegFlag),"");
        final String deviceID = sp.getString(getString(R.string.deviceID),"");
        if(!regFlag.equals("Registered")){
            dataController.categoryRegistration(this,R.string.deviceSmrtRegFlag, deviceID,sp, category, sensors);
        }
        button.initializeSmrtButton(this);
        dataController.buttonFunction(deviceID,category,button.smrtTemp,sensors[0]);
        dataController.buttonFunction(deviceID,category,button.smrtHum,sensors[1]);
    }

    /*private void categoryRegistration(String deviceID){
        SharedPreferences.Editor editor = sp.edit();
        String msg = dataController.sendToServer("/server/"+deviceID,3,"{\"m2m:cnt\":{\"rn\":\"smarthome\"}}","Cae_device"+deviceID);
        if(msg.equals("Created")){
            dataController.sendToServer("/server/"+deviceID+"/smarthome",3,"{\"m2m:cnt\":{\"rn\":\"temp\"}}","Cae_device"+deviceID);
            editor.putString(getString(R.string.deviceSmrtRegFlag),"Registered");
            editor.commit();
        }
    }*/
}
