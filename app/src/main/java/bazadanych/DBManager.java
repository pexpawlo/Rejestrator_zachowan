package bazadanych;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * Created by dawid on 19.11.2016.
 */

public class DBManager {

    private SQLiteDatabase database;
    private Context context;
    private DatabaseHelper dbHelper;
    private static final int DB_VERSION = 4;
    private static final String DB_NAME = "autism_therapy.db";
    private static final String DB_TABLE_EVENT = "events";
    private static final String DB_TABLE_PATIENT = "patients";
    private static final String DB_TABLE_THERAPY = "therapies";

    public DBManager(Context context) {
        this.context = context;
    }

    public DBManager open() {
        dbHelper = new DatabaseHelper(context, DB_NAME, null, DB_VERSION);
        try {
            database = dbHelper.getWritableDatabase();
        } catch (SQLException e) {
            database = dbHelper.getReadableDatabase();
        }

        return this;
    }

    public void close() {
        dbHelper.close();
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context, String name,
                              SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + DB_TABLE_PATIENT + "( id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, surname TEXT NOT NULL );");
            db.execSQL("CREATE TABLE " + DB_TABLE_THERAPY + "(id INTEGER PRIMARY KEY AUTOINCREMENT, patient_id INTEGER NOT NULL, start_time DATETIME, end_time DATETIME );");
            db.execSQL("CREATE TABLE " + DB_TABLE_EVENT + " ( id INTEGER PRIMARY KEY AUTOINCREMENT, patient_id INTEGER NOT NULL, event_time DATETIME );");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("delete from patients where id >20;");
         /*   db.execSQL("drop table "+ DB_TABLE_PATIENT+";");
            db.execSQL("CREATE TABLE " + DB_TABLE_PATIENT + "( id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, surname TEXT NOT NULL );");
            db.execSQL("CREATE TABLE " + DB_TABLE_THERAPY + "(id INTEGER PRIMARY KEY AUTOINCREMENT, patient_id INTEGER NOT NULL, start_time DATETIME, end_time DATETIME );");
            db.execSQL("CREATE TABLE " + DB_TABLE_EVENT + " ( id INTEGER PRIMARY KEY AUTOINCREMENT, patient_id INTEGER NOT NULL, event_time DATETIME );");
*/
        }
    }

    public long insertPatient(String name, String surname) {
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("surname", surname);
        return database.insert(DB_TABLE_PATIENT, null, cv);
    }

    public long insertEvent(long patient_id, Date eventTime) {
        ContentValues cv = new ContentValues();
        cv.put("patient_id", patient_id);
        cv.put("event_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(eventTime));
        return database.insert(DB_TABLE_EVENT, null, cv);
    }

    public long insertTherapy(long patient_id, Date startTime, Date endTime) {
        ContentValues cv = new ContentValues();
        cv.put("patient_id", patient_id);
        cv.put("start_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(startTime));
        cv.put("end_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(endTime));
        return database.insert(DB_TABLE_THERAPY, null, cv);
    }

    public boolean deletePatient(long id) {
        if (database.delete(DB_TABLE_PATIENT, " id = " + id, null) > 0) return true;
        else return false;
    }

    public boolean deleteEvent(long id) {
        if (database.delete(DB_TABLE_EVENT, " id = " + id, null) > 0) return true;
        else return false;
    }

    public boolean deleteTheapy(long id) {
        if (database.delete(DB_TABLE_THERAPY, " id = " + id, null) > 0) return true;
        else return false;
    }

    public ArrayList<Patient> getAllPatients(String whereClause) {
        ArrayList<Patient> patients = new ArrayList<Patient>();
        String[] columns = {"id", "name", "surname"};
        open();

        Cursor cursor = database.query(DB_TABLE_PATIENT, columns, whereClause, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast() && cursor.getCount() > 0) {
            patients.add(new Patient(
                    cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getString(cursor.getColumnIndex("surname"))));
            cursor.moveToNext();
        }
        close();
        return patients;
    }

    public ArrayList<Event> getAllEvents(String whereClause) {
        ArrayList<Event> events = new ArrayList<Event>();
        String columns[] = {"id", "patient_id", "event_time"};
        open();
        Cursor cursor = database.query(DB_TABLE_EVENT, columns, whereClause, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast() && cursor.getCount() > 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                events.add(new Event(
                        cursor.getLong(cursor.getColumnIndex("id")),
                        cursor.getLong(cursor.getColumnIndex("patient_id")),
                        sdf.parse(cursor.getString(cursor.getColumnIndex("event_time")))
                ));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            cursor.moveToNext();
        }
        close();
        return events;
    }

    public ArrayList<Therapy> getAllTherapies(String whereClause) {
        ArrayList<Therapy> therapies = new ArrayList<Therapy>();
        String columns[] = {"id", "patient_id", "start_time", "end_time"};
        open();
        Cursor cursor = database.query(DB_TABLE_THERAPY, columns, whereClause, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast() && cursor.getCount() > 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                therapies.add(new Therapy(
                        cursor.getLong(cursor.getColumnIndex("id")),
                        cursor.getLong(cursor.getColumnIndex("patient_id")),
                        sdf.parse(cursor.getString(cursor.getColumnIndex("start_time"))),
                        sdf.parse(cursor.getString(cursor.getColumnIndex("end_time")))
                ));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            cursor.moveToNext();
        }
        close();
        return therapies;
    }

    public void updateTherapy(long patient_id, Date startTime, Date endTime, long idToUpdate) {
        ContentValues cv = new ContentValues();
        cv.put("patient_id", patient_id);
        cv.put("start_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(startTime));
        cv.put("end_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(endTime));
        database.update(DB_TABLE_THERAPY, cv, " id = " + idToUpdate, null);
    }

    public void updatePatient(long idToUpdate, String name, String surname){
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("surname", surname);
        database.update(DB_TABLE_PATIENT,cv," id = " + idToUpdate, null);
    }

}

