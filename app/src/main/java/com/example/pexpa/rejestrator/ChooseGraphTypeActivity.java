package com.example.pexpa.rejestrator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import bazadanych.DBManager;
import bazadanych.Patient;


public class ChooseGraphTypeActivity extends AppCompatActivity {

    Button startTherapiesListButton;
    Button startCalendarGraphButton;
    TextView choosenPatientTextView;
    DBManager db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_graph_type);
        setStartTherapiesListButton();
        setStartCalendarGraphButton();
        db = new DBManager(this);
        choosenPatientTextView = (TextView) findViewById(R.id.activity_choose_graph_type_tv_patient_name);
        db.open();
        Bundle extras = getIntent().getExtras();
        Patient choosenPatient = db.getAllPatients("id = "+extras.getLong("id_pacjenta")).get(0);
        choosenPatientTextView.setText(choosenPatient.getName()+" "+choosenPatient.getSurname());

    }

    private void setStartTherapiesListButton(){
        startTherapiesListButton = (Button) findViewById(R.id.activity_choose_graph_type_btn_start_therapies_list);
        startTherapiesListButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i=new Intent(getApplicationContext(),TherapiesListActivity.class);
                i.putExtra("id_pacjenta", getIntent().getExtras().getLong("id_pacjenta"));
                startActivity(i);
            }
        });
    }
    private void setStartCalendarGraphButton(){
        startCalendarGraphButton = (Button) findViewById(R.id.activity_choose_graph_type_btn_calendar_graph);
        startCalendarGraphButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i=new Intent(getApplicationContext(),ChooseDateActivity.class);
                i.putExtra("id_pacjenta", getIntent().getExtras().getLong("id_pacjenta"));
                startActivity(i);
            }
        });
    }
}
