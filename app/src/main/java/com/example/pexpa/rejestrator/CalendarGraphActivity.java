package com.example.pexpa.rejestrator;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class CalendarGraphActivity extends AppCompatActivity {
int a,b,c;
    DatePickerDialog DPD;
    EditText ET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_graph);
        ET = (EditText) findViewById(R.id.editText3);

        showBackToManMenuAlertDialog();
        ET.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

           DPD.show();
ET.setText("dasa");
            }
        });
    }



    public boolean showBackToManMenuAlertDialog()
    {

       DPD = new DatePickerDialog(this,new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                //ET.setText(dateFormatter.format(newDate.getTime()));
            }

        },a,b,c);



return true;
    }

}
