package com.example.pexpa.rejestrator;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
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
        therapiesListView.setEmptyView(findViewById(R.id.textView6));
       therapiesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                for(int aa=0; aa<a.therapies.size(); aa++){
                    a.therapies.get(aa).setChecked(false);
                }
                a.therapies.get(i).setChecked(true);
                a.which = a.therapies.get(i).getId();
                a.notifyDataSetChanged();
            }
        });
        Button btn = (Button) findViewById(R.id.activity_therapies_list_btn_choose);
        btn.setClickable(false);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(a.which!=-1) {
                    Intent i = new Intent(getApplicationContext(), SummaryGraphActivity.class);
                    i.putExtra("idTerapii", a.which);
                    startActivity(i);
                }
                else{
                    showTherapyNotCheckedDialog();
                }
            }
        });
    }
    public void showTherapyNotCheckedDialog()
    {

        new AlertDialog.Builder(this)
                .setTitle("Nie wybrano terapii!")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }


}
