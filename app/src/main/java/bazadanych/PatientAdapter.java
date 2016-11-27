package bazadanych;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.pexpa.rejestrator.R;

import java.util.ArrayList;

/**
 * Created by dawid on 21.11.2016.
 */

public class PatientAdapter extends BaseAdapter {

    ArrayList<Patient> patients;
    LayoutInflater inflater;

    public PatientAdapter(ArrayList<Patient> patients, Context context){
        inflater = LayoutInflater.from(context);
        this.patients = patients;
    }
    private class PatientListElement{
       public TextView textView1;
    }

    //Patient
    @Override
    public int getCount() {
        if(patients!=null){
        return patients.size();}
        else {return 0;}
    }

    @Override
    public Object getItem(int position) {
      return  patients.get(position);
    }

    @Override
    public long getItemId(int position) {
        return patients.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PatientListElement holder = null;
        if(convertView == null) {
            holder = new PatientListElement();
            convertView = inflater.inflate(R.layout.list, null);
            holder.textView1 = (TextView) convertView.findViewById(R.id.code);

            convertView.setTag(holder);
        } else {
            holder = (PatientListElement) convertView.getTag();
        }
        holder.textView1.setText(patients.get(position).getName() +" "+patients.get(position).getSurname());

        return convertView;
    }
}
