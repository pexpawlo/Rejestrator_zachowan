package com.example.pexpa.rejestrator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class EditPatientActivity extends AppCompatActivity {


    long patientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_patient);
        getExtras();
    }

    private void getExtras(){
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            patientId = extras.getInt("id_pacjenta");
        }
    }
}
