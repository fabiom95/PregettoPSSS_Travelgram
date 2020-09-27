package com.psss.travelgram.model.entity;

import com.psss.travelgram.model.repository.TravelerRepository;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Traveler extends Observable {

    //Qui ci metto tutti i private
    private TravelerRepository travelerRepo;
    private ArrayList<String> visited_countries;

    //TODO: eventualmente rimuovere singleton e observer

    // singleton
    //private static Traveler singleton = null;

    //private
    public Traveler(){
        travelerRepo = new TravelerRepository();
        visited_countries = new ArrayList<>();
    }

    /*
    public static Traveler getInstance(){
        if(singleton == null){
            singleton = new Traveler();
        }
        return singleton;
    }
*/



    public void addVisitedCountry(String country){
        //TODO: booleano
        if(!isCountryVisited(country)) {
            visited_countries.add(country);
            travelerRepo.addVisitedCountry(country);
        }
    }

    public boolean isCountryVisited(String country){
        return visited_countries.contains(country);
    }

}
