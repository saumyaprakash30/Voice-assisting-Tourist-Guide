package com.example.indoguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;


import java.util.ArrayList;

public class selectionScreen extends AppCompatActivity {
    public static final String PLACE_SELECTED = "com.example.IndoGuide.placeName";
    private SearchView sv ;
    private Button btnPlace,btnStart;
    private Spinner spinner;
    private ListView list;
    private ImageView image;
    String data="",startpoint ="";
    String[] startpts = null;
    int selectedStartPoint=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection_screen);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        sv =  findViewById(R.id.svHomeScreen);
        btnPlace = findViewById(R.id.btnSelectHomeScreen);
        btnStart = findViewById(R.id.btnStartHomeScreen);
        spinner  = findViewById(R.id.spinnerStartingPlace);
        list = findViewById(R.id.lvHints);
        image = findViewById(R.id.ivHomeScreen);
        //hidden on start
        btnStart.setVisibility(View.GONE);
        spinner.setVisibility(View.GONE);
        image.setVisibility(View.GONE);


        final ArrayList<String> places = new places().getplaces();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,places);
        list.setAdapter(adapter);

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                Toast.makeText(selectionScreen.this, "Sub", Toast.LENGTH_SHORT).show();
                if(places.contains(query)){
                    adapter.getFilter().filter(query);
                }
//                selectedStartPoint=0;
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
//                if(sv.getVisibility()!=View.GONE)
//                    selectedStartPoint=0;
//                Toast.makeText(selectionScreen.this, "change", Toast.LENGTH_SHORT).show();
                list.setVisibility(View.VISIBLE);

                return false;
            }
        });
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedStartPoint =1;
                data = adapter.getItem(i);
                sv.setQuery(data,false);
                sv.clearFocus();
                list.setVisibility(View.GONE);


            }
        });
        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedStartPoint==1){

                    String[] startpt = {"Select Starting point","1","2","3"};
                    startpts = startpt; //global variable
                    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(selectionScreen.this,android.R.layout.simple_spinner_dropdown_item,startpts);
                    spinner.setAdapter(adapter1);
                    btnStart.setVisibility(View.VISIBLE);
                    spinner.setVisibility(View.VISIBLE);
                    if(data=="Ehipassiko")
                        image.setImageResource(R.drawable.ehipassiko);
                    else if(data=="Chennai Museum")
                        image.setImageResource(R.drawable.chennaimuseum);
                    else if(data=="IIITDM Acad")
                        image.setImageResource(R.drawable.iiitdmacad);
                    else if(data=="Taj Mahal")
                        image.setImageResource(R.drawable.tajmahal);
                    image.setVisibility(View.VISIBLE);

                }
                else{
                    Toast.makeText(selectionScreen.this, "Select Valid Place!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(spinner.getSelectedItemPosition()!=0){
                    SharedPreferences.Editor editor = getSharedPreferences(PLACE_SELECTED,MODE_PRIVATE).edit();
                    editor.putString("place",data);
                    editor.putString("startpoint",startpts[spinner.getSelectedItemPosition()]);
                    editor.commit();
                    selectionScreen.this.finish();
                }
                else{
                    Toast.makeText(selectionScreen.this, "Please Select Starting point!", Toast.LENGTH_SHORT).show();
                }
                ;            }
        });

    }

}
