package com.example.aic.onem2m_demo;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Agriculture extends AppCompatActivity {

    private ViewController button = new ViewController();
    private DataController dataController = new DataController();
    private SharedPreferences sp;
    private String[] sensors = new String[]{"moisture","gas","humidity","temp"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agriculture);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        String regFlag = sp.getString(getString(R.string.deviceAgriRegFlag),"");
        final String deviceID = sp.getString(getString(R.string.deviceID),"");
        if(!regFlag.equals("Registered")){
            dataController.categoryRegistration(this, deviceID, sp,"agriculture", sensors);
        }
        button.intializeAgriButton(this);
        button.agrMoist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataController.sendToServer("/server/"+deviceID+"/agriculture/moisture",4,"{\"m2m:cin\":{\"con\":\"OFF\"}}","Cae_device"+deviceID);
            }
        });
    }

    /*private void categoryRegistration(String deviceID){
        SharedPreferences.Editor editor = sp.edit();
        String msg = dataController.sendToServer("/server/"+deviceID,3,"{\"m2m:cnt\":{\"rn\":\"agriculture\"}}","Cae_device"+deviceID);
        if(msg.equals("Created")){
            dataController.sendToServer("/server/"+deviceID+"/agriculture",3,"{\"m2m:cnt\":{\"rn\":\"moisture\"}}","Cae_device"+deviceID);
            editor.putString(getString(R.string.deviceAgriRegFlag),"Registered");
            editor.commit();
        }
    }*/
}
