package com.example.pexpa.rejestrator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import bazadanych.DBManager;

public class MainActivity extends AppCompatActivity {

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

    private void setStartTherapyButton() {
        startTherapyButton = (Button) findViewById(R.id.activity_main_btn_start_therapy);
        startTherapyButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), StartTherapyActivity.class);
                startActivity(i);
            }
        });

    }

    private void setShowGraphsButton() {
        showGraphsButton = (Button) findViewById(R.id.activity_main_btn_show_graphs);
        showGraphsButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), PatientListActivity.class);
                startActivity(i);
            }
        });
    }

    private void setSettingsButton() {
        settingsButton = (Button) findViewById(R.id.activity_main_btn_settings);
        settingsButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(i);
            }
        });
    }
    public void randomData() throws ParseException {

//dodajemy 20 losowych pacjentów
        DBManager db = new DBManager(this);
        db.open();

        db.close();
        Random r = new Random();
// dodajemy losowe daty terapii
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


        for(int i=0; i<250; i++){
            System.out.println("   i :  "+i);
            String year = 2016+"";
            String month = (r.nextInt(12)+1)+"";
            String day = (r.nextInt(28)+1)+"";

            String hour =  (r.nextInt(24))+"";
            String min = (r.nextInt(60))+"";
            String sec = (r.nextInt(60))+"";


            String date = year + "-" +(month.length()==1?"0"+month:month) + "-" + (day.length()==1?"0"+day:day) + " " + (hour.length()==1?"0"+hour:hour) +":"+(min.length()==1?"0"+min:min)+":"+(sec.length()==1?"0"+sec:sec);
            Date start = sdf.parse(date);
            long time = start.getTime();
            time+=(r.nextInt(4)+1)*1*1000*60*60;
            time+=r.nextInt(60)*1*1000*60;
            Date end = new Date(time);
            int patientID = r.nextInt(20)+1;
            long timeBetween = end.getTime() - start.getTime();
            //System.out.println("START: " + sdf.format(start)+"   KONIEC:  "+sdf.format(end)+"   id pacje:  "+patientID);
            db.open();
            db.insertTherapy(patientID,start,end);
            db.close();
            int events = r.nextInt(25)+1;
            for(int j=0; j<events; j++){
                long newTime= (long) (timeBetween*r.nextFloat());
                Date date123 = new Date(start.getTime()+newTime);
                //System.out.println(sdf.format(date123)+"   id pacje:  "+patientID);
                db.open();
                db.insertEvent(patientID,date123);
                db.close();
            }
//db.close();
        }

    }
}