package bazadanych;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by dawid on 27.11.2016.
 */

public class EventAdapter extends BaseAdapter {

    ArrayList<Event> events;
    LayoutInflater inflater;

    public EventAdapter(ArrayList<Event> events, Context context){
        inflater = LayoutInflater.from(context);
        this.events = events;
    }
    @Override
    public int getCount() {
        return events.size();
    }

    @Override
    public Object getItem(int position) {
        return events.get(position);
    }

    @Override
    public long getItemId(int position) {
        return events.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
