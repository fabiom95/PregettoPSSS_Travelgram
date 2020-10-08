package com.psss.travelgram.model.entity;


import com.psss.travelgram.model.repository.TravelerRepository;
import java.util.ArrayList;
import java.util.Observable;


public class TravelerList extends Observable {

    private ArrayList<Traveler> travelers;
    private TravelerRepository travelerRepo;


    // costruttore
    public TravelerList(){
        travelers = new ArrayList<>();
        travelerRepo = new TravelerRepository();
    }


    // set e get
    public void setTravelers(ArrayList<Traveler> travelers) { this.travelers = travelers; }
    public ArrayList<Traveler> getTravelers() { return travelers; }


    // ricerca di un traveler
    public void searchTraveler(String s){
        travelerRepo.searchTravelers(this, s);
    }


    // callback, chiamata da travelerRepo quando è arrivato il risultato della query
    // notifica gli observer
    public void callback(String s){
        setChanged();
        notifyObservers(s);
    }


    // get degli username dei traveler
    public ArrayList<String> getUsernames(){
        ArrayList<String> usernames = new ArrayList<>();
        for(Traveler traveler : travelers)
            usernames.add(traveler.getUsername());
        return usernames;
    }

}
