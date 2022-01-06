package com.example.tester;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class Splash_Activity extends AppCompatActivity {
    Thread timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        timer = new Thread(){
            @Override
            public void run() {
                try{
                    synchronized (this){
                        wait(2500);
                    }
                } catch (InterruptedException e){
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent(Splash_Activity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        timer.start();
    }
}