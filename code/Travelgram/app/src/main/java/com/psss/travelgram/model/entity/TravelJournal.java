package com.psss.travelgram.model.entity;

import com.psss.travelgram.model.repository.MemoryRepository;
import java.util.ArrayList;
import java.util.Observable;


public class TravelJournal extends Observable{

    private ArrayList<Memory> memories;
    private MemoryRepository memoryRepo;

    public TravelJournal(){
        memories = new ArrayList<>();
        memoryRepo = new MemoryRepository();
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

    public void loadMemories(String countryName, ArrayList<String> userIDs){
        memoryRepo.loadMemories(this, countryName, userIDs);
    }


    public void setMemories(ArrayList<Memory> memories){
        this.memories = memories;
        setChanged();
        notifyObservers("TJ ready");
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

    public ArrayList<String> getUsernames(){
        ArrayList<String> usernames = new ArrayList<>();
        for(Memory memory : memories)
            usernames.add(memory.getOwner());
        return usernames;
    }


}
