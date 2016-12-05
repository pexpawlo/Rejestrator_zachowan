package com.example.pexpa.rejestrator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import bazadanych.DBManager;
import bazadanych.Patient;
import bazadanych.PatientAdapter;

import static java.lang.Integer.parseInt;

public class StartTherapyActivity extends AppCompatActivity {

    ArrayList<Patient> patientsList;
    PatientAdapter adapter;
    Button startTherapyButton;
    ListView patientsListView;
    DBManager db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_therapy);
        db = new DBManager(this);
        setStartTherapyButton();
        setPatientsListView();
    }

    private void setStartTherapyButton(){
        startTherapyButton = (Button) findViewById(R.id.activity_start_therapy_btn_start);
        startTherapyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), TherapyActivity.class);
                i.putExtra("id_pacjenta", adapter.which);
                startActivity(i);
            }
        });
    }

    private void setPatientsListView(){
        patientsListView = (ListView) findViewById(R.id.activity_start_therapy_lv_patients);
        patientsList = db.getAllPatients(null);
        adapter = new PatientAdapter(patientsList, this);
        patientsListView.setAdapter(adapter);
        patientsListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                for(int aa=0; aa<patientsList.size(); aa++){
                    patientsList.get(aa).checked = false;
                }
                patientsList.get(i).checked = true;
                adapter.which = (int) patientsList.get(i).getId();
                adapter.notifyDataSetChanged();
            }
        });
    }
}
