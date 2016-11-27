package com.example.pexpa.rejestrator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import bazadanych.DBManager;
import bazadanych.PatientAdapter;

public class ListaPacjentow extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pacjentow);

    }
}
