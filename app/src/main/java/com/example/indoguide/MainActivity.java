package com.example.indoguide;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    public static final String PLACE_SELECTED = "com.example.IndoGuide.placeName";
    private TextView tvstep,tvAngle,tvPlace ;
    String place,startPt;
    private Sensor sensorMotion,sensorMotionAcc,smMagneto,smGyro;
    int readingSensor=0;
    Button btnStart;
    ImageButton btnStop;


    @Override
    protected void onStart() {
        super.onStart();
        if(sensorMotion!=null){
            mSensorManager.registerListener(this,sensorMotion,SensorManager.SENSOR_DELAY_FASTEST);
        }
        if(sensorMotionAcc!=null){
            mSensorManager.registerListener(this,sensorMotionAcc,SensorManager.SENSOR_DELAY_NORMAL);
        }if(smMagneto!=null){
            mSensorManager.registerListener(this,smMagneto,SensorManager.SENSOR_DELAY_NORMAL);
        }
        if(smGyro!=null){
            mSensorManager.registerListener(this,smGyro,SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    private SensorManager mSensorManager;

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
        tvPlace.setText(place+" - "+startPt);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.setTitle("IndoGuide");
        //sensor initialisation
        mSensorManager = (SensorManager) getSystemService(
                Context.SENSOR_SERVICE);
        sensorMotion = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        sensorMotionAcc = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        smMagneto = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        smGyro = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

//        getSupportActionBar().hide();
        // action bar
        ActionBar actionBar = getSupportActionBar();


        tvPlace =  findViewById(R.id.tvPlace);
        tvstep = findViewById(R.id.tvStepMain);
        tvAngle = findViewById(R.id.tvAngleMain);
        btnStart = findViewById(R.id.btnStartMain);
        btnStop = findViewById(R.id.btnStopMain);
        //saving place name
        SharedPreferences pref = getSharedPreferences(PLACE_SELECTED,MODE_PRIVATE);
        place =  pref.getString("place","emptyPlace");
        startPt = pref.getString("startpoint","emptySP");

        if(place.equals("emptyPlace")){
            Intent homeScreen = new Intent(this,com.example.indoguide.selectionScreen.class);
            startActivity(homeScreen);
        }else{

//            Toast.makeText(this, place+" "+startPt, Toast.LENGTH_SHORT).show();
            tvPlace.setText(place+" - "+startPt);
        }

//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE|View.SYSTEM_UI_FLAG_FULLSCREEN|View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(readingSensor==0){
                    readingSensor=1;
                }else{
                    Toast.makeText(MainActivity.this, "Invalid!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(readingSensor==1){
                    readingSensor = 0;

                }else{
                    Toast.makeText(MainActivity.this, "Invalid!", Toast.LENGTH_SHORT).show();
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


    @Override
    public void onSensorChanged(SensorEvent event) {
        int sensorType = event.sensor.getType();

        if(readingSensor==1){
            switch (sensorType){
                case Sensor.TYPE_STEP_COUNTER:
                    try{
                        float step = event.values[0];
                        tvstep.setText("Step: "+(step));


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case Sensor.TYPE_MAGNETIC_FIELD:
                    try {

                        double heading = (Math.atan2(event.values[1], event.values[0]) * 180 / Math.PI)+180;
                        tvAngle.setText("angle: "+(int)heading );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;



            }
        }



    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
