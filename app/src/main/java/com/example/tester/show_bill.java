package com.example.tester;

import androidx.appcompat.app.AppCompatActivity;

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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;

public class show_bill extends AppCompatActivity {
    Button buttonBook,buttonCancel;
    String bill_name,bill_event,bill_booth;
    TextView textViewFullname;
    TextView textViewPrice;
    User a = new User();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_bill);
        LocalDateTime time = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    time = LocalDateTime.now();
                }
        bill_name = a.getUser();
        textViewFullname = findViewById(R.id.fullName_bill);
        textViewPrice = findViewById(R.id.price_bill);
        select_booth c = new select_booth();
        bill_event = ""+c.getEvent();
        bill_booth = c.getBooth_id();

        billTime g = new billTime();
        String title_e,zone_name;
        TextView event_bill,time_text,zone_bill,booth_bill,type_bill,price_bill;
        Button pay,cancel;
        event_bill = findViewById(R.id.event_bill);
        time_text = findViewById(R.id.time_text);
        zone_bill = findViewById(R.id.zone_bill);
        booth_bill = findViewById(R.id.Booth_bill);
        type_bill = findViewById(R.id.Type_bill);
        price_bill = findViewById(R.id.price_bill);
        pay = findViewById(R.id.buttonbook);
        cancel = findViewById(R.id.buttoncancel);
        if(c.getEvent()==1){
            title_e = "Book_fair";
        }else{
            title_e = "Commart";
        }
        if(c.getZone()==1){
            zone_name = "zoneA";
        }else if(c.getZone()==2){
            zone_name = "zoneB";
        }else if(c.getZone()==3){
            zone_name = "zoneC";
        }else if(c.getZone()==4){
            zone_name = "zoneD";
        }else if(c.getZone()==5){
            zone_name = "zoneE";
        }else{
            zone_name = "zoneF";
        }
        event_bill.setText(title_e);
        g.setTime(String.valueOf(time));
        time_text.setText(g.getTime());
        zone_bill.setText(zone_name);
        booth_bill.setText(c.getBooth_id());
        type_bill.setText(c.getBooth_type());
        getData();
        getPrice();
        buttonBook = findViewById(R.id.buttonbook);
        buttonBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(show_bill.this);
                View view = LayoutInflater.from(show_bill.this).inflate(R.layout.custom,null);
                TextView title = (TextView) view.findViewById(R.id.titlealert);
                title.setText("Booking");
                TextView message = (TextView) view.findViewById(R.id.message);
                message.setText("Do you sure to book this booth?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String selectbooth,username,event,zone,booktime;
                        User a = new User();
                        select_booth b = new select_booth();
                        username = a.getUser();
                        event = String.valueOf(b.getEvent());
                        zone = String.valueOf(b.getZone());
                        selectbooth = b.getBooth_id();
                        booktime = g.getTime() ;
                        billTime c = new billTime();
                        if(!selectbooth.equals("")) {
                            Handler handler = new Handler();
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    String[] field = new String[5];
                                    field[0] = "username";
                                    field[1] = "event_id";
                                    field[2] = "zone_id";
                                    field[3] = "booth_id";
                                    field[4] = "booking_time";
                                    String[] data = new String[5];
                                    data[0] = username;
                                    data[1] = event;
                                    data[2] = zone;
                                    data[3] = selectbooth;
                                    data[4] = booktime;

                                    HttpsTrustManager.allowAllSSL();
                                    PutData putData = new PutData(URL.url+"book.php", "POST", field, data);
//                                    PutData putData = new PutData("https://192.168.64.2/LoginAndRegister/book.php", "POST", field, data);
                                    if (putData.startPut()) {
                                        if (putData.onComplete()) {
                                            String result = putData.getResult();
                                            if(result.equals("Book Success")){
                                                Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                            else{
                                                Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                    }

                                }
                            });
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"All fields required", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(),"cancel book",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setView(view);
                builder.show();

            }
        });

        buttonCancel = findViewById(R.id.buttoncancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), FetchImageBooth.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void getData() {


        String username = bill_name.toString().trim();
        if (username.equals("")) {

            Toast.makeText(this, "Check Detail!", Toast.LENGTH_LONG).show();
            return;
        }
        String url = ConfigFetchName.DATA_URL + bill_name.toString().trim();
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                showJSONS(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(show_bill.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
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


        String event_id = bill_event.toString().trim();
        String booth_id = bill_booth.toString().trim();
        if (event_id.equals("") && booth_id.equals("")) {

            Toast.makeText(this, "Check Detail!", Toast.LENGTH_LONG).show();
            return;
        }
        String url = ConfigFetchPrice.DATA_URL + bill_event.toString().trim() + ("&booth_id=").trim() +bill_booth.toString().trim();
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                showpriceJSONS(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(show_bill.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
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
}
