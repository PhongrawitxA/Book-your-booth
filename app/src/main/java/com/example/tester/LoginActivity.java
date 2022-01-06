package com.example.tester;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.util.HashMap;

import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class LoginActivity extends AppCompatActivity {
  TextInputEditText textInputEditTextUsername, textInputEditTextPassword;
//    EditText editTextUsername,editTextPassword;
    Button buttonLogin;
    TextView textViewSignUp;
    ProgressBar progressBar;
    public static String login;
    static SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        textInputEditTextUsername = findViewById(R.id.username);
        textInputEditTextPassword = findViewById(R.id.password);

        textInputEditTextUsername.setText("");
        textInputEditTextPassword.setText("");

        buttonLogin = findViewById(R.id.buttonLogin);
        textViewSignUp = findViewById(R.id.signUpText);
        progressBar = findViewById(R.id.progress);

        CheckBox remember = findViewById(R.id.checkBox);

        SessionManager sessionManager = new SessionManager(LoginActivity.this,SessionManager.SESSION_REMEMBERME);
        if (sessionManager.checkRememberMe() && sessionManager.KEY_USERNAME != null && sessionManager.KEY_PASSWORD != null){
            HashMap<String, String> rememberDetails = sessionManager.getRememberMeDetailsFromSession();
            textInputEditTextUsername.setText(rememberDetails.get(sessionManager.KEY_USERNAME));
            textInputEditTextPassword.setText(rememberDetails.get(sessionManager.KEY_PASSWORD));
        }
        else{
            textInputEditTextUsername.setText(null);
            textInputEditTextPassword.setText(null);
        }
        sharedpreferences = getApplicationContext().getSharedPreferences("Preferences", 0);
        login = sharedpreferences.getString("LOGIN", null);
        if (login != null && textInputEditTextUsername.getText() != null && textInputEditTextPassword.getText() != null ) {
            User a = new User();
            a.setUser(textInputEditTextUsername.getText().toString());
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            finish();
        }else {

        }
        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username, password;
                username = String.valueOf(textInputEditTextUsername.getText());
                User a = new User();
                a.setUser(username);
                password = String.valueOf(textInputEditTextPassword.getText());

                if(remember.isChecked()){
                    SessionManager sessionManager = new SessionManager(LoginActivity.this,SessionManager.SESSION_REMEMBERME);
                    sessionManager.createRememberMeSession(username,password);
                    sessionManager.createLoginSession(username,password);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("LOGIN", username);
                    editor.commit();
                }
                else{
                    SessionManager sessionManager = new SessionManager(LoginActivity.this,SessionManager.SESSION_REMEMBERME);
                    sessionManager.createRememberMeSession(null,null);
                    sessionManager.createLoginSession(null,null);
                }

                if(!username.equals("") && !password.equals("")) {
//                    progressBar.setVisibility(View.VISIBLE);
                    Handler handler = new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                            String[] field = new String[2];
                            field[0] = "username";
                            field[1] = "password";
                            String[] data = new String[2];
                            data[0] = username;
                            data[1] = password;
                            HttpsTrustManager.allowAllSSL();
                            PutData putData = new PutData(URL.url+"login.php", "POST", field, data);
//                            PutData putData = new PutData("https://192.168.64.2/LoginAndRegister/login.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
//                                    progressBar.setVisibility(View.GONE);
                                    String result = putData.getResult();
                                    if(result.equals("Login Success")){
                                        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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
    }
}