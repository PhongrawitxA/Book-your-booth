package com.example.tester;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HistoryActivity extends AppCompatActivity {
//        ProgressDialog mProgressDialog;
        Toolbar toolbar;
        ListView listview;
        TextView textViewFullname;
        User a = new User();
        String Username ;
        public static final String KEY_USER = "username";
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_history);

            HttpsTrustManager.allowAllSSL();
            toolbar = (Toolbar) findViewById(R.id.toolbar_history);
            setSupportActionBar(toolbar);
            toolbar.setNavigationIcon(R.drawable.ic_arrowbackward);
            getSupportActionBar().setDisplayShowTitleEnabled(false);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            textViewFullname = findViewById(R.id.fullname_history);
            getData();
            listview = findViewById(R.id.ListView_history);
            GetMatchData();
        }

    private void GetMatchData() {

        Username = a.getUser().trim();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConfigHistory.MATCHDATA_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")) {

                            showJSON(response);

                        } else {

                            showJSON(response);


                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(HistoryActivity.this, ""+error, Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put(KEY_USER,Username);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response) {
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(ConfigHistory.JSON_ARRAY);

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String event = jo.getString(ConfigHistory.KEY_EVENT);
                String zone = jo.getString(ConfigHistory.KEY_ZONE);
                String booth = jo.getString(ConfigHistory.KEY_BOOTH);
                String time = jo.getString(ConfigHistory.KEY_TIME);



                final HashMap<String, String> employees = new HashMap<>();
                employees.put(ConfigHistory.KEY_EVENT, event);
                employees.put(ConfigHistory.KEY_ZONE, zone);
                employees.put(ConfigHistory.KEY_BOOTH, booth);
                employees.put(ConfigHistory.KEY_TIME, time);
                list.add(0,employees);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        ListAdapter adapter = new SimpleAdapter(
                HistoryActivity.this, list, R.layout.custom_list,
                new String[]{ConfigHistory.KEY_EVENT, ConfigHistory.KEY_ZONE, ConfigHistory.KEY_BOOTH,ConfigHistory.KEY_TIME},
                new int[]{R.id.event_history, R.id.zone_history, R.id.booth_history,R.id.time_history});

        listview.setAdapter(adapter);
        TextView event = findViewById(R.id.event_history);
        TextView booth = findViewById(R.id.booth_history);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),show_bill_history.class);
                String messageevent = list.get(i).get(ConfigHistory.KEY_EVENT);
                String messagezone = list.get(i).get(ConfigHistory.KEY_ZONE);
                String messagebooth = list.get(i).get(ConfigHistory.KEY_BOOTH);
                intent.putExtra("event",messageevent);
                intent.putExtra("zone",messagezone);
                intent.putExtra("booth",messagebooth);
                startActivity(intent);
                finish();


            }
        });
    }


    private void getData() {


        String username = a.getUser().toString().trim();
        String url = ConfigFetchName.DATA_URL + username.toString().trim();
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                showName(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(HistoryActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void showName(String response) {
        String name = "";
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(ConfigFetchName.JSON_ARRAY);
            JSONObject collegeData = result.getJSONObject(0);
            name = collegeData.getString(ConfigFetchName.KEY_NAME);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        textViewFullname.setText("" + name);
    }


}
