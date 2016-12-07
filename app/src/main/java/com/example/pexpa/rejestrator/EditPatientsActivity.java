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
import bazadanych.DBManager;
import bazadanych.PatientAdapter;

public class EditPatientsActivity extends AppCompatActivity {
 //   ArrayList<Patient> patientsList;
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
        setPatientsListView();
        setDeleteButton();
        setEditButton();
        setAddButton();

    }

    private boolean showDeleteAlertDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Usunąć pacjenta?")
                .setMessage("Wszystkie dane i badania zostaną usunięte")
                .setPositiveButton("TAK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        db.open();
                        db.deletePatient(adapter.which);
                        adapter.patients=db.getAllPatients(null);
                        db.close();
                       adapter.notifyDataSetChanged();
                        //TODO: nie usuwa jeszcze terapii usuwanego pacjenta
                    }
                })
                .setNegativeButton("NIE", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
        return true;

    }

    private void setDeleteButton(){
        deleteButton = (Button) findViewById(R.id.usun_pacjenta_button);
        deleteButton.setError("najpierw wybierz pacjenta");
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adapter.which!=-1) showDeleteAlertDialog();
                else showPatientNotCheckedDialog();
            }
        });
        deleteButton.setClickable(false);
    }

    private void setEditButton(){
        editButton = (Button) findViewById(R.id.edytuj_pacjenta_button);
        deleteButton.setClickable(false);
        editButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(adapter.which!=-1) {
                    Intent i = new Intent(getApplicationContext(), EditPatientActivity.class);
                    i.putExtra("id_pacjenta", adapter.which);
                    startActivity(i);
                }
                else showPatientNotCheckedDialog();
            }
        });
    }

    private void setAddButton(){
        addButton = (Button) findViewById(R.id.dodaj_pacjenta_button);
        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
    Intent i = new Intent(getApplicationContext(), AddPatientActivity.class);
    i.putExtra("id_pacjenta", adapter.which);
    startActivity(i);
            }
        });

    }

    private void setPatientsListView(){
        patientsListView = (ListView) findViewById(R.id.activity_edit_patients_lv_patients);
        adapter = new PatientAdapter(db.getAllPatients(null), this);
        patientsListView.setAdapter(adapter);


        patientsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                for(int aa=0; aa<adapter.patients.size(); aa++){
                    adapter.patients.get(aa).checked = false;
                }
                adapter.patients.get(i).checked = true;
                adapter.which = adapter.patients.get(i).getId();
                deleteButton.setClickable(true);
                editButton.setClickable(true);
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void showPatientNotCheckedDialog()
    {

        new AlertDialog.Builder(this)
                .setTitle("Nie wybrano pacjenta!")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
