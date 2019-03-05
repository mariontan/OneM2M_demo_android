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
    private String[] sensors = new String[]{"GPS","gas","radiation"};
    private String category = "disaster";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disaster);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        String regFlag = sp.getString(getString(R.string.deviceDisasterRegFlag),"");
        final String deviceID = sp.getString(getString(R.string.deviceID),"");
        if(!regFlag.equals("Registered")){
            dataController.categoryRegistration(this,R.string.deviceDisasterRegFlag,deviceID, sp, "disaster", sensors);
        }
        button.initializeDisasterButton(this);
        dataController.buttonFunction(deviceID,category,button.dGPS,sensors[0],sp);
        dataController.buttonFunction(deviceID,category,button.dGas,sensors[1],sp);
        dataController.buttonFunction(deviceID,category,button.dRad,sensors[2],sp);
    }
}


