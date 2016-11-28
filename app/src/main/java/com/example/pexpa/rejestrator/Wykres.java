package com.example.pexpa.rejestrator;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.util.LongSparseArray;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bazadanych.DBManager;
import bazadanych.Event;
import bazadanych.HourFormatter;
import bazadanych.Therapy;

public class Wykres extends AppCompatActivity {
    BarChart chart;
    ArrayList<BarEntry> BARENTRY;
    ArrayList<String> BarEntryLabels;
    BarDataSet Bardataset;
    BarData BARDATA;
    TextView textView;
    int [] intervalValues = {1,5,10,15,20,30};
    DBManager db = new DBManager(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wykres);
        chart = (BarChart) findViewById(R.id.chart);
        textView = (TextView) findViewById(R.id.textViewWykres);
        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBarWykres);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                textView.setText("Interwały: " +intervalValues[i]+" min.");
                List<BarEntry> entries = new ArrayList<>();
long patientID = getIntent().getExtras().getLong("idTerapii");
ArrayList<Therapy> therapies = db.getAllTherapies(" id ="+getIntent().getExtras().getLong("idTerapii"));
                long start = therapies.get(0).getStartDate().getTime();
                long end = therapies.get(0).getEndDate().getTime();
                long addTime = intervalValues[i]*1000;
                SimpleDateFormat SDF =new  SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                int k =0;
                int sizeMax = 0;

                for(long j= start;j<= end;j+=addTime){
                    String startString = SDF.format(j);
                    String endString = SDF.format(j+addTime);
                    ArrayList<Event> EventList = db.getAllEvents(" event_time>='"+startString +"' AND event_time<'"+endString+"';");
                    if(EventList.size()>0)entries.add(new BarEntry((float) (k-0.5),EventList.size()));
                    if(sizeMax< EventList.size())
                    sizeMax = EventList.size();
                    k++;
                }
                chart.getXAxis().setValueFormatter(new HourFormatter(intervalValues[i]));

sizeMax*=1.5;
                BarDataSet set = new BarDataSet(entries, "BarDataSet");
                BarData data = new BarData(set);
                //data.setBarWidth(0.9f); // set custom bar width
                chart.getAxisLeft().setAxisMinimum(0);
                chart.getAxisLeft().setAxisMaximum(sizeMax);
                chart.getAxisRight().setAxisMinimum(0);
                chart.getAxisRight().setAxisMaximum(sizeMax);
                chart.setData(data);


                //chart.setFitBars(true); // make the x-axis fit exactly all bars
                chart.invalidate(); // refresh
//chart.getXAxis().setGranularityEnabled(true);
               // chart.getXAxis().setGranularity(intervalValues[i]);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        Button WracajMenu = (Button) findViewById(R.id.wrocDoMenu);
        WracajMenu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                pytanieWykres(MainActivity.class);
            }
        });


        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0f, 30f));
        entries.add(new BarEntry(1f, 80f));
        entries.add(new BarEntry(2f, 60f));
        entries.add(new BarEntry(3f, 50f));
        // gap of 2f
        entries.add(new BarEntry(5f, 70f));
        entries.add(new BarEntry(6f, 60f));

        BarDataSet set = new BarDataSet(entries, "BarDataSet");

        BarData data = new BarData(set);
        data.setBarWidth(0.9f); // set custom bar width
        chart.setData(data);
        chart.setFitBars(true); // make the x-axis fit exactly all bars
        chart.invalidate(); // refresh
    }




    public boolean pytanieWykres(final Class cls)
    {

        new AlertDialog.Builder(this)
                .setTitle("Zakończyć oglądanie wykresu?")
                .setMessage("Bedziesz mógł do niego wrócić poprzez opcję Przeglądaj wyniki.")
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
}