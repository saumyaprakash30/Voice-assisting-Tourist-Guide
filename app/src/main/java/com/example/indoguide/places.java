package com.example.indoguide;


import java.util.ArrayList;

public  class places {
    public ArrayList<String> getplaces() {

        String[] temp  ={"Ehipassiko","IIITDM Acad","Taj Mahal","Chennai Museum"};
        ArrayList<String> places = new ArrayList<>();

        for( int i=0;i<temp.length;i++)
        {
            places.add(temp[i]);
        }

        return places;


    }
}

