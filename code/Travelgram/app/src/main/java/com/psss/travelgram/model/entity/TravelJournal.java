package com.psss.travelgram.model.entity;

import com.psss.travelgram.model.repository.MemoryRepository;
import java.util.ArrayList;
import java.util.Observable;


public class TravelJournal extends Observable{

    private ArrayList<Memory> memories;
    private MemoryRepository memoryRepo;


    // costruttore
    public TravelJournal(){
        memories = new ArrayList<>();
        memoryRepo = new MemoryRepository();
    }


    // set e get
    public void setMemories(ArrayList<Memory> memories) { this.memories = memories; }
    public ArrayList<Memory> getMemories() { return memories; }


    // caricamento di più Memory da Firestore
    public void loadMemories(){
        memoryRepo.loadMemories(this);
    }


    // caricamento di più Memory da Firestore (dato un paese)
    public void loadMemories(String countryName){
        memoryRepo.loadMemories(this, countryName);
    }


    // caricamento di più Memory da Firestore (dato un paese, data una lista utenti)
    public void loadMemories(String countryName, ArrayList<String> userIDs){
        memoryRepo.loadMemories(this, countryName, userIDs);
    }


    // callback, chiamata da memoryRepo quando è arrivato il risultato della query
    // notifica gli observer
    public void callback(String s) {
        setChanged();
        notifyObservers(s);
    }


    // get dei campi delle memory
    public ArrayList<String> getImageLinks(){
        ArrayList<String> imageLinks = new ArrayList<>();
        for(Memory memory : memories)
            imageLinks.add(memory.getImageLink());
        return imageLinks;
    }

    public ArrayList<String> getMemoryIDs(){
        ArrayList<String> memoryIDs = new ArrayList<>();
        for(Memory memory : memories)
            memoryIDs.add(memory.getId());
        return memoryIDs;
    }

    public ArrayList<String> getCountries(){
        ArrayList<String> countries = new ArrayList<>();
        for(Memory memory : memories)
            countries.add(memory.getCountry());
        return countries;
    }

    public ArrayList<String> getUsernames(){
        ArrayList<String> usernames = new ArrayList<>();
        for(Memory memory : memories)
            usernames.add(memory.getTravelerUsername());
        return usernames;
    }

}
