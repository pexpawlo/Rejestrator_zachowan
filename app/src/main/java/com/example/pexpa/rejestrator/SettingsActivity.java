package com.example.pexpa.rejestrator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class SettingsActivity extends AppCompatActivity {
    Button editPatientsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setEditPatientsButton();
    }

    private void setEditPatientsButton() {
        editPatientsButton = (Button) findViewById(R.id.activity_settings_btn_edit_patients);
        editPatientsButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), EditPatientsActivity.class);
                startActivity(i);
            }
        });
    }
}
