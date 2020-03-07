package com.example.indoguide;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String PLACE_SELECTED = "com.example.IndoGuide.placeName";
    private TextView tvstep ;
    private ImageButton mute;
    String place,startPt;
    Boolean odd=true;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences pref = getSharedPreferences(PLACE_SELECTED,MODE_PRIVATE);
        place =  pref.getString("place","emptyPlace");
        startPt = pref.getString("startpoint","emptySP");
        tvstep.setText(place+" : "+startPt);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.setTitle("IndoGuide");
//        getSupportActionBar().hide();
        // action bar
        ActionBar actionBar = getSupportActionBar();


        tvstep =  findViewById(R.id.tvStep);
        mute = findViewById(R.id.btnMute);

        SharedPreferences pref = getSharedPreferences(PLACE_SELECTED,MODE_PRIVATE);
        place =  pref.getString("place","emptyPlace");
        startPt = pref.getString("startpoint","emptySP");

        if(place=="emptyPlace"){
            Intent homeScreen = new Intent(this,com.example.indoguide.selectionScreen.class);
            startActivity(homeScreen);
        }else{

            Toast.makeText(this, place+" "+startPt, Toast.LENGTH_SHORT).show();
            tvstep.setText(place+" "+startPt);
        }

//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE|View.SYSTEM_UI_FLAG_FULLSCREEN|View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        mute.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // your handler code here
                if(odd)
                {
                    mute.setImageResource(android.R.drawable.ic_lock_silent_mode);
                    odd=false;
                }
                else
                {
                    mute.setImageResource(android.R.drawable.ic_lock_silent_mode_off);
                    odd=true;
                }

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.changePlace:
            {
                Intent homeScreen = new Intent(this,com.example.indoguide.selectionScreen.class);
                startActivity(homeScreen);
            }
            break;
        }
        return super.onOptionsItemSelected(item);
    }





}
