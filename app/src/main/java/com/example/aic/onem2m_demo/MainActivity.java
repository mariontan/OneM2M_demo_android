package com.example.aic.onem2m_demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {
    //CSE Params
    private static final String host = "127.0.0.1";

    //AE Params
    private static final String origin = "Cae_device1";//Do not change Constant in oneM2M
    private static final int aePort = 80;

    private void Register(){
        String localIP = Utils.getIPAddress(true);
        // Create AE resource
        String resulat = Send("/server",2,"{\"m2m:ae\":{\"rn\":\"mydevice1\",\"api\":\"mydevice1.company.com\",\"rr\":\"true\",\"poa\":[\"http://"+localIP+":"+aePort+"\"]}}");

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

    private String Send(String url,int ty, String rep){
        iWifiClient client = new TestWifiClient();
        // url = name of the device
        // rep = sensor data
        // prepare the HTTP request
        String req = "POST " + url + " HTTP/1.1\r\n" +
                "Host: " + host + "\r\n" +
                "X-M2M-Origin: " + origin + "\r\n" +
                "Content-Type: application/json;ty="+ty+"\r\n" +
                "Content-Length: "+ rep.length()+"\r\n"+
                "Connection: close\r\n\n" +
                rep;

        String res = "";
        if(client.available()==0) {
            res = client.readStringUntil('\r');
        }
        return res;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText name = (EditText) findViewById(R.id.ID_name);
        Button sensor1 = (Button) findViewById(R.id.Sensor1);


        sensor1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call send data here
            }
        });
    }



}
