package com.example.pexpa.rejestrator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import bazadanych.DBManager;
import bazadanych.Patient;

public class EditPatientActivity extends AppCompatActivity {

    Patient editedPatient;
    long patientId;
    DBManager db;
    EditText nameEditText;
    EditText surnameEditText;
    Button saveChangesButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_patient);
        db = new DBManager(this);
        getExtras();
        editedPatient = db.getAllPatients(" id = "+patientId+";").get(0);
        nameEditText = (EditText) findViewById(R.id.acitivity_edit_patient_et_name);
        surnameEditText = (EditText) findViewById(R.id.acitivity_edit_patient_et_surname);
        nameEditText.setText(editedPatient.getName());
        surnameEditText.setText(editedPatient.getSurname());
        saveChangesButton = (Button) findViewById(R.id.activity_edit_patient_btn_save);
        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.open();
                db.updatePatient(patientId, nameEditText.getText().toString(),surnameEditText.getText().toString());
                db.close();
                Intent i=new Intent(getApplicationContext(),EditPatientsActivity.class);
                startActivity(i);
            }
        });

    }

    private void getExtras(){
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            patientId = extras.getLong("id_pacjenta");
        }
    }
}
