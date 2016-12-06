package com.example.pexpa.rejestrator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import bazadanych.DBManager;
import bazadanych.TherapyAdapter;

public class TherapiesListActivity extends AppCompatActivity {

    TherapyAdapter a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_therapies_list);

        ListView therapiesListView = (ListView) findViewById(R.id.activity_therapies_list_lv_therapies);
        DBManager db = new DBManager(this);
        long patientid = getIntent().getExtras().getLong("id_pacjenta");
        a = new TherapyAdapter(db.getAllTherapies(" patient_id = " + patientid),this);
        therapiesListView.setAdapter(a);
        Button btn = (Button) findViewById(R.id.button5);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SummaryGraphActivity.class);
                i.putExtra("idTerapii", a.which);
                startActivity(i);
            }
        });
    }


}
