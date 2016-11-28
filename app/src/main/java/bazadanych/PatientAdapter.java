package bazadanych;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.pexpa.rejestrator.R;

import java.util.ArrayList;

/**
 * Created by dawid on 21.11.2016.
 */

public class PatientAdapter extends BaseAdapter {

    ArrayList<Patient> patients;
    LayoutInflater inflater;
    public int which = -1;
    public PatientAdapter(ArrayList<Patient> patients, Context context){
        inflater = LayoutInflater.from(context);
        this.patients = patients;
    }
    private class PatientListElement{
        public TextView textView1;
        public RadioButton isChecked;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        PatientListElement holder = null;
        if(convertView == null) {
            holder = new PatientListElement();
            convertView = inflater.inflate(R.layout.list, null);
            holder.textView1 = (TextView) convertView.findViewById(R.id.code);
            holder.isChecked = (RadioButton) convertView.findViewById(R.id.radioButton);

            convertView.setTag(holder);
        } else {
            holder = (PatientListElement) convertView.getTag();
        }
        holder.textView1.setText(patients.get(position).getName() +" "+patients.get(position).getSurname());
        holder.isChecked.setChecked(patients.get(position).isChecked());
        holder.isChecked.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                for(int aa=0; aa<patients.size(); aa++){
                    patients.get(aa).checked = false;
                }
                patients.get(position).checked = true;
                notifyDataSetChanged();
                which = (int) patients.get(position).getId();
            }
        });
        return convertView;
    }
}
