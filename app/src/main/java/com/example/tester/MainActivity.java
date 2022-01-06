package com.example.tester;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import android.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.HashMap;

import static com.example.tester.LoginActivity.sharedpreferences;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ListView listView;
    String mTitle[] = {"Bookfair2020","Commart2020","Comingsoon","Comingsoon","Comingsoon"};
    String mDescription[] = {"IMPACT Muang Thong Thani","Bitec Bangna","","",""};
    int image[] = {R.drawable.bookfair,R.drawable.commart,R.drawable.comingsoon,R.drawable.comingsoon,R.drawable.comingsoon};
    AnimationDrawable animationDrawable;
    LinearLayout linearlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearlayout = (LinearLayout) findViewById(R.id.mylayer);

        animationDrawable = (AnimationDrawable) linearlayout.getBackground();
        animationDrawable.setEnterFadeDuration(4000);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();

        Toolbar toolbar = findViewById(R.id.toolbar_imagetestfetch);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        NavigationView navigationView = findViewById(R.id.navigation_view);

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawerLayout,
                toolbar,
                R.string.nav_open,
                R.string.nav_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_edit: Intent i = new Intent(getApplicationContext(), EditActivity.class); startActivity(i);
                        break;
                    case R.id.nav_logout: Intent j = new Intent(getApplicationContext(), LoginActivity.class);
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.custom,null);
                        TextView title = (TextView) view.findViewById(R.id.titlealert);
                        title.setText("Warning");
                        TextView message = (TextView) view.findViewById(R.id.message);
                        message.setText("Do you want to Exit?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                SessionManager sessionManager = new SessionManager(MainActivity.this,SessionManager.SESSION_REMEMBERME);
                                sessionManager.createRememberMeSession(null,null);
                                sessionManager.createLoginSession(null,null);
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.remove("LOGIN");
                                editor.commit();
                                startActivity(j);
                                finish();
                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(getApplicationContext(),"cancel",Toast.LENGTH_SHORT).show();
                            }
                        });
                        builder.setView(view);
                        builder.show();
                        break;
                    case R.id.nav_history: Intent k = new Intent(getApplicationContext(), HistoryActivity.class); startActivity(k);
                        break;
                    case R.id.nav_profile: Intent l = new Intent(getApplicationContext(), ShowInfoActivity.class); startActivity(l);
                        break;
                }
                return true;
            }
        });

        listView = findViewById(R.id.ListView);
        MyAdapter adapter = new MyAdapter(this,mTitle,mDescription,image);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    Toast.makeText(MainActivity.this, "Event Bookfair", Toast.LENGTH_SHORT).show();
                    select_booth a = new select_booth();
                    a.setEvent(1);
                    Intent i = new Intent(getApplicationContext(),select_booth_event01.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
                if(position == 1){
                    Toast.makeText(MainActivity.this, "Event Commart", Toast.LENGTH_SHORT).show();
                    select_booth a = new select_booth();
                    a.setEvent(2);
                    Intent i = new Intent(getApplicationContext(),select_booth_event02.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
                if(position == 2){
                    Toast.makeText(MainActivity.this, "Coming soon", Toast.LENGTH_SHORT).show();
                }
                if(position == 3){
                    Toast.makeText(MainActivity.this, "Coming soon", Toast.LENGTH_SHORT).show();
                }
                if(position == 4){
                    Toast.makeText(MainActivity.this, "Coming soon", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    class MyAdapter extends ArrayAdapter<String>{
        Context context;
        String rTitle[];
        String rDescription[];
        int rImg[];

        MyAdapter(Context c,String title[],String Description[],int imgs[]){
            super(c,R.layout.row,R.id.textview1,title);
            this.context = c;
            this.rTitle = title;
            this.rDescription = Description;
            this.rImg = imgs;

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.from(context).inflate(R.layout.car_view_item_layout_list,parent,false);
            ImageView images = convertView.findViewById(R.id.image);
            TextView myTitle = convertView.findViewById(R.id.textview1);
            TextView myDescription = convertView.findViewById(R.id.textview2);

            images.setImageResource(rImg[position]);
            myTitle.setText(rTitle[position]);
            myDescription.setText(rDescription[position]);

            Animation animation = AnimationUtils.loadAnimation(context,R.anim.item_fall_down);
            convertView.startAnimation(animation);

            return convertView;
        }
    }
}