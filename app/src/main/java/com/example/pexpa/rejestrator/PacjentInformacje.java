package com.example.pexpa.rejestrator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PacjentInformacje extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pacjent_informacje);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int which = extras.getInt("id_pacjenta");
        }
    }
}
