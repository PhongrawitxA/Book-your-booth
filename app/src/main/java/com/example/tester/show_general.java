package com.example.tester;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

public class show_general extends AppCompatActivity {
    Toolbar toolbar;
    private SlidrInterface slidr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_general);
        TextView textView;
        textView = findViewById(R.id.zoneA);
        textView.setText(result(new general()));

        slidr = Slidr.attach(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar_show_general);
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
    }
    public void lockSlide(View v) {
        slidr.lock();
    }
    public void unlockSlide(View v) {
        slidr.unlock();
    }

    public String result(booth in){
        return in.toStrings();
    }
}