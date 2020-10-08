package com.psss.travelgram.viewmodel;



import androidx.lifecycle.ViewModel;

import com.psss.travelgram.model.entity.Traveler;


public class PlaceViewModel extends ViewModel {

    private Traveler traveler;
    private String country;


    // costruttore
    public PlaceViewModel(String country) {
        traveler = new Traveler();
        traveler.loadTraveler();
        this.country = country;
    }


    // get
    public String getUsername() { return traveler.getUsername();}


    // segna luogo come "visited" o come "wish"
    public void addVisitedCountry() { traveler.addVisitedCountry(country); }
    public void removeVisitedCountry() { traveler.removeVisitedCountry(country); }

    public void addWishedCountry() { traveler.addWishedCountry(country); }
    public void removeWishedCountry() { traveler.removeWishedCountry(country); }

}