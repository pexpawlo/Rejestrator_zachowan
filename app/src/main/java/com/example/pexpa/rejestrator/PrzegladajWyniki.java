package com.example.pexpa.rejestrator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;

import java.util.ArrayList;

import bazadanych.DBManager;
import bazadanych.Patient;
import bazadanych.PatientAdapter;

public class PrzegladajWyniki extends AppCompatActivity {


    ArrayList<Patient> patientsList;
    PatientAdapter adapter;
    Button rbb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_przegladaj_wyniki);


               /* PRZYKŁAD LISTY */
        ListView pacjenci = (ListView) findViewById(R.id.listViewPacjenci);
        DBManager db = new DBManager(this);

        final Button ogladaj = (Button) findViewById(R.id.zobacz_dane_pacjenta_button);
        rbb = (Button) findViewById(R.id.zobacz_dane_pacjenta_button);
        patientsList = db.getAllPatients(null);
        adapter = new PatientAdapter(patientsList, this);
        //edit = (EditText) findViewById(R.id.liczbaMinut);
        pacjenci.setAdapter(adapter);
        RadioButton rb = (RadioButton) findViewById(R.id.radioButton);
        // rb.seton

        pacjenci.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Patient a = (Patient) (adapterView.getItemAtPosition(i));
                rbb.setText("ID:" + l + " imie: " + a.getName());
                for(int aa=0; aa<patientsList.size(); aa++){
                    patientsList.get(aa).checked = false;
                }
                patientsList.get(i).checked = true;
                adapter.which = (int) patientsList.get(i).getId();
                ogladaj.setEnabled(true);
                adapter.notifyDataSetChanged();
            }
        });
        /*KONIEC PRZYKŁADU LISTY
         *
          * */

        ogladaj.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), SpisBadan.class);
                i.putExtra("id_pacjenta", adapter.which);
                startActivity(i);
            }
        });
    }
}
