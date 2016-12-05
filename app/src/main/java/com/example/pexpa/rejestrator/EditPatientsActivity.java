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
import java.util.ArrayList;
import bazadanych.DBManager;
import bazadanych.Patient;
import bazadanych.PatientAdapter;

public class EditPatientsActivity extends AppCompatActivity {
    ArrayList<Patient> patientsList;
    PatientAdapter adapter;
    DBManager db;

    Button editButton;
    Button deleteButton;
    Button addButton;
    ListView patientsListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_patients);

        db = new DBManager(this);
        setDeleteButton();
        setEditButton();
        setAddButton();
        setPatientsListView();
    }

    private boolean showDeleteAlertDialog(int id) {
        new AlertDialog.Builder(this)
                .setTitle("Usunąć pacjenta?")
                .setMessage("Wszystkie dane i badania zostaną usunięte")
                .setPositiveButton("TAK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        db.open();
                        db.deletePatient(which);
                        adapter.patients=db.getAllPatients(null);
                        db.close();
                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("NIE", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //nic nie robimy
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
        return true;

    }

    private void setDeleteButton(){
        deleteButton = (Button) findViewById(R.id.usun_pacjenta_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteAlertDialog(adapter.which);

            }
        });
    }

    private void setEditButton(){
        editButton = (Button) findViewById(R.id.edytuj_pacjenta_button);
        editButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i=new Intent(getApplicationContext(),EditPatientActivity.class);
                i.putExtra("id_pacjenta", adapter.which);
                startActivity(i);
            }
        });
    }

    private void setAddButton(){
        addButton = (Button) findViewById(R.id.dodaj_pacjenta_button);
        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i=new Intent(getApplicationContext(),EditPatientActivity.class);
                i.putExtra("id_pacjenta", adapter.which);
                startActivity(i);
            }
        });

    }

    private void setPatientsListView(){
        patientsListView = (ListView) findViewById(R.id.activity_edit_patients_lv_patients);
        patientsList=db.getAllPatients(null);
        adapter = new PatientAdapter(patientsList, this);

        patientsListView.setAdapter(adapter);


        patientsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Patient a = (Patient) (adapterView.getItemAtPosition(i));

                for(int aa=0; aa<patientsList.size(); aa++){
                    patientsList.get(aa).checked = false;
                }
                patientsList.get(i).checked = true;
                adapter.which = (int) patientsList.get(i).getId();
                editButton.setEnabled(true);
                addButton.setEnabled(true);
                deleteButton.setEnabled(true);
                adapter.notifyDataSetChanged();
            }
        });
    }
}
