package com.example.pexpa.rejestrator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import bazadanych.DBManager;
import bazadanych.Event;
import bazadanych.Therapy;

public class Podsumowanie extends AppCompatActivity {
long therapyID;


    @Override
    public void onBackPressed() {

        Intent setIntent = new Intent(getApplicationContext(),MainActivity.class);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(setIntent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_podsumowanie);

        DBManager db = new DBManager(this);

TextView tv = (TextView) findViewById(R.id.CzasLiczba);
        TextView tv2 = (TextView) findViewById(R.id.KliknieciaLiczba);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ArrayList<Therapy> therapy = db.getAllTherapies("id = "+extras.get("idTerapii"));
            therapyID =extras.getLong("idTerapii");
           tv.setText( new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(  therapy.get(0).getStartDate()) + " " +
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(therapy.get(0).getEndDate()));


            ArrayList<Event> events = db.getAllEvents(" event_time >= '" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(  therapy.get(0).getStartDate())+
            "' AND " + " event_time <= '" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(  therapy.get(0).getEndDate()) +"'");
            tv2.setText(events.size()+"");

        }


        Button ZobaczWykres=(Button)findViewById(R.id.pokazWykres);
        ZobaczWykres.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i=new Intent(getApplicationContext(),Wykres.class);
                i.putExtra("idTerapii", therapyID);
                startActivity(i);
            }
        });


        Button WracajMenu=(Button)findViewById(R.id.wrocDoGlownegoMenu);
        WracajMenu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i=new Intent(getApplicationContext(),MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

    }
}
