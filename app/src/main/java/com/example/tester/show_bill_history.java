package com.example.tester;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class show_bill_history extends AppCompatActivity {
    TextView textViewFullname,textViewEvent,textViewZone,textViewBooth,textViewPrice,textViewType,textViewTime;
    User user = new User();
    select_booth a = new select_booth();
    String event_id, booth_id;
    Toolbar toolbar;
    Button buttonDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_bill_history);

        toolbar = (Toolbar) findViewById(R.id.toolbar_show_bill_history);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrowbackward);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), HistoryActivity.class);
                startActivity(i);
                finish();
            }
        });

        textViewFullname = findViewById(R.id.fullName_hisbill);
        textViewEvent = findViewById(R.id.event_hisbill);
        textViewZone = findViewById(R.id.zone_hisbill);
        textViewBooth = findViewById(R.id.booth_hisbill);
        textViewPrice = findViewById(R.id.price_hisbill);
        textViewType = findViewById(R.id.Type_hisbill);
        textViewTime = findViewById(R.id.time_hisbill);
        buttonDelete = findViewById(R.id.buttondelete_hisbill);

        Intent i = getIntent();
        String event_his_bill = getIntent().getStringExtra("event");
        String zone_his_bill = getIntent().getStringExtra("zone");
        String booth_his_bill = getIntent().getStringExtra("booth");

        textViewEvent.setText(event_his_bill);
        textViewZone.setText(zone_his_bill);
        textViewBooth.setText(booth_his_bill);

        getData();
        if(event_his_bill.equals("Bookfair")){
            a.setEvent(1);
        }
        else if(event_his_bill.equals("commart 2020")){
            a.setEvent(2);
        }
        a.setBooth_id(booth_his_bill);
        getPrice();
        getBoothType();
        getTime();

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(show_bill_history.this);
                View view = LayoutInflater.from(show_bill_history.this).inflate(R.layout.custom,null);
                TextView title = (TextView) view.findViewById(R.id.titlealert);
                title.setText("Warning");
                TextView message = (TextView) view.findViewById(R.id.message);
                message.setText("Do you want to change delete?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String username,event_id,booth_id;
                        username = user.getUser();
                        event_id = a.getEvent()+"";
                        booth_id = a.getBooth_id();
                        if (!username.equals("") && !event_id.equals("") && !booth_id.equals("")) {
                            Handler handler = new Handler();
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    String[] field = new String[3];
                                    field[0] = "username";
                                    field[1] = "event_id";
                                    field[2] = "booth_id";
                                    String[] data = new String[3];
                                    data[0] = username;
                                    data[1] = event_id;
                                    data[2] = booth_id;
                                    HttpsTrustManager.allowAllSSL();
                                    PutData putData = new PutData(URL.url+"delete.php", "POST", field, data);
//                                    PutData putData = new PutData("https://192.168.64.2/LoginAndRegister/delete.php", "POST", field, data);
                                    if (putData.startPut()) {
                                        if (putData.onComplete()) {
                                            String result = putData.getResult();
                                            if (result.equals("Delete Success")) {
                                                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(getApplicationContext(), HistoryActivity.class);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                    }

                                }
                            });
                        } else {
                            Toast.makeText(getApplicationContext(), "All fields required", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(),"cancle",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setView(view);
                builder.show();
                    }
                });

    }








    private void getData() {
        String username = user.getUser().trim();
        if (username.equals("")) {

            Toast.makeText(this, "Check Detail!", Toast.LENGTH_LONG).show();
            return;
        }
        String url = ConfigFetchName.DATA_URL + username;
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                showJSONS(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(show_bill_history.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void showJSONS(String response) {
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

    private void getPrice() {


        String event_id = a.getEvent()+"";
        String booth_id = a.getBooth_id().trim();
        if (event_id.equals("") && booth_id.equals("")) {

            Toast.makeText(this, "Check Detail!", Toast.LENGTH_LONG).show();
            return;
        }
        String url = ConfigFetchPrice.DATA_URL + event_id.trim() + ("&booth_id=").trim() + booth_id.trim();
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                showpriceJSONS(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(show_bill_history.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void showpriceJSONS(String response) {
        String price = "";
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(ConfigFetchPrice.JSON_ARRAY);
            JSONObject collegeData = result.getJSONObject(0);
            price = collegeData.getString(ConfigFetchPrice.KEY_NAME);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        textViewPrice.setText("" + price);
    }

    private void getBoothType() {


        String event_id = a.getEvent()+"";
        String booth_id = a.getBooth_id().trim();
        if (event_id.equals("") && booth_id.equals("")) {

            Toast.makeText(this, "Check Detail!", Toast.LENGTH_LONG).show();
            return;
        }
        String url = ConfigFetchBoothType.DATA_URL + event_id.trim() + ("&booth_id=").trim() + booth_id.trim();
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                showboothtypeJSONS(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(show_bill_history.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void showboothtypeJSONS(String response) {
        String boothtype = "";
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(ConfigFetchBoothType.JSON_ARRAY);
            JSONObject collegeData = result.getJSONObject(0);
            boothtype = collegeData.getString(ConfigFetchBoothType.KEY_NAME);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        textViewType.setText("" + boothtype);
    }

    private void getTime() {
        String username = user.getUser().trim();
        String event_id = a.getEvent()+"";
        String booth_id = a.getBooth_id().trim();
        if (username.equals("") && event_id.equals("") && booth_id.equals("")) {

            Toast.makeText(this, "Check Detail!", Toast.LENGTH_LONG).show();
            return;
        }
        String url = ConfigFetchTime.DATA_URL+ username + ("&event_id=").trim() + event_id.trim() + ("&booth_id=").trim() + booth_id.trim();
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                showtimeJSONS(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(show_bill_history.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void showtimeJSONS(String response) {
        String time = "";
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(ConfigFetchTime.JSON_ARRAY);
            JSONObject collegeData = result.getJSONObject(0);
            time = collegeData.getString(ConfigFetchTime.KEY_NAME);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        textViewTime.setText("" + time);
    }
}

