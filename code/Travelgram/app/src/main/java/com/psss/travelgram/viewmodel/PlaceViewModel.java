package com.psss.travelgram.viewmodel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.psss.travelgram.model.entity.Traveler;


public class PlaceViewModel extends ViewModel {

    //private MutableLiveData<Boolean> isVisited;
    //private MutableLiveData<Boolean> isWished;
    private Traveler traveler;
    private String country;

    // costruttore
    public PlaceViewModel(String country) {
        traveler = new Traveler();
        //isVisited = new MutableLiveData<>();
        //isWished = new MutableLiveData<>();
        this.country = country;
    }

    // get e set
    /*public MutableLiveData<Boolean> getIsVisited() {
        return isVisited;
    }

    public MutableLiveData<Boolean> getIsWished() {
        return isWished;
    }

    public void setIsVisited(Boolean isVisited) {
        this.isVisited.setValue(isVisited);
    }

    public void setIsWished(Boolean isWished) {
        this.isWished.setValue(isWished);
    }*/




    // altre funzioni

    public boolean isCountryVisited(){
        return traveler.isCountryVisited(country);
    }
    public boolean isCountryWished(){
        return traveler.isCountryWished(country);
    }

    public void addVisitedCountry(){
        traveler.addVisitedCountry(country);
    }

    public void removeVisitedCountry(){
        traveler.removeVisitedCountry(country);
    }

    public void addWishedCountry(){
        traveler.addWishedCountry(country);
    }



}