package com.psss.travelgram.model.entity;

import android.util.Log;

import com.psss.travelgram.model.repository.TravelerRepository;
import com.psss.travelgram.viewmodel.ScratchMapViewModel;

import java.util.ArrayList;
import java.util.Observable;

public class Traveler extends Observable {

    private TravelerRepository travelerRepo;
    private String username;
    private ArrayList<String> visitedCountries;
    private ArrayList<String> wishedCountries;


    // costruttore
    public Traveler(){
        travelerRepo = new TravelerRepository();
        visitedCountries = new ArrayList<>();
        wishedCountries = new ArrayList<>();
        loadTraveler();
    }


    // get e set
    public String getUsername() {
        return username;
    }

    public ArrayList<String> getVisitedCountries() {
        return visitedCountries;
    }

    public ArrayList<String> getWishedCountries() {
        return wishedCountries;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setVisitedCountries(ArrayList<String> visitedCountries) {
        this.visitedCountries = visitedCountries;
    }

    public void setWishedCountries(ArrayList<String> wishedCountries) {
        this.wishedCountries = wishedCountries;
    }


    // altre funzioni
    public void loadTraveler(){
        travelerRepo.loadTraveler(this);
    }

    public boolean isCountryVisited(String country){
        return visitedCountries.contains(country);
    }
    public boolean isCountryWished(String country){
        return wishedCountries.contains(country);
    }

    // notifica gli observer
    public void ready(){
        setChanged();
        notifyObservers();
    }


    // funzioni invocate da PlaceViewModel
    public void addVisitedCountry(String country){
        if(!isCountryVisited(country)) {
            visitedCountries.add(country);
            travelerRepo.addVisitedCountry(country);
        }
    }

    public void addWishedCountry(String country){
        if(!isCountryWished(country)) {
            wishedCountries.add(country);
            travelerRepo.addWishedCountry(country);
        }
    }

    public void removeVisitedCountry(String country){
        if(isCountryVisited(country)) {
            visitedCountries.remove(country);
            travelerRepo.removeVisitedCountry(country);
        }
    }

    public void removeWishedCountry(String country){
        if(isCountryWished(country)) {
            wishedCountries.remove(country);
            travelerRepo.removeWishedCountry(country);
        }
    }


}
