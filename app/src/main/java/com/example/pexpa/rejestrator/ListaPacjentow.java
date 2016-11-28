package com.example.pexpa.rejestrator;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;

import java.util.ArrayList;

import bazadanych.DBManager;
import bazadanych.Patient;
import bazadanych.PatientAdapter;

public class ListaPacjentow extends AppCompatActivity {
    ArrayList<Patient> patientsList;
    PatientAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pacjentow);



                       /* PRZYKŁAD LISTY */
        ListView pacjenci = (ListView) findViewById(R.id.listViewZarzadzajPacjentami);
        DBManager db = new DBManager(this);

        final Button edytuj = (Button) findViewById(R.id.edytuj_pacjenta_button);
        final Button dodaj = (Button) findViewById(R.id.dodaj_pacjenta_button);
        final Button usun = (Button) findViewById(R.id.usun_pacjenta_button);
        //rbb = (Button) findViewById(R.id.zobacz_dane_pacjenta_button);
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
                //rbb.setText("ID:" + l + " imie: " + a.getName());
                for(int aa=0; aa<patientsList.size(); aa++){
                    patientsList.get(aa).checked = false;
                }
                patientsList.get(i).checked = true;
                adapter.which = (int) patientsList.get(i).getId();
                edytuj.setEnabled(true);
                dodaj.setEnabled(true);
                usun.setEnabled(true);
                adapter.notifyDataSetChanged();
            }
        });
        /*KONIEC PRZYKŁADU LISTY
         *
          * */

        dodaj.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i=new Intent(getApplicationContext(),PacjentInformacje.class);
                i.putExtra("id_pacjenta", adapter.which);
                startActivity(i);
            }
        });

        usun.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
pytanieUsun(adapter.which);
//jakas aktualizacja listy
            }
        });


        edytuj.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i=new Intent(getApplicationContext(),PacjentInformacje.class);
                i.putExtra("id_pacjenta", adapter.which);
                startActivity(i);
            }
        });




    }

    public boolean pytanieUsun(int id)
    {

        new AlertDialog.Builder(this)
                .setTitle("Usunąć pacjenta?")
                .setMessage("Wszystkie dane i badania zostaną usunięte")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {


                        //usuwanie z bazy danych - korzystamy z id
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
