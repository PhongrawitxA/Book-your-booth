package com.example.tester;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ShowInfoActivity extends AppCompatActivity {
    TextView textViewFullname,textViewEmail;
    Toolbar toolbar;
    User a = new User();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_info);

        toolbar = (Toolbar) findViewById(R.id.toolbar_showinfo);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrowbackward);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        textViewFullname = findViewById(R.id.fullname);
        textViewEmail = findViewById(R.id.email);
        getData();
        getEmail();

    }

    private void getData() {


        String username = a.getUser().trim();
        if (username.equals("")) {

            Toast.makeText(this, "Check Detail!", Toast.LENGTH_LONG).show();
            return;
        }
        String url = ConfigFetchName.DATA_URL + username;
        HttpsTrustManager.allowAllSSL();
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                showJSONS(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ShowInfoActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
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

    private void getEmail() {


        String username = a.getUser().trim();
        if (username.equals("")) {

            Toast.makeText(this, "Check Detail!", Toast.LENGTH_LONG).show();
            return;
        }
        String url = ConfigFetchEmail.DATA_URL + username;
        HttpsTrustManager.allowAllSSL();
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                showEmailJSONS(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ShowInfoActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void showEmailJSONS(String response) {
        String email = "";
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(ConfigFetchEmail.JSON_ARRAY);
            JSONObject collegeData = result.getJSONObject(0);
            email = collegeData.getString(ConfigFetchEmail.KEY_EMAIL);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        textViewEmail.setText("" + email);
    }

}