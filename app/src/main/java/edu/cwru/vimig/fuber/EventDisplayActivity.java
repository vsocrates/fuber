package edu.cwru.vimig.fuber;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

public class EventDisplayActivity extends AppCompatActivity {

    private ClubEventArrayAdapter adapter;
    private ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_display);

        listview = (ListView) findViewById(R.id.group_view);
        final EventDisplayActivity temp = this;

        adapter = new ClubEventArrayAdapter(this, new ArrayList<ClubEvent>());

        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            final EventDisplayActivity activity = temp;
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent openMapIntent = new Intent(activity, FindGroup.class);
                startActivity(openMapIntent);
            }
        });

        /* make the API call */
        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/events", null, HttpMethod.GET, new GraphRequest.Callback() {
            EventDisplayActivity activity = temp;

            public void onCompleted(GraphResponse response) {
                try {
                    JSONObject event_response = response.getJSONObject();
                    JSONArray events = event_response.getJSONArray("data");
                    System.out.println(events.length());
                    for (int i = 0; i < events.length(); i++) {
                        adapter.addEvent(new ClubEvent(events.getJSONObject(i)));
                    }
                    adapter.notifyDataSetChanged();
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
        ).executeAsync();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
