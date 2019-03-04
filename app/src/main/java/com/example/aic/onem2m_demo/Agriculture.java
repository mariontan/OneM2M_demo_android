package com.example.aic.onem2m_demo;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class Agriculture extends AppCompatActivity {

    private ViewController button = new ViewController();
    private DataController dataController = new DataController();
    private SharedPreferences sp;
    private String[] sensors = new String[]{"moisture","gas","humidity","temp"};
    private String category = "agriculture";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agriculture);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        String regFlag = sp.getString(getString(R.string.deviceAgriRegFlag),"");
        final String deviceID = sp.getString(getString(R.string.deviceID),"");
        if(!regFlag.equals("Registered")){
            dataController.categoryRegistration(this,R.string.deviceAgriRegFlag, deviceID, sp,category, sensors);
        }
        button.intializeAgriButton(this);
        dataController.buttonFunction(deviceID,category,button.agrMoist,sensors[0]);
        dataController.buttonFunction(deviceID,category,button.agrGas,sensors[1]);
        dataController.buttonFunction(deviceID,category,button.agrHum,sensors[2]);
        dataController.buttonFunction(deviceID,category,button.agrTemp,sensors[3]);
    }
}
