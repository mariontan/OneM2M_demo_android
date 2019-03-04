package com.example.aic.onem2m_demo;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class DataController {
    //CSE Params
    public static final String host = "http://192.168.20.187:8080";
    //private static final String host = "http://acctechstaging.southeastasia.cloudapp.azure.com:8080";
    private static final int aePort = 3000;
    /****https://stackoverflow.com/questions/9148899/returning-value-from-thread***///return values from threads

    protected void categoryRegistration(Activity activity,int id, String deviceID, SharedPreferences sp, String category, String[] sensors ){
        SharedPreferences.Editor editor = sp.edit();
        String msg = sendToServer("/server/"+deviceID,3,"{\"m2m:cnt\":{\"rn\":\""+category+"\"}}","Cae_device"+deviceID);
        if(msg.equals("Created")){
            for(String sensor: sensors){
                sendToServer("/server/"+deviceID+"/"+category+"",3,"{\"m2m:cnt\":{\"rn\":\""+sensor+"\"}}","Cae_device"+deviceID);
            }
            editor.putString(activity.getString(id),"Registered");
            editor.commit();
        }
    }

    protected void buttonFunction(final String deviceID, final String category, Button button, final String sensor){
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendToServer("/server/"+deviceID+"/"+category+"/"+sensor,4,"{\"m2m:cin\":{\"con\":\""+dataGen()+"\"}}","Cae_device"+deviceID);
                }
            });
    }
    protected String sendToServer(final String location, final int ty, final String rep,final String origin){
        final CountDownLatch latch = new CountDownLatch(1);
        final String[] msg = new String[1];
        new Thread(){
            public void run() {
                URL url = null;
                try{
                    url = new URL(host+location);
                    URLConnection urlConn = url.openConnection();

                    if (!(urlConn instanceof HttpURLConnection)) {
                        throw new IOException("URL is not an Http URL");
                    }
                    HttpURLConnection httpConn = (HttpURLConnection) urlConn;
                    httpConn.setRequestMethod("POST");
                    httpConn.setRequestProperty("X-M2M-Origin", origin);
                    httpConn.setRequestProperty("Content-Type", "application/json;ty="+String.valueOf(ty));
                    httpConn.setDoOutput(true);
                    httpConn.setChunkedStreamingMode(0);
                    //sending to server
                    OutputStream out = new BufferedOutputStream(httpConn.getOutputStream());
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
                    writer.write(rep);
                    writer.flush();
                    writer.close();
                    out.close();
                    httpConn.connect();
                    msg[0] = httpConn.getResponseMessage();
                    latch.countDown();
                    Log.i("INFO","connected: "+url.getHost());
                    Log.i("INFO","Response message " + httpConn.getResponseMessage());
                    Log.i("INFO", "Response Code " + String.valueOf(httpConn.getResponseCode()));
                    httpConn.disconnect();
                }catch(Exception e){
                    Log.i("INFO","Connection failed: " +url +" "+ e.getMessage());
                }
            }
        }.start();
        try {
            latch.await();
        }catch (Exception e){
            Log.i("INFO", "Thread issue: " + e.getMessage());
        }
        return msg[0];
    }

    private String dataGen(){
        Random rand = new Random();
        int n = rand.nextInt(40);
        return String.valueOf(n);
    }


}
