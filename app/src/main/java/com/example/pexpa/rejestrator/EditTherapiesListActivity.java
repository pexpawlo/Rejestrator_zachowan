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
import bazadanych.TherapyAdapter;

public class EditTherapiesListActivity extends AppCompatActivity {
    TherapyAdapter a;
    DBManager db = new DBManager(this);
    long patientid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_therapies_list);
         patientid = getIntent().getExtras().getLong("id_pacjenta");
        a = new TherapyAdapter(db.getAllTherapies(" patient_id = " + patientid),this);
        ListView therapiesListView = (ListView) findViewById(R.id.activity_edit_therapies_list_lv_therapies);
        therapiesListView.setAdapter(a);
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


        Button btn = (Button) findViewById(R.id.activity_edit_therapies_list_btn_delete);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(a.which!=-1) {
                    showDeleteAlertDialog();
                }
                else{
                    showTherapyNotCheckedDialog();
                }
            }
        });
    }

    private boolean showDeleteAlertDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Usunąć terapię?")

                .setPositiveButton("TAK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        db.open();
                        db.deleteTheapy(a.which);
                        a.therapies=db.getAllTherapies(" patient_id = " + patientid);
                        db.close();
                        a.notifyDataSetChanged();
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
