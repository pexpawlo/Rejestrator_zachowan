package com.example.pexpa.rejestrator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity  {

    private Button startTherapyButton;
    private Button showGraphsButton;
    private Button settingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setStartTherapyButton();
        setShowGraphsButton();
        setSettingsButton();
    }

    private void setStartTherapyButton(){
        startTherapyButton=(Button)findViewById(R.id.activity_main_btn_start_therapy);
        startTherapyButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i=new Intent(getApplicationContext(),StartTherapyActivity.class);
                startActivity(i);
            }
        });

    }

    private void setShowGraphsButton(){
        showGraphsButton=(Button)findViewById(R.id.activity_main_btn_show_graphs);
        showGraphsButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i=new Intent(getApplicationContext(),PatientListActivity.class);
                startActivity(i);
            }
        });
    }

    private void setSettingsButton(){
        settingsButton=(Button)findViewById(R.id.activity_main_btn_settings);
        settingsButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i=new Intent(getApplicationContext(),SettingsActivity.class);
                startActivity(i);
            }
        });
    }

}