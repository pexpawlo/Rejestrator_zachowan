package bazadanych;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by dawid on 27.11.2016.
 */

public class TherapyAdapter extends BaseAdapter {
    private ArrayList<Therapy> therapies;
    private LayoutInflater inflater;
    @Override
    public int getCount() {
        return therapies.size();
    }

    @Override
    public Object getItem(int position) {
        return therapies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return therapies.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
