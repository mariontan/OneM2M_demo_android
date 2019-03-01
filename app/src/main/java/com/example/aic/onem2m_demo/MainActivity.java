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
    //CSE Params
    //public static final String host = "http://192.168.20.187:8080";
    //private static final String host = "http://acctechstaging.southeastasia.cloudapp.azure.com:8080";
    //AE Params
    //private static final String origin = "Cae_device9";//Do not change Constant in oneM2M
    //private static final int aePort = 3000;
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
        //DataController dataController = new DataController();
        //dataController.sendToServer("/server",2,"{\"m2m:ae\":{\"rn\":\"mydevice9\",\"api\":\"mydevice9.company.com\",\"rr\":\"true\",\"poa\":[\"http://"+Utils.getIPAddress(true)+":80\"]}}");
        //dataController.sendToServer("/server",2,"{\"m2m:ae\":{\"rn\":\"mydevice10\",\"api\":\"mydevice10.company.com\",\"rr\":\"true\",\"poa\":[\"http://"+Utils.getIPAddress(true)+":80\"]}}");
        //dataController.sendToServer("/server/mydevice9",3,"{\"m2m:cnt\":{\"rn\":\"luminosity\"}}");
        //dataController.sendToServer("/server/mydevice9/luminosity",4,"{\"m2m:cin\":{\"con\":\"0\"}}");
        //dataController.sendToServer("/server/mydevice9",3,"{\"m2m:cnt\":{\"rn\":\"led\"}}");
        //dataController.sendToServer("/server/mydevice9/led",4,"{\"m2m:cin\":{\"con\":\"OFF\"}}");
        //dataController.sendToServer("/server/mydevice9/led",23,"{\"m2m:sub\":{\"rn\":\"led_sub\",\"nu\":[\"Cae_device9\"],\"nct\":1}}");*/
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
