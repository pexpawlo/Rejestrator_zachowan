package com.example.pexpa.rejestrator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;

import bazadanych.DBManager;
import bazadanych.Patient;
import bazadanych.PatientAdapter;

import static java.lang.Integer.parseInt;

public class RozpocznijBadanieMenu extends AppCompatActivity {


    private int liczbaMinuti;
    private RadioGroup rg1;
    private EditText edit;
    private boolean wybor=false;
    ArrayList<Patient> patientsList;
    PatientAdapter adapter;
    Button rbb;
    private void actv(final boolean active)
    {
        edit.setEnabled(active);
        if (active)
        {
            edit.requestFocus();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rozpocznij_badanie_menu);
        final Button badaj = (Button) findViewById(R.id.rozpocznij_badanie_button);
        badaj.setEnabled(false);
       /* PRZYKŁAD LISTY */
        ListView pacjenci = (ListView) findViewById(R.id.roz_bad);
        DBManager db = new DBManager(this);


        rbb = (Button) findViewById(R.id.rozpocznij_badanie_button);
        patientsList = db.getAllPatients(null);
        adapter = new PatientAdapter(patientsList, this);
        edit = (EditText) findViewById(R.id.liczbaMinut);
        pacjenci.setAdapter(adapter);
        RadioButton rb = (RadioButton) findViewById(R.id.radioButton);
       // rb.seton

        pacjenci.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Patient a = (Patient) (adapterView.getItemAtPosition(i));
                rbb.setText("ID:" + l + " imie: " + a.getName());
                for(int aa=0; aa<patientsList.size(); aa++){
                    patientsList.get(aa).checked = false;
                }
                patientsList.get(i).checked = true;
                adapter.which = (int) patientsList.get(i).getId();
                badaj.setEnabled(true);
                adapter.notifyDataSetChanged();
            }
        });
        /*KONIEC PRZYKŁADU LISTY
         *
          * */

        actv(false);
        rg1 = (RadioGroup) findViewById(R.id.radioGroupMinuty);
        rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButtonReczny:
                        wybor = false;
                        actv(false);
                        break;

                    case R.id.radioButtonMinuty:
                        wybor = true;
                        actv(true);
                        break;

                }

            }
        });



        badaj.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), Rejestracja.class);
                edit = (EditText) findViewById(R.id.liczbaMinut);
                liczbaMinuti = Integer.parseInt(edit.getText().toString());
                i.putExtra("id_pacjenta", adapter.which);
                i.putExtra("czas", liczbaMinuti);
                i.putExtra("wybor", wybor);
                startActivity(i);
            }
        });
    }
}
