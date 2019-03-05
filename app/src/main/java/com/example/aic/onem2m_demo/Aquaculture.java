package com.example.aic.onem2m_demo;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Aquaculture extends AppCompatActivity {
    private ViewController button = new ViewController();
    private DataController dataController = new DataController();
    private SharedPreferences sp;
    private String[] sensors = new String[]{"DO","conductivity","ph","temp"};
    private String category = "aquaculture";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aquaculture);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        String regFlag = sp.getString(getString(R.string.deviceAquaRegFlag),"");
        final String deviceID = sp.getString(getString(R.string.deviceID),"");
        if(!regFlag.equals("Registered")){
            dataController.categoryRegistration(this,R.string.deviceAquaRegFlag, deviceID,sp, category, sensors);
        }
        button.intializeAquaButton(this);
        dataController.buttonFunction(deviceID,category,button.aquaDO,sensors[0],sp);
        dataController.buttonFunction(deviceID,category,button.aquaCon,sensors[1],sp);
        dataController.buttonFunction(deviceID,category,button.aquaPH,sensors[2],sp);
        dataController.buttonFunction(deviceID,category,button.aquaTemp,sensors[3],sp);
    }


}
