package com.psss.travelgram.model.repository;

import android.net.Uri;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.psss.travelgram.viewmodel.InsertMemoryViewModel;

import java.util.HashMap;
import java.util.Map;

public class TravelerRepository {

    private FirebaseFirestore db;
    private String userID;


    // costruttore
    public TravelerRepository(){
        db = FirebaseFirestore.getInstance();
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }


    public void addVisitedCountry(String country){
        Map<String, Object> data = new HashMap<>();
        data.put("visited_countries", FieldValue.arrayUnion(country));

        db.collection("Travelers")
                .document(userID)
                .update(data);
    }

}


