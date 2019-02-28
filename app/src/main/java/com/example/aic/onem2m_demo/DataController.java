package com.example.aic.onem2m_demo;

import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class DataController {
    //CSE Params
    public static final String host = "http://192.168.20.187:8080";
    //private static final String host = "http://acctechstaging.southeastasia.cloudapp.azure.com:8080";
    //AE Params
    private static final String origin = "Cae_device9";//Do not change Constant in oneM2M
    private static final int aePort = 3000;

    protected void sendToServer(final String location, final int ty, final String rep){
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

                    Log.i("INFO","connected: "+url.getHost());
                    Log.i("INFO","Response message " + httpConn.getResponseMessage());
                    Log.i("INFO", "Response Code " + String.valueOf(httpConn.getResponseCode()));
                    httpConn.disconnect();
                }catch(Exception e){
                    Log.i("INFO","Connection failed: " +url +" "+ e.getMessage());
                }
            }
        }.start();

    }
}
