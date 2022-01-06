package com.example.tester;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class select_booth_event01 extends AppCompatActivity {
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_event01);

        toolbar = (Toolbar) findViewById(R.id.toolbar_selectevent01);
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



        TextView zoneA = findViewById(R.id.zoneA);
        TextView zoneB = findViewById(R.id.zoneB);
        TextView zoneC = findViewById(R.id.zoneC);
        TextView zoneD = findViewById(R.id.zoneD);
        TextView zoneE = findViewById(R.id.zoneE);
        zoneA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select_booth c = new select_booth();
                c.setZone(1);
                Intent intent = new Intent(getApplicationContext(),selectBoothtype.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        zoneB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select_booth c = new select_booth();
                c.setZone(2);
                Intent intent = new Intent(getApplicationContext(),selectBoothtype.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        zoneC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select_booth c = new select_booth();
                c.setZone(3);
                Intent intent = new Intent(getApplicationContext(),selectBoothtype.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        zoneD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select_booth c = new select_booth();
                c.setZone(4);
                Intent intent = new Intent(getApplicationContext(),selectBoothtype.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        zoneE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select_booth c = new select_booth();
                c.setZone(5);
                Intent intent = new Intent(getApplicationContext(),selectBoothtype.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

    }
    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}