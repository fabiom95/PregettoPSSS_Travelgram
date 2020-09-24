package com.psss.travelgram.viewmodel;


import android.content.Context;
import android.widget.EditText;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.psss.travelgram.R;
import com.psss.travelgram.model.entity.Traveler;
import com.psss.travelgram.model.repository.AuthRepository;


public class PlaceViewModel extends ViewModel {

    private Traveler traveler;


    public PlaceViewModel() {
        traveler = Traveler.getInstance();
    }


    public void addVisitedCountry(String country){
        traveler.addVisitedCountry(country);
    }



}