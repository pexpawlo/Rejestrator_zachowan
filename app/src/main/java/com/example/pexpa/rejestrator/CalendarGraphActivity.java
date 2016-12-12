package com.example.pexpa.rejestrator;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import bazadanych.DBManager;
import bazadanych.Event;
import bazadanych.Patient;
import bazadanych.Therapy;

public class CalendarGraphActivity extends AppCompatActivity {
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    ArrayList<DataPoint> entries;
    TextView intervalsTextView;
    Button exportToCsv;
    GraphView graph;
    int[] intervalValues = {1, 5, 10, 15, 20, 30};
    DBManager db = new DBManager(this);
    SeekBar intervalsSeekBar;
    Button backToMainMenuButton;
    ArrayList<Event> eventList;
    Date dataStart = new Date();
    Date dataStop = new Date();
    long patientID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_graph);
        exportToCsv = (Button) findViewById(R.id.activity_calendar_graph_btn_export_to_csv);
        exportToCsv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int permission = ActivityCompat.checkSelfPermission(CalendarGraphActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

                if (permission != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                            CalendarGraphActivity.this,
                            PERMISSIONS_STORAGE,
                            REQUEST_EXTERNAL_STORAGE
                    );
                }

                if (permission == PackageManager.PERMISSION_GRANTED) {
                    try {

                        File root = new File(Environment.getExternalStorageDirectory(), "TerapiaAutyzm");
                        if (!root.exists()) {
                            root.mkdirs();
                        }
                        List<Patient> patients = db.getAllPatients("id = "+patientID);
                        String fileName = patients.get(0).getName() + "_" + patients.get(0).getSurname() +"_" + getIntent().getExtras().getString("start_date")+"_"+ getIntent().getExtras().getString("end_date");
                        File gpxfile = new File(root, fileName);
                        gpxfile.createNewFile();
                        FileWriter writer = new FileWriter(gpxfile);
                        for (int i = 0; i < entries.size(); i++) {
                            writer.append(entries.get(i).getX() + "," + entries.get(i).getY() + "\n");
                        }
                        writer.flush();
                        writer.close();
                        Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        patientID = getIntent().getExtras().getLong("id_pacjenta");

        final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            dataStart = SDF.parse(getIntent().getExtras().getString("start_date")+" 00:00:00");
            dataStop = SDF.parse(getIntent().getExtras().getString("end_date")+" 23:59:59");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        eventList = db.getAllEvents(" patient_id =" + patientID + " AND event_time>='" + SDF.format(dataStart) + "' AND event_time<'" + SDF.format(dataStop) + "';");
        intervalsTextView = (TextView) findViewById(R.id.activity_calendar_graph_tv_intervals);
        graph = (GraphView) findViewById(R.id.activity_calendar_graph_gv_graph);
        setBackToMainMenuButton();
        setIntervalsSeekBar();
        setGraph(0,dataStart.getTime(),dataStop.getTime(),patientID);

    }

    private void setBackToMainMenuButton() {
        backToMainMenuButton = (Button) findViewById(R.id.activity_calendar_graph_btn_back_to_menu);
        backToMainMenuButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                showBackToManMenuAlertDialog(MainActivity.class);
            }
        });
    }

    private void setGraph(int interval,long dataStart,long dataStop,long patientID ) {

        intervalsTextView.setText("Interwały: " + intervalValues[interval] + " min.");
        entries = new ArrayList<>();
        entries.add(new DataPoint(0, 0));
        Long intervalMilis = new Long(intervalValues[interval]*1000*60);
        Map<Long,Boolean> mapa = new HashMap<Long,Boolean>();
        for(int i=0; i<eventList.size(); i++){

        long timeFromStart = eventList.get(i).getDate().getTime() - dataStart;
            Long which = new Long (timeFromStart/intervalMilis);
            mapa.put(which,true);
        }
        long dayInMillis = (24*60*60*1000)/(intervalValues[interval]*1000*60);
        Map<Long, Long> wykres = new HashMap<>();
        for (Long a: mapa.keySet()) {
            long xd = a/dayInMillis;
            Long number = wykres.get(xd);
            if(number==null) number = new Long(1);
            else number++;
            wykres.put(xd,number);
        }
     List<Long> keys = new ArrayList<Long>();
        for (Long a: wykres.keySet()) {
            keys.add(a);
        }
        Collections.sort(keys);
        for(int i=0; i< keys.size(); i++){
            entries.add(new DataPoint(keys.get(i), wykres.get(keys.get(i))));
        }
        DataPoint[] points = new DataPoint[entries.size()];
        for (int j = 0; j < entries.size(); j++) points[j] = entries.get(j);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(points);

        series.setDrawDataPoints(true);
        graph.removeAllSeries();
        graph.addSeries(series);
        graph.getGridLabelRenderer().setHorizontalLabelsAngle(90);
        graph.getGridLabelRenderer().setLabelHorizontalHeight(115);//TODO
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(30);
        graph.getViewport().setMaxY(30);
        graph.getViewport().setMinY(0);
        graph.getGridLabelRenderer().setNumHorizontalLabels(31);

        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    Date date = new Date();
                    date.setTime((long) (date.getTime() + value * 60 * 60 * 24 * 1000));
                    if (value %7 == 0) {


                        return new SimpleDateFormat("yyyy-MM-dd").format(date);

                    } else return "";
                } else {

                    return super.formatLabel(value, isValueX);
                }
            }
        });
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setScrollable(true);
        graph.getViewport().setScalable(true);
       // graph.getGridLabelRenderer().setNumHorizontalLabels(5);
    }

    private void setIntervalsSeekBar() {
        intervalsSeekBar = (SeekBar) findViewById(R.id.activity_calendar_graph_sb_intervals);
        intervalsSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                setGraph(i,dataStart.getTime(),dataStop.getTime(),patientID);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public boolean showBackToManMenuAlertDialog(final Class cls) {

        new AlertDialog.Builder(this)
                .setTitle("Zakończyć oglądanie wykresu?")
                .setMessage("Bedziesz mógł do niego wrócić poprzez opcję Przeglądaj wyniki.")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(getApplicationContext(), cls);
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
