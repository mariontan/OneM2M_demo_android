package com.example.aic.onem2m_demo;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;

import javax.net.ssl.HttpsURLConnection;


public class MainActivity extends AppCompatActivity {
    //CSE Params
    //public static final String host = "http://192.168.20.187:8080";
    //private static final String host = "http://acctechstaging.southeastasia.cloudapp.azure.com:8080";
    //AE Params
    //private static final String origin = "Cae_device9";//Do not change Constant in oneM2M
    //private static final int aePort = 3000;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttons();
        checkInternet();
        DataController dataController = new DataController();
        dataController.sendToServer("/server",2,"{\"m2m:ae\":{\"rn\":\"mydevice9\",\"api\":\"mydevice9.company.com\",\"rr\":\"true\",\"poa\":[\"http://"+Utils.getIPAddress(true)+":80\"]}}");
        dataController.sendToServer("/server/mydevice9",3,"{\"m2m:cnt\":{\"rn\":\"luminosity\"}}");
        dataController.sendToServer("/server/mydevice9/luminosity",4,"{\"m2m:cin\":{\"con\":\"0\"}}");
        dataController.sendToServer("/server/mydevice9",3,"{\"m2m:cnt\":{\"rn\":\"led\"}}");
        dataController.sendToServer("/server/mydevice9/led",4,"{\"m2m:cin\":{\"con\":\"OFF\"}}");
        dataController.sendToServer("/server/mydevice9/led",23,"{\"m2m:sub\":{\"rn\":\"led_sub\",\"nu\":[\"Cae_device9\"],\"nct\":1}}");
    }

    private void buttons(){
        final Context MainAct = MainActivity.this;
        ViewController buttons = new ViewController(this);
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


    private void Register(){
        String localIP = Utils.getIPAddress(true);
        // Create AE resource
        //String resulat = Send("/server",2,"{\"m2m:ae\":{\"rn\":\"mydevice1\",\"api\":\"mydevice1.company.com\",\"rr\":\"true\",\"poa\":[\"http://"+localIP+":"+aePort+"\"]}}");
        String resulat="";
        //String rep = "{\"m2m:ae\":{\"rn\":\"mydevice9\",\"api\":\"mydevice9.company.com\",\"rr\":\"true\",\"poa\":[\"http://"+Utils.getIPAddress(true)+":"+aePort+"\"]}}";
        //connectToServer(rep);
        Log.i("IP Address",localIP);
        if(resulat=="HTTP/1.1 201 Created"){
            // Create Container resource
            Send("/server/mydevice1",3,"{\"m2m:cnt\":{\"rn\":\"luminosity\"}}");

            // Create ContentInstance resource
            Send("/server/mydevice1/luminosity",4,"{\"m2m:cin\":{\"con\":\"0\"}}");

            // Create Container resource
            Send("/server/mydevice1",3,"{\"m2m:cnt\":{\"rn\":\"led\"}}");

            // Create ContentInstance resource
            Send("/server/mydevice1/led",4,"{\"m2m:cin\":{\"con\":\"OFF\"}}");

            // Create Subscription resource
            Send("/server/mydevice1/led",23,"{\"m2m:sub\":{\"rn\":\"led_sub\",\"nu\":[\"Cae_device1\"],\"nct\":1}}");
        }
    }

    //put http connection code inside, needs input and output stream
    private String Send(String url,int ty, String rep){
        iWifiClient client = new TestWifiClient();


        // url = name of the device
        // rep = sensor data
        // prepare the HTTP request
        /*String req = "POST " + url + " HTTP/1.1\r\n" +
                "Host: " + host + "\r\n" +
                "X-M2M-Origin: " + origin + "\r\n" +
                "Content-Type: application/json;ty="+ty+"\r\n" +
                "Content-Length: "+ rep.length()+"\r\n"+
                "Connection: close\r\n\n" +
                rep;*/

        /***************send the http request here*************/

        /***
        //checks if the server timed out
        unsigned long timeout = millis();
        while (client.available() == 0) {
            if (millis() - timeout > 5000) {
                Serial.println(">>> Client Timeout !");
                client.stop();
                return "error";
            }
        }
        ***/

        String res = "";
        if(client.available()) {
            res = client.readStringUntil('\r');
            return res;
        }
        else{
            return "";
        }
        /*while(client.available()){
            String line = client.readStringUntil('\r');

        }*/

    }

    private void Push(){
        Random rand = new Random();
        String data = Integer.toString(rand.nextInt(50));
        Send("/server/mydevice1/luminosity",4,data);
    }







}
