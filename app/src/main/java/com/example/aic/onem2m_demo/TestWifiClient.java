package com.example.aic.onem2m_demo;

public class TestWifiClient implements iWifiClient{

    @Override
    public boolean connect(String host, String httpPort) {
        return false;
    }

    @Override
    public boolean available() {
        return true;
    }

    @Override
    public void print(String req) {

    }

    @Override
    public String readStringUntil(char delim) {
        return "";
    }
}
