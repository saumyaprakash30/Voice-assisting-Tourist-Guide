package com.example.indoguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String PLACE_SELECTED = "com.example.IndoGuide.placeName";
    private TextView tvstep ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("IndoGuide");
        this.setTitle("Select Place");
        tvstep =  findViewById(R.id.tvStep);

        Intent homeScreen = new Intent(this,com.example.indoguide.selectionScreen.class);
        startActivity(homeScreen);
        getSupportActionBar().hide();
        SharedPreferences pref = getSharedPreferences(PLACE_SELECTED,MODE_PRIVATE);
        String place =  pref.getString("place","emptyPlace");
        String startPt = pref.getString("startpoint","emptySP");
        Toast.makeText(this, place+" "+startPt, Toast.LENGTH_SHORT).show();
        tvstep.setText(place+" "+startPt);
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE|View.SYSTEM_UI_FLAG_FULLSCREEN|View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

    }
}
