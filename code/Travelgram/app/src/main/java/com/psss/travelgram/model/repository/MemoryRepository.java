package com.psss.travelgram.model.repository;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.psss.travelgram.model.entity.Memory;
import com.psss.travelgram.model.entity.TravelJournal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// MemoryRepository incapsula la logica di accesso alla collection Memories su Firestore

public class MemoryRepository {

    private FirebaseStorage storage;
    private FirebaseFirestore db;
    private String myUserID;


    // costruttore
    public MemoryRepository(){
        storage = FirebaseStorage.getInstance();
        db = FirebaseFirestore.getInstance();
        if(FirebaseAuth.getInstance().getCurrentUser() != null)
            myUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }


    // inserimento nuova Memory su Firestore
    public void createMemory(Uri uri, final Memory memo){

        // caricamento immagine su Storage
        final StorageReference memoRef = storage.getReference().child( myUserID + "/" + uri.getLastPathSegment());
        UploadTask uploadTask = memoRef.putFile(uri);

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // caricamento metadati su FireStore
                memoRef.getDownloadUrl().addOnSuccessListener(
                        new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                Map<String, Object> data = new HashMap<>();
                                Map<String, Object> traveler = new HashMap<>();

                                traveler.put("UID", myUserID);
                                traveler.put("username", memo.getTravelerUsername());

                                data.put("traveler", traveler);
                                data.put("imageLink", uri.toString());
                                data.put("country", memo.getCountry());
                                data.put("city", memo.getCity());
                                data.put("description", memo.getDescription());
                                data.put("date", memo.getDate());

                                db.collection("Memories")
                                        .add(data);

                                memo.callback("success");
                            }
                        }
                );
            }
        });
    }


    // eliminazione Memory da Firestore
    public void deleteMemory(final Memory memo) {

        // eliminazione da Storage
        StorageReference photoRef = storage.getReferenceFromUrl(memo.getImageLink());
        photoRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // eliminazione da Firestore
                db.collection("Memories")
                        .document(memo.getId())
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                memo.callback("removed");
                            }
                        });
            }
        });
    }


    // caricamento Memory da Firestore
    public void loadMemory(final Memory memo){
        db.collection("Memories")
                .document(memo.getId())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            memo.setImageLink(document.get("imageLink").toString());
                            memo.setCountry(document.get("country").toString());
                            memo.setCity(document.get("city").toString());
                            memo.setDescription(document.get("description").toString());
                            memo.setDate(document.get("date").toString());

                            Map <String,String> travelerInfo = (Map<String, String>) document.get("traveler");
                            memo.setTravelerUsername(travelerInfo.get("username"));

                            memo.callback("info loaded");
                        } else {
                            Log.d("PROVA", "get failed with ", task.getException());
                        }
                    }
                });
    }


    // caricamento di più Memory da Firestore
    public void loadMemories(final TravelJournal TJ){

        db.collection("Memories")
                .whereEqualTo("traveler.UID", myUserID)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) return;

                        ArrayList<Memory> memories = new ArrayList<>();
                        for (QueryDocumentSnapshot document : value) {

                            Memory memo = new Memory();
                            memo.setId(document.getId());
                            memo.setImageLink(document.get("imageLink").toString());
                            memo.setCountry(document.get("country").toString());
                            memories.add(memo);
                        }
                        TJ.setMemories(memories);
                        TJ.callback("TJ ready");
                    }
                });
    }



    // caricamento di più Memory da Firestore (dato un paese)
    public void loadMemories(final TravelJournal TJ, String country){

        db.collection("Memories")
                .whereEqualTo("traveler.UID", myUserID)
                .whereEqualTo("country", country)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) return;

                        ArrayList<Memory> memories = new ArrayList<>();
                        for (QueryDocumentSnapshot document : value) {
                            Memory memo = new Memory();
                            memo.setId(document.getId());
                            memo.setImageLink(document.get("imageLink").toString());
                            memo.setCountry(document.get("country").toString());
                            memories.add(memo);
                        }
                        TJ.setMemories(memories);
                        TJ.callback("TJ ready");
                    }
                });
    }



    // caricamento di più Memory da Firestore (dato un paese, data una lista utenti)
    public void loadMemories(final TravelJournal TJ, String country, ArrayList<String> userIDs){

        if(!userIDs.isEmpty()) {
            db.collection("Memories")
                    .whereIn("traveler.UID", userIDs)
                    .whereEqualTo("country", country)
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value,
                                            @Nullable FirebaseFirestoreException e) {
                            if (e != null) return;

                            ArrayList<Memory> memories = new ArrayList<>();
                            for (QueryDocumentSnapshot document : value) {
                                Memory memo = new Memory();
                                memo.setId(document.getId());
                                memo.setImageLink(document.get("imageLink").toString());
                                memo.setCountry(document.get("country").toString());
                                Map <String,String> travelerInfo = (Map<String, String>) document.get("traveler");
                                memo.setTravelerUsername(travelerInfo.get("username"));
                                memories.add(memo);
                            }
                            TJ.setMemories(memories);
                            TJ.callback("TJ ready");
                        }
                    });
        }
    }

}
