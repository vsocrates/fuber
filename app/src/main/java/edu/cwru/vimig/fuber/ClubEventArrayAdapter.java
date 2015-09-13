package edu.cwru.vimig.fuber;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import android.content.Context;
import android.widget.TextView;

/**
 * Created by Omkar on 9/12/2015.
 */
public class ClubEventArrayAdapter extends ArrayAdapter<ClubEvent> {

    private final Context context;
    private final ArrayList<ClubEvent> events;

    public ClubEventArrayAdapter(Context context, ArrayList<ClubEvent> events) {
        super(context, -1, events);
        this.context = context;
        this.events = events;;
    }

    public void addEvent(ClubEvent event) {
        events.add(event);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item = inflater.inflate(R.layout.layout_event, parent, false);
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yy");
        ((TextView) item.findViewById(R.id.event_title)).setText(events.get(position).title);
        ((TextView) item.findViewById(R.id.event_date)).setText(sdf1.format(events.get(position).calendar.getTime()));
        ((TextView) item.findViewById(R.id.event_time)).setText(sdf.format(events.get(position).calendar.getTime()));
        return item;
    }
}
