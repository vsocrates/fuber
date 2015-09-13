package edu.cwru.vimig.fuber;

import android.media.Image;
import android.text.method.DateTimeKeyListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Omkar on 9/12/2015.
 */
public class ClubEvent {
    public final int id;
    public final Calendar calendar;
    public final String title;
    public final String description;
    public final double latitude;
    public final double longitude;

    public ClubEvent(JSONObject jsonObject) throws Exception
    {
        this.id = jsonObject.getInt("id");
        this.calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            calendar.setTime(sdf.parse(jsonObject.getString("start_time")));
        } catch(Exception e) {
            calendar.setTime(sdf1.parse(jsonObject.getString("start_time")));
        }
        this.title = jsonObject.getString("name");
        this.description = jsonObject.getString("description");
        if(jsonObject.getJSONObject("place").has("location")) {
            this.latitude = jsonObject.getJSONObject("place").getJSONObject("location").getDouble("latitude");
            this.longitude = jsonObject.getJSONObject("place").getJSONObject("location").getDouble("longitude");
        } else {
            this.latitude = 0.0;
            this.longitude = 0.0;
        }
    }
}
