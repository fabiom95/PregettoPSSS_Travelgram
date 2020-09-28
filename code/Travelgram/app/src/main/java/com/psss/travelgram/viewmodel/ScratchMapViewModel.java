package com.psss.travelgram.viewmodel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.psss.travelgram.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.maps.android.data.kml.KmlLayer;
import com.psss.travelgram.model.entity.Traveler;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class ScratchMapViewModel extends ViewModel implements Observer{

    private Traveler traveler;
    private MutableLiveData<ArrayList<String>> visitedCountries;
    private MutableLiveData<ArrayList<String>> wishedCountries;


    public ScratchMapViewModel(){
        visitedCountries = new MutableLiveData<>();
        wishedCountries = new MutableLiveData<>();
        traveler = new Traveler();
        traveler.addObserver(this);
    }


    public MutableLiveData<ArrayList<String>> getVisitedCountries() {
        return visitedCountries;
    }
    public MutableLiveData<ArrayList<String>> getWishedCountries() {
        return wishedCountries;
    }


    public void setVisitedCountries() {
        this.visitedCountries.setValue(traveler.getVisitedCountries());
    }

    public void setWishedCountries() {
        this.wishedCountries.setValue(traveler.getWishedCountries());
    }


    // l'update è chiamata quando traveler ha finito di caricare i dati dal database
    @Override
    public void update(Observable o, Object arg) {
        if (arg.equals("visited"))
            setVisitedCountries();
        if (arg.equals("wish"))
            setWishedCountries();
    }

}