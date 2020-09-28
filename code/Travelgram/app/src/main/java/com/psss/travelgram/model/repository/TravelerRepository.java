package com.psss.travelgram.model.repository;


import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.psss.travelgram.model.entity.Memory;
import com.psss.travelgram.model.entity.Traveler;

import java.util.ArrayList;
import java.util.Arrays;
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


    public void loadTraveler(final Traveler traveler){
        db.collection("Travelers")
                .document(userID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                traveler.setUsername(document.getData().get("username").toString());
                                traveler.setVisitedCountries( (ArrayList<String>) (document.getData().get("visited_countries")));
                                traveler.setWishedCountries( (ArrayList<String>) (document.getData().get("wished_countries")));
                            } else {
                                Log.d("PROVA", "No such document");
                            }
                        } else {
                            Log.d("PROVA", "get failed with ", task.getException());
                        }
                    }
                });
    }


    public void addVisitedCountry(String country){
        db.collection("Travelers")
                .document(userID)
                .update("visited_countries", FieldValue.arrayUnion(country));
    }

    public void addWishedCountry(String country){
        db.collection("Travelers")
                .document(userID)
                .update("wished_countries", FieldValue.arrayUnion(country));
    }

    public void removeVisitedCountry(String country){
        db.collection("Travelers")
                .document(userID)
                .update("visited_countries", FieldValue.arrayRemove(country));
    }


}


