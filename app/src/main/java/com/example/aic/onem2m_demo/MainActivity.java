package com.example.aic.onem2m_demo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkInternet();
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        String regFlag = sp.getString(getString(R.string.deviceRegFlag),"");
        if(!regFlag.equals("Registered")){
            RegisterDevice();
        }
        buttons();
    }

    private void checkInternet(){
        ConnectivityManager check = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] info = check.getAllNetworkInfo();

        for (int i = 0; i<info.length; i++){
            if (info[i].getState() == NetworkInfo.State.CONNECTED){
                Toast.makeText(getApplicationContext(), "Internet is connected",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void buttons(){

        final Context MainAct = MainActivity.this;
        ViewController buttons = new ViewController();
        buttons.initializeButton(this);
        buttons.agriculture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentStarter(MainAct,Agriculture.class);
            }
        });
        buttons.aquaculture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentStarter(MainAct,Aquaculture.class);
            }
        });
        buttons.telemed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentStarter(MainAct, Telemed.class);
            }
        });
        buttons.smartHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentStarter(MainAct,SmartHome.class);
            }
        });
        buttons.disaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentStarter(MainAct, Disaster.class);
            }
        });

    }

    private void intentStarter(Context context, Class clas){
        Intent intent = new Intent(context, clas);
        startActivity(intent);
    }

    private void RegisterDevice(){
        String deviceID = sp.getString(getString(R.string.deviceID),"");
        DataController dataController = new DataController();
        String msg = dataController.sendToServer("/server",2,"{\"m2m:ae\":{\"rn\":\""+deviceID+"\",\"api\":\""+deviceID+".company.com\",\"rr\":\"true\",\"poa\":[\"http://"+Utils.getIPAddress(true)+":80\"]}}","Cae_"+deviceID);
        SharedPreferences.Editor editor = sp.edit();
        if(msg.equals("Created")){
            editor.putString(getString(R.string.deviceRegFlag),"Registered");
            editor.commit();
        }
        Log.i("INFO","test : "+sp.getString(getString(R.string.deviceRegFlag),""));
    }

}
