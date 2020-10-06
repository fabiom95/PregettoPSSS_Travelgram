package com.psss.travelgram.model.entity;

import android.util.Log;

import com.psss.travelgram.model.repository.TravelerRepository;

import java.util.ArrayList;
import java.util.Observable;

public class TravelerList extends Observable {

    private ArrayList<Traveler> travelers;
    private TravelerRepository travelerRepo;
    private int count;  // per calcolare quando sono stati caricati i TJ di tutti i traveler

    public TravelerList(){
        travelers = new ArrayList<>();
        travelerRepo = new TravelerRepository();
        count = 0;
    }

    public ArrayList<Traveler> getTravelers(){
        return travelers;
    }

    public void searchTraveler(String s){
        travelerRepo.searchTravelers(this, s);
    }

    public void loadFollowingTravelers(ArrayList<String> IDs){
        travelerRepo.loadFollowingTravelers(this, IDs);
    }

    public void setTravelers(ArrayList<Traveler> travelers){
        this.travelers = travelers;
        Log.d("PROVA", "size: " + travelers.size());
        setChanged();
        notifyObservers("Travelers ready");
    }

    public void ready(){
        if(count < travelers.size())
            count = count+1;
        if(count == travelers.size()){
            setChanged();
            notifyObservers("TJ ready");
        }
    }

    // associa ad ogni Traveler il suo TravelJournal
    public void linkJournals(String country){
        for(Traveler traveler : travelers)
            traveler.setTJ(this, country);
    }

    public ArrayList<String> getUsernames(){
        ArrayList<String> usernames = new ArrayList<>();
        for(Traveler traveler : travelers)
            usernames.add(traveler.getUsername());
        return usernames;
    }

    // ottiene per ogni memory il suo username (che quindi possono essere replicati)
    public ArrayList<String> getTotalUsernames(){
        ArrayList<String> usernames = new ArrayList<>();
        for(Traveler traveler : travelers)
            for(int i=0; i<traveler.getMemoryCount(); i++)
                usernames.add(traveler.getUsername());
        return usernames;
    }

    // tutte le immagini di tutti i traveler
    public ArrayList<String> getImageLinks(){
        ArrayList<String> imageLinks = new ArrayList<>();
        for(Traveler traveler : travelers)
            imageLinks.addAll(traveler.getImageLinks());
        return imageLinks;
    }

    //
    public ArrayList<String> getMemoryIDs(){
        ArrayList<String> memoryIDs = new ArrayList<>();
        for(Traveler traveler : travelers)
            memoryIDs.addAll(traveler.getMemoryIDs());
        return memoryIDs;
    }





}
