package com.example.aic.onem2m_demo;

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
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import javax.net.ssl.HttpsURLConnection;


public class MainActivity extends AppCompatActivity {
    //CSE Params
    private static final String host = "http://127.0.0.1";

    //AE Params
    private static final String origin = "Cae_device1";//Do not change Constant in oneM2M
    private static final int aePort = 80;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast toast = new Toast(getApplicationContext());
        try{
            URL url = new URL(host);
            HttpURLConnection httpConn = (HttpURLConnection)url.openConnection();
            OutputStream out = new BufferedOutputStream(httpConn.getOutputStream());

            InputStream in = new BufferedInputStream(httpConn.getInputStream());
            toast.makeText(getApplicationContext(),
                    url.getHost(),
                    Toast.LENGTH_LONG);

            toast.show();
            /*BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
            writer.write(data);
            writer.flush();
            writer.close();
            out.close();*/
            Log.i("INFO","connected");
            httpConn.connect();
        }catch(Exception e){
            Log.e("Error", e.getMessage());
        }


        //conflict with iWifiClient displays only blank screen
        EditText name = (EditText) findViewById(R.id.ID_name);
        Button sensor1 = (Button) findViewById(R.id.Sensor1);
        //Register();
        sensor1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //pushes random sensor value to the server
                Push();
            }
        });
    }

    private void Register(){
        String localIP = Utils.getIPAddress(true);
        // Create AE resource
        String resulat = Send("/server",2,"{\"m2m:ae\":{\"rn\":\"mydevice1\",\"api\":\"mydevice1.company.com\",\"rr\":\"true\",\"poa\":[\"http://"+localIP+":"+aePort+"\"]}}");
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
        String req = "POST " + url + " HTTP/1.1\r\n" +
                "Host: " + host + "\r\n" +
                "X-M2M-Origin: " + origin + "\r\n" +
                "Content-Type: application/json;ty="+ty+"\r\n" +
                "Content-Length: "+ rep.length()+"\r\n"+
                "Connection: close\r\n\n" +
                rep;

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
