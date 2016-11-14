package com.example.pexpa.rejestrator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity  {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Button idz_do_badanie=(Button)findViewById(R.id.rozpocznijBadanieButton);
        idz_do_badanie.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i=new Intent(getApplicationContext(),RozpocznijBadanieMenu.class);
                startActivity(i);
            }
        });


        Button idz_do_wyniki=(Button)findViewById(R.id.przegladajWynikiButton);
        idz_do_wyniki.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i=new Intent(getApplicationContext(),PrzegladajWyniki.class);
                startActivity(i);
            }
        });


        Button idz_do_ustawienia=(Button)findViewById(R.id.ustawieniaButton);
        idz_do_ustawienia.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i=new Intent(getApplicationContext(),Ustawienia.class);
                startActivity(i);
            }
        });


    }



}