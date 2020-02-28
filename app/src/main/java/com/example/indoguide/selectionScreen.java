package com.example.indoguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
    String data="",startpoint ="";
    String[] startpts = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection_screen);
        sv =  findViewById(R.id.svHomeScreen);
        btnPlace = findViewById(R.id.btnSelectHomeScreen);
        btnStart = findViewById(R.id.btnStartHomeScreen);
        spinner  = findViewById(R.id.spinnerStartingPlace);
        list = findViewById(R.id.lvHints);


        final ArrayList<String> places = new places().getplaces();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,places);
        list.setAdapter(adapter);

           sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    if(places.contains(query)){
                        adapter.getFilter().filter(query);
                    }
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    adapter.getFilter().filter(newText);
                    list.setVisibility(View.VISIBLE);

                    return false;
                }
            });
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                data = adapter.getItem(i);
                sv.setQuery(data,false);
                list.setVisibility(View.GONE);


            }
        });
        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(data=="EHIPASSIKO"){

                    String[] startpt = {"Select Starting point","1","2","3"};
                    startpts = startpt;
                    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(selectionScreen.this,android.R.layout.simple_spinner_dropdown_item,startpts);
                    spinner.setAdapter(adapter1);


                }
            }
        });
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences(PLACE_SELECTED,MODE_PRIVATE).edit();
                editor.putString("place",data);
                editor.putString("startPoint",startpts[spinner.getSelectedItemPosition()]);
                selectionScreen.this.finish();
;            }
        });

    }

}
