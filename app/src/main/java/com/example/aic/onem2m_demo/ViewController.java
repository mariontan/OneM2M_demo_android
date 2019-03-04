package com.example.aic.onem2m_demo;

import android.app.Activity;
import android.widget.Button;
import android.widget.EditText;

public class ViewController {
    public EditText id;
    public Button start;
    public Button agriculture, aquaculture, telemed, smartHome, disaster;
    public Button dGPS;
    public Button agrMoist,agrGas,agrHum,agrTemp;
    public Button aquaDO,aquaCon,aquaPH,aquaTemp;
    public Button teleECG;
    public Button smrtTemp,smrtHum;

    public ViewController(){

    }
    public void initilizeStartButton(Activity activity){
        start = (Button) activity.findViewById(R.id.start);
    }
    public void initializeButton(Activity activity){
        agriculture = (Button) activity.findViewById(R.id.agriculture);
        aquaculture = (Button) activity.findViewById(R.id.aquaculture);
        telemed = (Button) activity.findViewById(R.id.telemed);
        smartHome = (Button) activity.findViewById(R.id.smarthome);
        disaster = (Button) activity.findViewById(R.id.disaster);
    }

    public void intializeEditText(Activity activity){
        id = (EditText) activity.findViewById(R.id.ID_name);
    }

    public void intializeAgriButton(Activity activity){
        agrMoist = (Button) activity.findViewById(R.id.agrmoist);
        agrGas = (Button) activity.findViewById(R.id.agrgas);
        agrHum = (Button) activity.findViewById(R.id.agrhumidity);
        agrTemp = (Button) activity.findViewById(R.id.agrtemp);
    }

    public void intializeAquaButton(Activity activity){
        aquaDO = (Button) activity.findViewById(R.id.aquaDO);
        aquaCon = (Button) activity.findViewById(R.id.aquaCon);
        aquaPH =(Button) activity.findViewById(R.id.aquaPH);
        aquaTemp = (Button) activity.findViewById(R.id.aquaTemp);
    }

    public void initializeTeleButton(Activity activity){
        teleECG = (Button) activity.findViewById(R.id.ecg);
    }

    public void initializeSmrtButton(Activity activity){
        smrtTemp = (Button) activity.findViewById(R.id.smrttemp);
        smrtHum = (Button) activity.findViewById(R.id.smrtHum);
    }
    public void initializeDisasterButton(Activity activity){
        dGPS = (Button) activity.findViewById(R.id.gps);
    }



}
