package com.psss.travelgram.model.entity;

import android.net.Uri;

import com.psss.travelgram.model.repository.MemoryRepository;
import com.psss.travelgram.viewmodel.InsertMemoryViewModel;

import java.util.Observable;

public class  Memory extends Observable {

    private String imageLink;
    private String place;
    private String description;

    private MemoryRepository memoryRepo;


    public Memory(){
        memoryRepo = new MemoryRepository();
    }


    public void setImage(String imageLink){
        this.imageLink = imageLink;
    }
    public void setPlace(String place) { this.place = place; }
    public void setDescription(String description) { this.description = description; }

    public String getImage() {
        return imageLink;
    }
    public String getPlace() {
        return place;
    }
    public String getDescription() {
        return description;
    }


    public void insertMemory(Uri uri){
        memoryRepo.insertMemory(uri, this);
    }


    public void ready(){
        setChanged();
        notifyObservers();
    }
}
