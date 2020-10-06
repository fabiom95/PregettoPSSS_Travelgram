package com.psss.travelgram.model.entity;

import com.psss.travelgram.model.repository.MemoryRepository;
import java.util.ArrayList;
import java.util.Observable;


public class TravelJournal extends Observable{

    private ArrayList<Memory> memories;
    private MemoryRepository memoryRepo;
    private Traveler traveler;

    public TravelJournal(){
        memories = new ArrayList<>();
        memoryRepo = new MemoryRepository();
        traveler = null;
    }

    public TravelJournal(String countryName){
        memories = new ArrayList<>();
        memoryRepo = new MemoryRepository();
        traveler = null;
        loadMemories(countryName);
    }

    public ArrayList<Memory> getMemories(){
        return memories;
    }

    public void loadMemories(){
        memoryRepo.loadMemories(this);
    }

    public void loadMemories(String countryName){
        memoryRepo.loadMemories(this, countryName);
    }

    public void loadMemories(Traveler traveler, String countryName, String userID){
        this.traveler = traveler;
        memoryRepo.loadMemories(this, countryName, userID);
    }


    public void setMemories(ArrayList<Memory> memories){
        this.memories = memories;
        if(traveler != null)
            traveler.ready("TJ ready");
        setChanged();
        notifyObservers();
    }

    public ArrayList<String> getImageLinks(){
        ArrayList<String> imageLinks = new ArrayList<>();
        for(Memory memory : memories)
            imageLinks.add(memory.getImage());
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


    public int getMemoryCount(){
        return memories.size();
    }


}
