package com.psss.travelgram.model.entity;

import android.net.Uri;

import com.psss.travelgram.model.repository.MemoryRepository;
import com.psss.travelgram.viewmodel.InsertMemoryViewModel;

public class  Memory {

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



    public void insertMemory(Uri uri, InsertMemoryViewModel insertMemoryVM){
        memoryRepo.insertMemory(uri, this, insertMemoryVM);
        //TODO: usare una nuova memory al posto di this
    }

}
