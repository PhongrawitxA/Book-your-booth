package com.example.tester;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class EditActivity extends AppCompatActivity {
    Button buttonEdit;
    TextInputEditText textInputEditTextEdit;
    Toolbar toolbar;
    String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        toolbar = (Toolbar) findViewById(R.id.toolbar_edit);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrowbackward);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        User a = new User();
        String username;
        username = a.getUser();

        textInputEditTextEdit = findViewById(R.id.editemail);
        buttonEdit = findViewById(R.id.buttonEdit);

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = String.valueOf(textInputEditTextEdit.getText());
                if (!email.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(EditActivity.this);
                    View view = LayoutInflater.from(EditActivity.this).inflate(R.layout.custom,null);
                    TextView title = (TextView) view.findViewById(R.id.titlealert);
                    title.setText("Warning");
                    TextView message = (TextView) view.findViewById(R.id.message);
                    message.setText("Do you want to change email?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                            Handler handler = new Handler();
                            handler.post(new Runnable() {
                                @Override
                                public void run() {

                                    String[] field = new String[2];
                                    field[0] = "username";
                                    field[1] = "email";
                                    String[] data = new String[2];
                                    data[0] = username;
                                    data[1] = email;
                                    HttpsTrustManager.allowAllSSL();
                                    PutData putData = new PutData(URL.url+"editemail.php", "POST", field, data);
//                                    PutData putData = new PutData("https://192.168.64.2/LoginAndRegister/editemail.php", "POST", field, data);
                                    if (putData.startPut()) {
                                        if (putData.onComplete()) {
                                            String result = putData.getResult();
                                            if (result.equals("Edit Success")) {
                                                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                    }

                                }
                            });

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(), "cancel", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setView(view);
                builder.show();
            }
                else{
                    Toast.makeText(getApplicationContext(), "fields required", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}