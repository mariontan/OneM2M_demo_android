package com.example.aic.onem2m_demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Disaster extends AppCompatActivity {

    /*private static String location =
    private static String location2=
    private static int ty1=
    private static int ty2=;
    private static String rep1=
    private static String rep2=
    private static String origin=*/
    private ViewController button = new ViewController();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disaster);

        //register catergory under device
        categoryRegistration();
        button.initializeDisasterButton(this);
        button.dGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dataController.sendToServer("/server/mydevice9/led",4,"{\"m2m:cin\":{\"con\":\"OFF\"}}");
            }
        });
    }

    private void categoryRegistration(){
        DataController dataController = new DataController();
        dataController.sendToServer("/server/mydevice11",3,"{\"m2m:cnt\":{\"rn\":\"disaster\"}}","Cae_device11");
    }
}
