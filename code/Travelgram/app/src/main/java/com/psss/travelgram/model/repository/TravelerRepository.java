package com.psss.travelgram.model.repository;


import android.util.Log;

import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.psss.travelgram.model.entity.Traveler;

import java.util.ArrayList;

public class TravelerRepository {

    private FirebaseFirestore db;
    private String myUserID;
    private String userID;


    // costruttore
    public TravelerRepository(){
        db = FirebaseFirestore.getInstance();
        myUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    // snapshotListener è come la get(), ma rimane in ascolto, avvisando in tempo reale
    // di cambiamenti del documento
    public void loadTraveler(final Traveler traveler){
        db.collection("Travelers")
                .document(myUserID)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot snapshot,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w("PROVA", "Listen failed.", e);
                            return;
                        }

                        if (snapshot != null && snapshot.exists()) {
                            traveler.setUsername(snapshot.getData().get("username").toString());
                            traveler.setUserID(myUserID);
                            traveler.setVisitedCountries( (ArrayList<String>) (snapshot.getData().get("visited_countries")));
                            traveler.setWishedCountries( (ArrayList<String>) (snapshot.getData().get("wished_countries")));
                            traveler.setFollowers( (ArrayList<String>) (snapshot.getData().get("followers")));
                            traveler.setFollowing( (ArrayList<String>) (snapshot.getData().get("following")));
                            traveler.ready();
                        }
                    }
                });
    }




    // funzioni per PlaceActivity
    public void addVisitedCountry(String country){
        db.collection("Travelers")
                .document(myUserID)
                .update("visited_countries", FieldValue.arrayUnion(country));
    }

    public void addWishedCountry(String country){
        db.collection("Travelers")
                .document(myUserID)
                .update("wished_countries", FieldValue.arrayUnion(country));
    }

    public void removeVisitedCountry(String country){
        db.collection("Travelers")
                .document(myUserID)
                .update("visited_countries", FieldValue.arrayRemove(country));
    }

    public void removeWishedCountry(String country){
        db.collection("Travelers")
                .document(myUserID)
                .update("wished_countries", FieldValue.arrayRemove(country));
    }



    // funzioni per SearchFragment
    public void follow(){
        db.collection("Travelers")
                .document(userID)
                .update("followers", FieldValue.arrayUnion(myUserID));
        db.collection("Travelers")
                .document(myUserID)
                .update("following", FieldValue.arrayUnion(userID));
    }

    public void unfollow(){
        db.collection("Travelers")
                .document(userID)
                .update("followers", FieldValue.arrayRemove(myUserID));
        db.collection("Travelers")
                .document(myUserID)
                .update("following", FieldValue.arrayRemove(userID));
    }

}


