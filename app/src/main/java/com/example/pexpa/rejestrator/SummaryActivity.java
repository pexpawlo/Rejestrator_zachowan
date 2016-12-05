package com.example.pexpa.rejestrator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import bazadanych.DBManager;
import bazadanych.Event;
import bazadanych.Therapy;

public class SummaryActivity extends AppCompatActivity {

    long therapyId;
    DBManager db;
    TextView startTimeTextView;
    TextView endTimeTextView;
    TextView timeTextView;
    TextView eventsTextView;
    Button showGraphButton;
    Button mainMenuButton;
    @Override
    public void onBackPressed() {

        Intent setIntent = new Intent(getApplicationContext(),MainActivity.class);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(setIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        db = new DBManager(this);
        setTextViews();
        setMainMenuButton();
        setShowGraphButton();
    }

    private void setTextViews(){
        startTimeTextView = (TextView) findViewById(R.id.activity_summary_tv_start_time);
        endTimeTextView = (TextView) findViewById(R.id.activity_summary_tv_end_time);
        timeTextView = (TextView) findViewById(R.id.activity_summary_tv_time);
        eventsTextView = (TextView) findViewById(R.id.activity_summary_tv_events);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ArrayList<Therapy> therapy = db.getAllTherapies("id = "+extras.get("idTerapii"));
            therapyId = extras.getLong("idTerapii");
            Date start = therapy.get(0).getStartDate();
            Date end = therapy.get(0).getEndDate();
            startTimeTextView.setText( new SimpleDateFormat("yyyy-MM-dd HH:mm").format(start));
            endTimeTextView.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(end));
            long ms = end.getTime() - start.getTime();
            String therapyTime = String.format("%d min, %d s",
                    TimeUnit.MILLISECONDS.toMinutes(ms),
                    TimeUnit.MILLISECONDS.toSeconds(ms) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(ms))
            );
            timeTextView.setText(therapyTime);
            ArrayList<Event> events = db.getAllEvents(" event_time >= '" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(  therapy.get(0).getStartDate())+
                    "' AND " + " event_time <= '" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(  therapy.get(0).getEndDate()) +"'");
            eventsTextView.setText(events.size()+"");

        }
    }

    private void setMainMenuButton(){
        mainMenuButton=(Button)findViewById(R.id.activity_summary_btn_back_to_main_menu);
        mainMenuButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i=new Intent(getApplicationContext(),MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
    }

    private void setShowGraphButton(){
        showGraphButton=(Button)findViewById(R.id.activity_summary_btn_show_graph);
        showGraphButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i=new Intent(getApplicationContext(),SummaryGraphActivity.class);
                i.putExtra("idTerapii", therapyId);
                startActivity(i);
            }
        });
    }

}
