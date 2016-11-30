package com.example.pexpa.rejestrator;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.support.v4.util.LongSparseArray;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

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
    GraphView graph;
    int [] intervalValues = {1,5,10,15,20,30};
    DBManager db = new DBManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wykres);
         graph = (GraphView) findViewById(R.id.graph123);
      //  chart = (BarChart) findViewById(R.id.chart);
        textView = (TextView) findViewById(R.id.textViewWykres);
        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBarWykres);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                textView.setText("Interwały: " +intervalValues[i]+" min.");
                ArrayList<DataPoint> entries = new ArrayList<>();
                entries.add(new DataPoint(0,0));
                long patientID = getIntent().getExtras().getLong("idTerapii");
                ArrayList<Therapy> therapies = db.getAllTherapies(" id ="+getIntent().getExtras().getLong("idTerapii"));
                long start = therapies.get(0).getStartDate().getTime();
                long end = therapies.get(0).getEndDate().getTime();
                long addTime = intervalValues[i]*1000;
                SimpleDateFormat SDF =new  SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                int k =1;
                int sizeMax = 0;

                for(long j= start;j<= end;j+=addTime){
                    String startString = SDF.format(j);
                    String endString = SDF.format(j+addTime);
                    ArrayList<Event> EventList = db.getAllEvents(" event_time>='"+startString +"' AND event_time<'"+endString+"';");
                    if(EventList.size()>0)entries.add(new DataPoint(k,EventList.size()));
                    if(sizeMax< EventList.size())
                    sizeMax = EventList.size();
                    k++;
                }
                DataPoint[] points = new DataPoint [entries.size()];
                for(int j=0; j<entries.size(); j++) points[j] = entries.get(j);
                BarGraphSeries<DataPoint> series = new BarGraphSeries<>(points);

                graph.removeAllSeries();
               graph.addSeries(series);

                graph.getViewport().setMinX(0);
               graph.getViewport().setMaxX(10);
                graph.getViewport().setMaxY(sizeMax*1.5);
                graph.getViewport().setMinY(0);

               series.setSpacing(15);
                graph.getViewport().setXAxisBoundsManual(true);
                graph.getViewport().setYAxisBoundsManual(true);
               graph.getViewport().setScrollable(true);

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


        Random r = new Random();


        DataPoint[] points = new DataPoint[2];
        points[0] = new DataPoint(0,0);
        for (int i = 1; i < points.length; i++) {
            points[i] = new DataPoint(i, r.nextInt(25));
        }

        //graph = (GraphView) findViewById(R.id.graph);
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(points);
        //series.
        graph.addSeries(series);
        series.setSpacing(15);
        graph.getViewport().setMinX(0);
       graph.getViewport().setMaxX(10);

        graph.getViewport().setXAxisBoundsManual(true);

        graph.getViewport().setScrollable(true); // enables horizontal scrolling


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