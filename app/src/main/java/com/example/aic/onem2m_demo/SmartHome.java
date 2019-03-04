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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_home);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        String regFlag = sp.getString(getString(R.string.deviceSmrtRegFlag),"");
        final String deviceID = sp.getString(getString(R.string.deviceID),"");
        if(!regFlag.equals("Registered")){
            dataController.categoryRegistration(this,R.string.deviceSmrtRegFlag, deviceID,sp, "smarthome", sensors);
        }
        button.initializeSmrtButton(this);
        button.smrtTemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random rand = new Random();
                int n = rand.nextInt(40);
                dataController.sendToServer("/server/"+deviceID+"/smarthome/temp",4,"{\"m2m:cin\":{\"con\":\""+String.valueOf(n)+"\"}}","Cae_device"+deviceID);
            }
        });
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
