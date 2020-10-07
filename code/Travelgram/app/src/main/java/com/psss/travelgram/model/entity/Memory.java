package com.psss.travelgram.model.entity;

import android.net.Uri;

import com.psss.travelgram.model.repository.MemoryRepository;

import java.util.Observable;

public class  Memory extends Observable {

    private String id;
    private String imageLink;
    private String country;
    private String city;
    private String description;
    private String date;
    private String owner;   // username dell'autore

    private MemoryRepository memoryRepo;


    public Memory(){
        memoryRepo = new MemoryRepository();
    }


    public void setId(String id){ this.id = id;}
    public void setImage(String imageLink){
        this.imageLink = imageLink;
    }
    public void setCountry(String country) { this.country = country; }
    public void setCity(String city) { this.city = city; }
    public void setDescription(String description) { this.description = description; }
    public void setDate(String date) { this.date = date; }
    public void setOwner(String owner) { this.owner = owner; }

    public String getId(){return id;}
    public String getImage() {
        return imageLink;
    }
    public String getCountry() {
        return country;
    }
    public String getCity() {
        return city;
    }
    public String getDescription() {
        return description;
    }
    public String getDate() {
        return date;
    }
    public String getOwner() {
        return owner;
    }

    public void insertMemory(Uri uri){
        memoryRepo.insertMemory(uri, this);
    }

    public void loadMemory(){
        memoryRepo.loadMemory(this);
    }

    public void ready(String s){
        setChanged();
        notifyObservers(s);
    }
}
