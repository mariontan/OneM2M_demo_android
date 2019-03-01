package com.example.aic.onem2m_demo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class StartScreen extends AppCompatActivity {
    private SharedPreferences sp;
    ViewController elements = new ViewController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
        elements.intializeEditText(this);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        elements.initilizeStartButton(this);
        elements.start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String regFlag = sp.getString(getString(R.string.deviceRegFlag),"");
                if(!regFlag.equals("Registered")){
                    setDeviceName();
                }
                Intent intent = new Intent(StartScreen.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setDeviceName(){
        String oldID = sp.getString(getString(R.string.deviceID),"");
        String deviceID = elements.id.getText().toString();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(getString(R.string.deviceID),deviceID);
        editor.commit();
        //resets the disable flags remove for final version
        if(!deviceID.equals(oldID)){
            editor.putString(getString(R.string.deviceRegFlag),"");
            editor.putString(getString(R.string.deviceDisasterRegFlag),"");
            editor.putString(getString(R.string.deviceAgriRegFlag),"");
            editor.putString(getString(R.string.deviceAquaRegFlag),"");
            editor.putString(getString(R.string.deviceTeleRegFlag),"");
            editor.putString(getString(R.string.deviceSmrtRegFlag),"");
            editor.commit();
        }
    }
}
