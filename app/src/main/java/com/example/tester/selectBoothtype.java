package com.example.tester;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

public class selectBoothtype extends AppCompatActivity {
    Toolbar toolbar;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_boothtype);

        frameLayout = (FrameLayout) findViewById(R.id.card_view);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.swing_up_right);
        frameLayout.startAnimation(animation);


        toolbar = (Toolbar) findViewById(R.id.toolbar_selectboothtype);
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

        TextView general,premium;
        CardView cardViewgeneral,cardViewpremium;
        general = findViewById(R.id.general);
        premium = findViewById(R.id.premium);
        cardViewgeneral = findViewById(R.id.cardviewgeneral);
        cardViewpremium = findViewById(R.id.cardviewpremium);
        cardViewgeneral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select_booth c = new select_booth();
                c.setBooth_type("General");
                Intent intent = new Intent(getApplicationContext(), FetchImageBooth.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        cardViewpremium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select_booth c = new select_booth();
                c.setBooth_type("Premium");
                Intent intent = new Intent(getApplicationContext(), FetchImageBooth.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        general.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),show_general.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        premium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),show_premium.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }
}