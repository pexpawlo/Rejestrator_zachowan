package com.example.pexpa.rejestrator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import bazadanych.DBManager;

public class AddPatientActivity extends AppCompatActivity {

    EditText nameEditText;
    EditText surnameEditText;
    Button addPatientButton;
    DBManager db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);
        db = new DBManager(this);
        nameEditText = (EditText) findViewById(R.id.acitivity_add_patient_et_name);
        surnameEditText = (EditText) findViewById(R.id.acitivity_add_patient_et_surname);
        addPatientButton = (Button) findViewById(R.id.activity_add_patient_btn_save);
        addPatientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.open();
                db.insertPatient(nameEditText.getText().toString(), surnameEditText.getText().toString());
                db.close();
                Intent i=new Intent(getApplicationContext(),EditPatientsActivity.class);
                startActivity(i);
            }
        });

    }
}
