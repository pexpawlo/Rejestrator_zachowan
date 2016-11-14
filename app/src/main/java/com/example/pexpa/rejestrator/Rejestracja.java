package com.example.pexpa.rejestrator;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Rejestracja extends AppCompatActivity {


    public boolean pytanie()
    {

        new AlertDialog.Builder(this)
                .setTitle("Zakończyć badanie?")
                //.setMessage("Are you sure you want to delete this entry?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
        return true;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rejestracja);



        Button zakoncz=(Button)findViewById(R.id.zakoncz_badanie_button);
        zakoncz.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


               pytanie();
               // Intent i=new Intent(getApplicationContext(),Rejestracja.class);
               // startActivity(i);
            }
        });

    }
}


