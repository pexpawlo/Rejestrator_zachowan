package com.example.pexpa.rejestrator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;
import bazadanych.DBManager;
import bazadanych.Patient;
import bazadanych.PatientAdapter;

public class PatientListActivity extends AppCompatActivity {

    PatientAdapter adapter;
    Button showPatientTherapiesButton;
    DBManager db;

    ListView patientsListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);
        db = new DBManager(this);
        setPatientsListView();
        setShowPatientTherapiesButton();
    }

    private void setPatientsListView(){

        patientsListView = (ListView) findViewById(R.id.listViewPacjenci);
        adapter = new PatientAdapter(db.getAllPatients(null), this);
        patientsListView.setAdapter(adapter);
        patientsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Patient a = (Patient) (adapterView.getItemAtPosition(i));

                for(int aa=0; aa<adapter.patients.size(); aa++){
                    adapter.patients.get(aa).checked = false;
                }
                adapter.patients.get(i).checked = true;
                adapter.which = (int) adapter.patients.get(i).getId();
                showPatientTherapiesButton.setEnabled(true);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void setShowPatientTherapiesButton(){
        showPatientTherapiesButton = (Button) findViewById(R.id.zobacz_dane_pacjenta_button);
        showPatientTherapiesButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), ChooseGraphTypeActivity.class);
                i.putExtra("id_pacjenta", adapter.which);
                startActivity(i);
            }
        });
    }
}
