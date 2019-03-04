package com.example.aic.onem2m_demo;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Telemed extends AppCompatActivity {
    private ViewController button = new ViewController();
    private DataController dataController = new DataController();
    private SharedPreferences sp;
    private String[] sensors = new String[]{"ECG","EMG","temp","bloodsugar"};
    private String category = "telemed";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telemed);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        String regFlag = sp.getString(getString(R.string.deviceTeleRegFlag),"");
        final String deviceID = sp.getString(getString(R.string.deviceID),"");
        if(!regFlag.equals("Registered")){
            dataController.categoryRegistration(this,R.string.deviceTeleRegFlag, deviceID, sp,category, sensors);
        }
        button.initializeTeleButton(this);
        dataController.buttonFunction(deviceID,category,button.teleECG,sensors[0]);
        dataController.buttonFunction(deviceID,category,button.teleEMG,sensors[1]);
        dataController.buttonFunction(deviceID,category,button.teleTemp,sensors[2]);
        dataController.buttonFunction(deviceID,category,button.teleSugar,sensors[3]);
    }

    /*private void categoryRegistration(String deviceID){
        SharedPreferences.Editor editor = sp.edit();
        String msg = dataController.sendToServer("/server/"+deviceID,3,"{\"m2m:cnt\":{\"rn\":\"telemed\"}}","Cae_device"+deviceID);
        if(msg.equals("Created")){
            dataController.sendToServer("/server/"+deviceID+"/telemed",3,"{\"m2m:cnt\":{\"rn\":\"ECG\"}}","Cae_device"+deviceID);
            editor.putString(getString(R.string.deviceTeleRegFlag),"Registered");
            editor.commit();
        }
    }*/
}
