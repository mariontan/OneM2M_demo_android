package com.example.aic.onem2m_demo;

public interface iWifiClient {
    boolean connect(String host, String httpPort);
    void print(String req);
    boolean available();
    String readStringUntil(char delim);
}


