package com.example.pexpa.rejestrator;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import bazadanych.DBManager;
import bazadanych.Event;
import bazadanych.Therapy;

public class SummaryGraphActivity extends AppCompatActivity {

    TextView intervalsTextView;
    GraphView graph;
    int [] intervalValues = {1,5,10,15,20,30};
    DBManager db = new DBManager(this);
    SeekBar intervalsSeekBar;
    Button backToMainMenuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_graph);
        intervalsTextView = (TextView) findViewById(R.id.activity_summaty_graph_tv_intervals);
        graph = (GraphView) findViewById(R.id.activity_summary_graph_gv_graph);
        setBackToMainMenuButton();
        setIntervalsSeekBar();
        setGraph(0);



    }


    private void setBackToMainMenuButton(){
        backToMainMenuButton = (Button) findViewById(R.id.activity_summary_graph_btn_back_to_menu);
        backToMainMenuButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showBackToManMenuAlertDialog(MainActivity.class);
            }
        });
    }

    private void setGraph(final int interval){
        intervalsTextView.setText("Interwały: " +intervalValues[interval]+" min.");
        ArrayList<DataPoint> entries = new ArrayList<>();
        entries.add(new DataPoint(0,0));
        ArrayList<Therapy> therapies = db.getAllTherapies(" id ="+getIntent().getExtras().getLong("idTerapii"));
        long start = therapies.get(0).getStartDate().getTime();
        long end = therapies.get(0).getEndDate().getTime();
        long addTime = intervalValues[interval]*1000*60;
        SimpleDateFormat SDF =new  SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int k =1;
        int sizeMax = 0;

        for(long j= start;j<= end;j+=addTime){
            String startString = SDF.format(j);
            String endString = SDF.format(j+addTime);
            ArrayList<Event> EventList = db.getAllEvents(" event_time>='"+startString +"' AND event_time<'"+endString+"';");
           // if(EventList.size()>0)
            entries.add(new DataPoint(k,EventList.size()));
            if(sizeMax< EventList.size())
                sizeMax = EventList.size();
            k++;
        }
        DataPoint[] points = new DataPoint [entries.size()];
        for(int j=0; j<entries.size(); j++) points[j] = entries.get(j);
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(points);

        graph.removeAllSeries();
        graph.addSeries(series);
        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    if (value*intervalValues[interval] % 5 == 0) {
                        return value*intervalValues[interval]+" min";

                    } else return "";
                } else {

                    return super.formatLabel(value, isValueX);
                }
            }
        });
        graph.getGridLabelRenderer().setHorizontalLabelsAngle(90);
        graph.getGridLabelRenderer().setLabelHorizontalHeight(100);//TODO
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(10);
        graph.getViewport().setMaxY(sizeMax*1.5);
        graph.getViewport().setMinY(0);

        series.setSpacing(15);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setScrollable(true);
    }

    private void setIntervalsSeekBar(){
        intervalsSeekBar = (SeekBar) findViewById(R.id.activity_summary_graph_sb_intervals);
        intervalsSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                setGraph(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
    public boolean showBackToManMenuAlertDialog(final Class cls)
    {

        new AlertDialog.Builder(this)
                .setTitle("Zakończyć oglądanie wykresu?")
                .setMessage("Bedziesz mógł do niego wrócić poprzez opcję Przeglądaj wyniki.")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i=new Intent(getApplicationContext(),cls);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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