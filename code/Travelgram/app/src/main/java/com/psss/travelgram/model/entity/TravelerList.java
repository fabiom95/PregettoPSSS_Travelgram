package com.psss.travelgram.model.entity;

import com.psss.travelgram.model.repository.TravelerRepository;

import java.util.ArrayList;
import java.util.Observable;

public class TravelerList extends Observable {

    private ArrayList<Traveler> travelers;
    private TravelerRepository travelerRepo;

    public TravelerList(){
        travelers = new ArrayList<>();
        travelerRepo = new TravelerRepository();
    }

    public ArrayList<Traveler> getTravelers(){
        return travelers;
    }

    public void searchTraveler(String s){
        travelerRepo.searchTravelers(this, s);
    }

    public void setTravelers(ArrayList<Traveler> travelers){
        this.travelers = travelers;
        setChanged();
        notifyObservers();
    }

    public ArrayList<String> getUsernames(){
        ArrayList<String> usernames = new ArrayList<>();
        for(Traveler traveler : travelers)
            usernames.add(traveler.getUsername());
        return usernames;
    }






}
