package com.psss.travelgram.model.entity;

import com.psss.travelgram.model.repository.MemoryRepository;
import java.util.ArrayList;
import java.util.Observable;


public class TravelJournal extends Observable{

    private ArrayList<Memory> memories;
    private MemoryRepository memoryRepo;

    public TravelJournal(String countryName){
        memories = new ArrayList<>();
        memoryRepo = new MemoryRepository();
        loadMemories(countryName);
    }

    public ArrayList<Memory> getMemories(){
        return memories;
    }

    public void loadMemories(String countryName){
        memoryRepo.loadMemories(this, countryName);
    }

    public void setMemories(ArrayList<Memory> memories){
        this.memories = memories;
        setChanged();
        notifyObservers();
    }

    public ArrayList<String> getImageLinks(){
        ArrayList<String> imageLinks = new ArrayList<>();
        for(Memory memory : memories)
            imageLinks.add(memory.getImage());
        return imageLinks;
    }


}
