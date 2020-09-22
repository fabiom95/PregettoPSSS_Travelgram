package com.psss.travelgram.model.entity;

import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.psss.travelgram.model.repository.MemoryRepository;
import com.psss.travelgram.viewmodel.InsertMemoryViewModel;

public class  Memory {

    private Uri file;
    private String place;
    private String description;
    private MemoryRepository memoryRepo;


    public Memory(){
        memoryRepo = new MemoryRepository();
    }


    public void setFile(Uri file){
        this.file = file;
    }
    public void setPlace(String place) { this.place = place; }
    public void setDescription(String description) { this.description = description; }

    public Uri getFile() {
        return file;
    }

    public String getPlace() {
        return place;
    }

    public String getDescription() {
        return description;
    }



    public void insertMemory(InsertMemoryViewModel insertMemoryVM){
        memoryRepo.insertMemory(this, insertMemoryVM);
        //TODO: usare una nuova memory al posto di this
    }

}
