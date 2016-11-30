package bazadanych;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

/* aaa
    DBManager db;
    EditText name;
    EditText nazwisko;
    TextView tv;
    ArrayList<Patient> patients;
    PatientAdapter adapter;
    ArrayList<Event> events;
  //  @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

       // db = new DBManager(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            randomData();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(4f, 0));
        entries.add(new BarEntry(8f, 1));
        entries.add(new BarEntry(6f, 2));
        entries.add(new BarEntry(12f, 3));
        entries.add(new BarEntry(18f, 4));
        entries.add(new BarEntry(9f, 5));
        BarDataSet dataset = new BarDataSet(entries, "# of Calls");
String labels[] = {"January", "February", "March", "April", "May", "June"};


        BarChart chart = (BarChart) findViewById(R.id.chart);
        dataset.setStackLabels(labels);
        BarData data = new BarData(dataset);

//db.i
        chart.setData(data);
     /*   View xd = findViewById(R.id.button2);
        xd.setOnClickListener(new View.OnClickListener(){

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                db.open();
                events = db.getAllEvents(null);
                db.close();
            }
        });
        patients = new ArrayList<Patient>();
         name = (EditText) findViewById(R.id.editText);
         nazwisko = (EditText) findViewById(R.id.editText2);
        Button btn  = (Button) findViewById(R.id.button);
         tv = (TextView) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                long xd=12345;
            db.open();
         xd =   db.insertPatient(name.getText().toString(), nazwisko.getText().toString());
            db.close();
            tv.setText(xd+"");
                adapter.patients = db.getAllPatients();
                adapter.notifyDataSetChanged();
        }});
        ListView listView = (ListView) findViewById(R.id.listView);
     //   Cursor xd;
     //   db.open();
     //   xd=db.getAllTodos();
//patients = new Patient[xd.getCount()];

    //    xd.moveToFirst();

patients=db.getAllPatients();
        db.close();
     adapter = new PatientAdapter(patients,this);
       // adapter.notifyDataSetChanged();
listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
            /*    db.open();
                db.deletePatient(id);
                patients = db.getAllPatients();
                db.close();
                adapter.patients = patients;
                adapter.notifyDataSetChanged(); *


                ColorDrawable color = (ColorDrawable) view.getBackground();

                if(color == null || color.getColor()==Color.BLACK) view.setBackgroundColor(Color.BLUE);
                else view.setBackgroundColor(Color.BLACK);
            }
        });

    }




    //@RequiresApi(api = Build.VERSION_CODES.N)
  //  @RequiresApi(api = Build.VERSION_CODES.N)
    public void randomData() throws ParseException {

/*dodajemy 20 losowych pacjentów */
     /*   db.insertPatient("Jakub",  "Woźniak");
        db.insertPatient("Tomasz", "Wieczorek");
        db.insertPatient("Julia", "Wróblewska");
        db.insertPatient("Natalia", "Pawlik");
        db.insertPatient("Igor", "Kucharski");
        db.insertPatient("Kacper", "Sosnowski");
        db.insertPatient("Maksymilian", "Niemiec");
        db.insertPatient("Krystian", "Dąbrowski");
        db.insertPatient("Mikołaj", "Tomczyk");
        db.insertPatient("Wiktoria", "Kowalska");
        db.insertPatient("Leon", "Bielecki");
        db.insertPatient("Zuzanna", "Chmielewska");
        db.insertPatient("Anastazja", "Wysocka");
        db.insertPatient("Joanna", "Sosnowska");
        db.insertPatient("Gabriel", "Jakubowski");
        db.insertPatient("Julia", "Łuczak");
        db.insertPatient("Julia", "Nowicka");
        db.insertPatient("Aleksandra", "Orzechowska");
        db.insertPatient("Fabian", "Zieliński");
        db.insertPatient("Michał", "Urbaniak");
        Random r = new Random();
/* dodajemy losowe daty terapii
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


        for(int i=0; i<250; i++){
            String year = (2010+r.nextInt(10))+"";
            String month = (r.nextInt(12)+1)+"";
            String day = (r.nextInt(28)+1)+"";

            String hour =  (r.nextInt(24))+"";
            String min = (r.nextInt(60))+"";
            String sec = (r.nextInt(60))+"";


            String date = year + "-" +(month.length()==1?"0"+month:month) + "-" + (day.length()==1?"0"+day:day) + " " + (hour.length()==1?"0"+hour:hour) +":"+(min.length()==1?"0"+min:min)+":"+(sec.length()==1?"0"+sec:sec);
            Date start = sdf.parse(date);
            long time = start.getTime();
            time+=(r.nextInt(4)+1)*1*1000*60*60;
            time+=r.nextInt(60)*1*1000*60;
            Date end = new Date(time);

            long timeBetween = end.getTime() - start.getTime();
            System.out.println("START: " + sdf.format(start)+"   KONIEC:  "+sdf.format(end));
            int events = r.nextInt(25)+1;
            for(int j=0; j<events; j++){
                long newTime= (long) (timeBetween*r.nextFloat());
                Date date123 = new Date(start.getTime()+newTime);
                System.out.println(sdf.format(date123));
            }

        }
    } */
}
