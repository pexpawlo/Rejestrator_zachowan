package com.example.pexpa.rejestrator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class ChooseGraphTypeActivity extends AppCompatActivity {

    Button startTherapiesListButton;
    Button startCalendarGraphButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_graph_type);
        setStartTherapiesListButton();
        setStartCalendarGraphButton();
    }

    private void setStartTherapiesListButton(){
        startTherapiesListButton = (Button) findViewById(R.id.activity_choose_graph_type_btn_start_therapies_list);
        startTherapiesListButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i=new Intent(getApplicationContext(),TherapiesListActivity.class);
                i.putExtra("id_pacjenta", 1);//TODO
                startActivity(i);
            }
        });
    }
    private void setStartCalendarGraphButton(){
        startCalendarGraphButton = (Button) findViewById(R.id.activity_choose_graph_type_btn_calendar_graph);
        startCalendarGraphButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i=new Intent(getApplicationContext(),CalendarGraphActivity.class);
                i.putExtra("id_pacjenta", 1);//TODO
                startActivity(i);
            }
        });
    }
}
