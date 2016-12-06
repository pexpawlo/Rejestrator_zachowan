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
import android.widget.SeekBar;
import android.widget.TextView;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import bazadanych.DBManager;
import bazadanych.Event;
import bazadanych.Therapy;

public class CalendarGraphActivity extends AppCompatActivity {

    TextView intervalsTextView;
    GraphView graph;
    ArrayList<Therapy> therapies;
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
        patientID = getIntent().getExtras().getLong("id_pacjenta");

        therapies = db.getAllTherapies(" patient_id =" + patientID);

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
        ArrayList<DataPoint> entries = new ArrayList<>();
        entries.add(new DataPoint(0, 0));
        long addTime = 60 * 60 * 24 * 1000;



        long start = dataStart;
        long end = dataStop;
        int xAxisPosition = 0;
        int sizeMax = 0;

        int yValue = 0;
        for (long j = start; j <= end; j += addTime) {//365
            for (long p = j; p < j + addTime; p += intervalValues[interval] * 1000 * 60) {
                boolean eventOccured = false;
                for (int l = 0; l < eventList.size(); l++) {

                    if (eventList.get(l).getPatientId() == patientID && eventList.get(l).getDate().getTime() >= p && eventList.get(l).getDate().getTime() < p + intervalValues[interval] * 1000 * 60) {
                        eventOccured = true;
                        break;
                    }
                }
                if (eventOccured == true) yValue++;
            }
            boolean thepapyOccured = false;
            if(yValue>0){
                thepapyOccured = true;
            }
            else{
            for (int o = 0; o < therapies.size(); o++) {

                if (therapies.get(o).getStartDate().getTime() >= j && therapies.get(o).getEndDate().getTime() < j + addTime) {
                    thepapyOccured = true;
                    break;
                }
            }
            }
            if (thepapyOccured) {
                entries.add(new DataPoint(xAxisPosition, yValue));
            }
            if (sizeMax < yValue)
                sizeMax = yValue;
            xAxisPosition++;
            yValue = 0;
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
                    if (value % 7 == 0) {
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
    }

    private void setIntervalsSeekBar() {
        intervalsSeekBar = (SeekBar) findViewById(R.id.activity_calendar_graph_sb_intervals);
        intervalsSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                setGraph(0,dataStart.getTime(),dataStop.getTime(),patientID);
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
