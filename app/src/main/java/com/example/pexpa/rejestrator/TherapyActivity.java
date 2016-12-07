package com.example.pexpa.rejestrator;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import bazadanych.DBManager;
import bazadanych.Patient;

public class TherapyActivity extends AppCompatActivity {

public  int iloscKlikniec=0;
    public  int czasKoncowy;
    public Date start;
    public Patient p;
    DBManager db;
    public int seconds = 0;
    public int minutes = 0;
    public long therapyID;


    @Override
    public void onBackPressed() {

        pytanieRejestracja(StartTherapyActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_therapy);
        start = new Date();
        Button button = (Button) findViewById(R.id.dodaj_zdarzenie_button);

        //Declare the timer
        Timer t = new Timer();
        //Set the schedule function and rate
        t.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        TextView tv = (TextView) findViewById(R.id.pokazCzas);


                        if(seconds == 59)
                        {
                            seconds=0;
                            minutes=minutes+1;
                            tv.setText(String.valueOf(minutes)+":"+(seconds<10?"0"+String.valueOf(seconds):String.valueOf(seconds)));

                        }else{
                            seconds += 1;
                         //   tv.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(start));
                            tv.setText(String.valueOf(minutes)+":"+(seconds<10?"0"+String.valueOf(seconds):String.valueOf(seconds)));

                        }



                    }

                });
            }

        }, 0, 1000);




        db = new DBManager(this);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String data = sdf.format(date);
                db.open();
                db.insertEvent(p.getId(),date);
                db.updateTherapy(p.getId(),start,date,therapyID);
                db.close();
                Toast.makeText(TherapyActivity.this, "Pomyslnie dodano zdarzenie. Czas: " + data , Toast.LENGTH_SHORT).show();
            }
        });
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            long which =  extras.getLong("id_pacjenta");
            DBManager db = new DBManager(this);
            db.open();
            p = db.getAllPatients(" id = " + which).get(0);
            db.close();
            db.open();
            therapyID = db.insertTherapy(p.getId(),start,start);
            db.close();

            //The key argument here must match that used in the other activity
        }

        TextView czass = (TextView) findViewById(R.id.pokazCzas);

        TextView info = (TextView) findViewById(R.id.pokazKlikniecia);
        info.setText("Pacjent: " + p.getName()+" "+ p.getSurname());

        Button zakoncz=(Button)findViewById(R.id.zakoncz_badanie_button);
        zakoncz.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


               pytanieRejestracja(SummaryActivity.class);

            }
        });

    }
    public boolean pytanieRejestracja(final Class cls)
    {

        new AlertDialog.Builder(this)
                .setTitle("Zakończyć badanie?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i=new Intent(getApplicationContext(),cls);
                        Date date = new Date();
                        db.open();
                        db.updateTherapy(p.getId(),start,date,therapyID);
                        db.close();
                        i.putExtra("idTerapii", therapyID);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //nic nie robimy
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
        return true;

    }



}


