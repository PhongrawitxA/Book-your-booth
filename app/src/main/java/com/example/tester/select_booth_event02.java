package com.example.tester;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class select_booth_event02 extends AppCompatActivity {
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_event02);

        toolbar = (Toolbar) findViewById(R.id.toolbar_selectevent02);
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

        TextView zoneA_E2,zoneB_E2,zoneC_E2,zoneD_E2;
        zoneA_E2 = findViewById(R.id.zoneA_E2);
        zoneB_E2 = findViewById(R.id.zoneB_E2);
        zoneC_E2 = findViewById(R.id.zoneC_E2);
        zoneD_E2 = findViewById(R.id.zoneD_E2);

        zoneA_E2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select_booth c = new select_booth();
                c.setZone(1);
                Intent intent = new Intent(getApplicationContext(),selectBoothtype.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        zoneB_E2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select_booth c = new select_booth();
                c.setZone(2);
                Intent intent = new Intent(getApplicationContext(),selectBoothtype.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        zoneC_E2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select_booth c = new select_booth();
                c.setZone(3);
                Intent intent = new Intent(getApplicationContext(),selectBoothtype.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        zoneD_E2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select_booth c = new select_booth();
                c.setZone(4);
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