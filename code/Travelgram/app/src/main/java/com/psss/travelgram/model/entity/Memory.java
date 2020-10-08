package com.psss.travelgram.model.entity;

import android.net.Uri;
import com.psss.travelgram.model.repository.MemoryRepository;
import java.util.Observable;


public class Memory extends Observable {

    private String id;
    private String imageLink;
    private String country;
    private String city;
    private String description;
    private String date;
    private String travelerUsername;

    private MemoryRepository memoryRepo;


    // costruttore
    public Memory(){
        memoryRepo = new MemoryRepository();
    }


    // set e get
    public void setId(String id) { this.id = id;}
    public void setImageLink(String imageLink) { this.imageLink = imageLink; }
    public void setCountry(String country) { this.country = country; }
    public void setCity(String city) { this.city = city; }
    public void setDescription(String description) { this.description = description; }
    public void setDate(String date) { this.date = date; }
    public void setTravelerUsername(String travelerUsername) { this.travelerUsername = travelerUsername; }

    public String getId() { return id;}
    public String getImageLink() { return imageLink; }
    public String getCountry() { return country; }
    public String getCity() { return city; }
    public String getDescription() { return description; }
    public String getDate() { return date; }
    public String getTravelerUsername() { return travelerUsername; }

    
    // inserimento nuova Memory su Firestore
    public void createMemory(Uri uri){
        memoryRepo.createMemory(uri, this);
    }


    // caricamento Memory da Firestore
    public void loadMemory(){
        memoryRepo.loadMemory(this);
    }


    // eliminazione Memory da Firestore
    public void deleteMemory(){
        memoryRepo.deleteMemory(this);
    }


    // callback, chiamata da memoryRepo quando è arrivato il risultato della query
    // notifica gli observer
    public void callback(String s){
        setChanged();
        notifyObservers(s);
    }
}
