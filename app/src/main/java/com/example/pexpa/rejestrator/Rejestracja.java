package com.example.pexpa.rejestrator;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Rejestracja extends AppCompatActivity {

public int czas;
    public boolean wybor;
public  int iloscKlikniec=0;
    public  int czasKoncowy;

    public boolean pytanieRejestracja(final Class cls)
    {

        new AlertDialog.Builder(this)
                .setTitle("Zakończyć badanie?")

                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i=new Intent(getApplicationContext(),cls);
                        startActivity(i);
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


    @Override
    public void onBackPressed() {

        pytanieRejestracja(RozpocznijBadanieMenu.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rejestracja);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
             wybor = extras.getBoolean("wybor");
            if(wybor==true)
            {
                czas = extras.getInt("czas");

            }
            //The key argument here must match that used in the other activity
        }

        TextView czass = (TextView) findViewById(R.id.pokazCzas);
        if(wybor==true)
        {
            czass.setText("Pozostało " + czas + " minut");
        }
        else
        {
            czass.setText("Upłynęło " + czas + " minut");
        }


        Button zakoncz=(Button)findViewById(R.id.zakoncz_badanie_button);
        zakoncz.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


               pytanieRejestracja(Podsumowanie.class);

            }
        });

    }
}


