package com.example.pexpa.rejestrator;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import bazadanych.DBManager;
import bazadanych.Patient;

public class ChooseDateActivity extends AppCompatActivity {

    EditText startDate;
    EditText endDate;
    Calendar date;
    boolean isStartDate;
    TextView patientTextView;
    Button button;
    Date dateFirst;
    Date dateSecond;
    DatePickerDialog.OnDateSetListener setTodayDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_date);
        startDate = (EditText) findViewById(R.id.activity_choose_date_et_start);
        endDate = (EditText) findViewById(R.id.activity_choose_date_et_end);
        startDate.setHint("np. 2000-01-01");
        endDate.setHint("np. 2000-01-31");
        patientTextView = (TextView) findViewById(R.id.activity_choose_date_tv_patient);
        DBManager db = new DBManager(this);
        Patient patient = db.getAllPatients(" id = "+getIntent().getExtras().getLong("id_pacjenta")).get(0);
        patientTextView.setText("Wybrano pacjenta: "+patient.getName()+" "+patient.getSurname());
        startDate.setFocusable(false);
        endDate.setFocusable(false);
        isStartDate = false;

        date = Calendar.getInstance();
        String myFormat = "yyyy-MM-dd";
        final SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        setTodayDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                date.set(Calendar.YEAR, year);
                date.set(Calendar.MONTH, monthOfYear);
                date.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        startDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                isStartDate = true;
                new DatePickerDialog(ChooseDateActivity.this, setTodayDate, date
                        .get(Calendar.YEAR), date.get(Calendar.MONTH),
                        date.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        endDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                 isStartDate = false;
                new DatePickerDialog(ChooseDateActivity.this, setTodayDate, date
                        .get(Calendar.YEAR), date.get(Calendar.MONTH),
                        date.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
    button = (Button) findViewById(R.id.activity_choose_date_btn_show_graph);
    button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            try {
                 dateFirst = sdf.parse(startDate.getText().toString());
                 dateSecond = sdf.parse(endDate.getText().toString());
                System.out.println(date);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            if(dateFirst==null || dateSecond==null || dateFirst.getTime()>dateSecond.getTime()){
                if(dateFirst==null) startDate.setError("To pole nie może być puste.");
                if(dateSecond==null) endDate.setError("To pole nie moze być puste.");
                if(dateFirst.getTime()>dateSecond.getTime()) endDate.setError("Data zakończenia nie może być późniejsza od daty rozpoczęcia.");
            }

            else {
                Intent i = new Intent(getApplicationContext(), CalendarGraphActivity.class);
                i.putExtra("id_pacjenta", getIntent().getExtras().getLong("id_pacjenta"));
                i.putExtra("start_date", startDate.getText().toString());
                i.putExtra("end_date", endDate.getText().toString());
                startActivity(i);
            }
        }
    });
    }
    private void updateLabel() {

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
if(isStartDate)
        startDate.setText(sdf.format(date.getTime()));
        else
    endDate.setText(sdf.format(date.getTime()));
    }






}
