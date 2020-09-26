package com.psss.travelgram.model.repository;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.psss.travelgram.model.entity.Memory;

import java.util.ArrayList;

public class TravelJournalRepository_temp {

    private String travelerUID;
    private FirebaseStorage storage;


    // costruttore
    public TravelJournalRepository_temp(){
        storage = FirebaseStorage.getInstance();
        travelerUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }


    public void fillMemoryArray(){
        StorageReference memoRef = storage.getReference().child(travelerUID);

    }

}
