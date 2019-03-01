package com.example.aic.onem2m_demo;

import android.app.Activity;
import android.widget.Button;
import android.widget.EditText;

public class ViewController {
    public EditText id;
    public Button start;
    public Button agriculture, aquaculture, telemed, smartHome, disaster;
    public Button dGPS;

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

    public void initializeDisasterButton(Activity activity){
        dGPS = (Button) activity.findViewById(R.id.gps);
    }

}
