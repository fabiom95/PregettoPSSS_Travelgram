package com.psss.travelgram.model.repository;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.psss.travelgram.model.entity.Memory;
import com.psss.travelgram.model.entity.TravelJournal;
import com.psss.travelgram.model.entity.Traveler;
import com.psss.travelgram.model.entity.TravelerList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TravelerRepository {

    private FirebaseFirestore db;
    private String myUserID;


    // costruttore
    public TravelerRepository(){
        db = FirebaseFirestore.getInstance();
        if(FirebaseAuth.getInstance().getCurrentUser() != null)
            myUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public String getCurrentUserID(){
        return myUserID;
    }

    // Salvataggio dati utenti su Firestore
    public void createUser(String username){
        myUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Map<String, Object> data = new HashMap<>();
        data.put("username", username);
        data.put("visited_countries", Collections.emptyList());
        data.put("wished_countries",  Collections.emptyList());
        data.put("following",  Collections.emptyList());
        data.put("followers",  Collections.emptyList());


        db.collection("Travelers")
                .document(myUserID)
                .set(data);
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
                            traveler.ready("loaded");
                        }
                    }
                });
    }


    public void loadFollowingTravelers(final TravelerList TL, ArrayList<String> userIDs){

        if(!userIDs.isEmpty()) {
            db.collection("Travelers")
                    .whereIn(FieldPath.documentId(), userIDs)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                ArrayList<Traveler> travelers = new ArrayList<>();
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Traveler traveler = new Traveler();
                                    traveler.setUsername(document.getData().get("username").toString());
                                    traveler.setUserID(document.getId());
                                    traveler.setVisitedCountries((ArrayList<String>) (document.getData().get("visited_countries")));
                                    traveler.setWishedCountries((ArrayList<String>) (document.getData().get("wished_countries")));
                                    traveler.setFollowers((ArrayList<String>) (document.getData().get("followers")));
                                    traveler.setFollowing((ArrayList<String>) (document.getData().get("following")));
                                    travelers.add(traveler);
                                }
                                TL.setTravelers(travelers);
                            } else {
                                Log.d("PROVA", "Error getting documents: ", task.getException());
                            }
                        }
                    });
        }
    }


    public void searchTravelers(final TravelerList TL, String s){

        if(s.length() == 0)
            TL.setTravelers(new ArrayList<Traveler>());
        else{
            db.collection("Travelers")
                    .orderBy("username")
                    .startAt(s)
                    .endAt(s + "\uf8ff")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                ArrayList<Traveler> travelers = new ArrayList<>();
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    if(!document.getId().equals(myUserID)) {
                                        Traveler traveler = new Traveler();
                                        traveler.setUsername(document.getData().get("username").toString());
                                        traveler.setUserID(document.getId());
                                        traveler.setVisitedCountries((ArrayList<String>) (document.getData().get("visited_countries")));
                                        traveler.setWishedCountries((ArrayList<String>) (document.getData().get("wished_countries")));
                                        traveler.setFollowers((ArrayList<String>) (document.getData().get("followers")));
                                        traveler.setFollowing((ArrayList<String>) (document.getData().get("following")));
                                        travelers.add(traveler);
                                    }
                                }

                                TL.setTravelers(travelers);
                            } else {
                                Log.d("PROVA", "Error getting documents: ", task.getException());
                            }
                        }
                    });
        }
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
    public void follow(String userID){
        db.collection("Travelers")
                .document(userID)
                .update("followers", FieldValue.arrayUnion(myUserID));
        db.collection("Travelers")
                .document(myUserID)
                .update("following", FieldValue.arrayUnion(userID));
    }

    public void unfollow(String userID){
        db.collection("Travelers")
                .document(userID)
                .update("followers", FieldValue.arrayRemove(myUserID));
        db.collection("Travelers")
                .document(myUserID)
                .update("following", FieldValue.arrayRemove(userID));
    }

}


