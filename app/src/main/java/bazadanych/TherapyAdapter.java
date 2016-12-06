package bazadanych;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.pexpa.rejestrator.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by dawid on 06.12.2016.
 */

public class TherapyAdapter extends BaseAdapter {

    public ArrayList<Therapy> therapies;
    LayoutInflater inflater;
    public long which = -1;


    public TherapyAdapter(ArrayList<Therapy> therapies, Context context){
        inflater = LayoutInflater.from(context);
        this.therapies = therapies;
    }
    private class TherapiesListElement{
        TextView dateStart;
        TextView dateEnd;
        RadioButton checkedRadioButton;
    }


    @Override
    public int getCount() {
      if (therapies==null) return 0;
        else return therapies.size();
    }

    @Override
    public Object getItem(int i) {
        return therapies.get(i);
    }

    @Override
    public long getItemId(int i) {
       return therapies.get(i).getId();
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        TherapyAdapter.TherapiesListElement holder = null;
        if(convertView == null) {
            holder = new TherapyAdapter.TherapiesListElement();
            convertView = inflater.inflate(R.layout.row_therapy, null);
            holder.dateStart = (TextView) convertView.findViewById(R.id.row_therapy_tv_date_start);
            holder.dateEnd = (TextView) convertView.findViewById(R.id.row_therapy_tv_date_end);
            holder.checkedRadioButton = (RadioButton) convertView.findViewById(R.id.row_therapy_rb_checked);
            convertView.setTag(holder);
        } else {
            holder = (TherapyAdapter.TherapiesListElement) convertView.getTag();
        }
        holder.dateStart.setText("Terapia z dnia: " +sdf.format(therapies.get(position).getStartDate()));
        holder.checkedRadioButton.setChecked(therapies.get(position).isChecked());
        holder.checkedRadioButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                for(int aa=0; aa<therapies.size(); aa++){
                    therapies.get(aa).setChecked(false);
                }
                therapies.get(position).setChecked(true);
                notifyDataSetChanged();
                which = (int) therapies.get(position).getId();
            }
        });
        return convertView;
    }

}
