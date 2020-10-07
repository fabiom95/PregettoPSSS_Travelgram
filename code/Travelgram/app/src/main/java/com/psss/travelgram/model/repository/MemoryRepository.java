package com.psss.travelgram.model.repository;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
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


    // ----------- INSERT MEMORY -----------
    public void insertMemory(Uri uri, final Memory memo){

        // caricamento immagine su Storage
        final StorageReference memoRef = storage.getReference().child( myUserID + "/" + uri.getLastPathSegment());
        UploadTask uploadTask = memoRef.putFile(uri);
        //TODO: check immagine con quel nome già esiste (nome dell'immagine: orario)

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                // caricamento metadati su FireStore
                memoRef.getDownloadUrl().addOnSuccessListener(
                        new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                Map<String, Object> data = new HashMap<>();
                                data.put("traveler/UID", myUserID);
                                data.put("imageLink", uri.toString());
                                data.put("country", memo.getCountry());
                                data.put("city", memo.getCity());
                                data.put("description", memo.getDescription());
                                data.put("date", memo.getDate());
                                data.put("traveler/username", memo.getOwner());

                                db.collection("Memories")
                                        .add(data);

                                memo.ready("success");
                            }
                        }
                );
            }
        });
    }



    // --------------- CARICA MEMORY DA DB ---------------
    public void loadMemory(final Memory memo){
        db.collection("Memories")
                .document(memo.getId())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            memo.setImage(document.getData().get("imageLink").toString());
                            memo.setCountry(document.getData().get("country").toString());
                            memo.setCity(document.getData().get("city").toString());
                            memo.setDescription(document.getData().get("description").toString());
                            memo.setDate(document.getData().get("date").toString());
                            memo.setOwner(document.getData().get("owner").toString());
                            memo.ready("info loaded");
                        } else {
                            Log.d("PROVA", "get failed with ", task.getException());
                        }
                    }
                });

    }


    public void loadMemories(final TravelJournal TJ){

        db.collection("Memories")
                .whereEqualTo("UID", myUserID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<Memory> memories = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Memory memo = new Memory();
                                memo.setId(document.getId());
                                memo.setImage(document.getData().get("imageLink").toString());
                                memo.setCountry(document.getData().get("country").toString());
                                memo.setCity(document.getData().get("city").toString());
                                memo.setDescription(document.getData().get("description").toString());
                                memo.setDate(document.getData().get("date").toString());
                                memo.setOwner(document.getData().get("owner").toString());
                                memories.add(memo);
                            }
                            TJ.setMemories(memories);
                        } else {
                            Log.d("PROVA", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }


    public void loadMemories(final TravelJournal TJ, String country){

        db.collection("Memories")
                .whereEqualTo("UID", myUserID)
                .whereEqualTo("country", country)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<Memory> memories = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Memory memo = new Memory();
                                memo.setId(document.getId());
                                memo.setImage(document.getData().get("imageLink").toString());
                                memo.setCountry(document.getData().get("country").toString());
                                memo.setCity(document.getData().get("city").toString());
                                memo.setDescription(document.getData().get("description").toString());
                                memo.setDate(document.getData().get("date").toString());
                                memo.setOwner(document.getData().get("owner").toString());
                                memories.add(memo);
                            }
                            TJ.setMemories(memories);
                        } else {
                            Log.d("PROVA", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }


    public void loadMemories(final TravelJournal TJ, String country, ArrayList<String> userIDs){

        if(!userIDs.isEmpty()) {
            db.collection("Memories")
                    .whereIn("UID", userIDs)
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
                                memo.setImage(document.getData().get("imageLink").toString());
                                memo.setCountry(document.getData().get("country").toString());
                                //memo.setCity(document.getData().get("city").toString());
                                //memo.setDescription(document.getData().get("description").toString());
                                //memo.setDate(document.getData().get("date").toString());
                                memo.setOwner(document.getData().get("owner").toString());
                                memories.add(memo);
                            }
                            TJ.setMemories(memories);
                        }
                    });
        }
    }


}
