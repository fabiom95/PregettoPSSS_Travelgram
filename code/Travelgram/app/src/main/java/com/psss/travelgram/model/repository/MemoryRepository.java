package com.psss.travelgram.model.repository;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.psss.travelgram.model.entity.Memory;
import com.psss.travelgram.model.entity.TravelJournal;
import com.psss.travelgram.viewmodel.InsertMemoryViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MemoryRepository {

    // TODO: eventuale classe AccessManager per la connessione al database
    private FirebaseStorage storage;
    private FirebaseFirestore db;
    private InsertMemoryViewModel insertMemoryViewModel;
    private String userID;
    //private Uri file;
    //private String state;

    // costruttore
    public MemoryRepository(){
        storage = FirebaseStorage.getInstance();
        db = FirebaseFirestore.getInstance();
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        insertMemoryViewModel = null; ////////////
    }


    // ----------- INSERT MEMORY -----------
    public void insertMemory(Uri uri, final Memory memo, InsertMemoryViewModel insertMemoryVM){
        insertMemoryViewModel = insertMemoryVM; ///////////

        // caricamento immagine su Storage
        final StorageReference memoRef = storage.getReference().child( userID + "/" + uri.getLastPathSegment());
        UploadTask uploadTask = memoRef.putFile(uri);
        //TODO: check campi non nulli
        //TODO: check immagine con quel nome già esiste

        // failure Listener
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d("PROVA","memory non caricata!");
            }
        });

        // success listener
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.d("PROVA","memory caricata con successo!");

                // caricamento metadati su FireStore
                memoRef.getDownloadUrl().addOnSuccessListener(
                        new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                Map<String, Object> data = new HashMap<>();
                                data.put("UID", userID);
                                data.put("imageLink", uri.toString());
                                data.put("place", memo.getPlace());
                                data.put("description", memo.getDescription());

                                db.collection("Memories")
                                        .add(data);
                            }
                        }
                );
            }
        });
    }



    // --------------- CARICA MEMORY DA DB ---------------
    public void loadMemories(final TravelJournal TJ){

        db.collection("Memories")
                .whereEqualTo("UID", userID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<Memory> memories = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Memory memo = new Memory();
                                memo.setImage(document.getData().get("imageLink").toString());
                                memo.setPlace(document.getData().get("place").toString());
                                memo.setDescription(document.getData().get("description").toString());
                                Log.d("PROVA", " imageLink " + memo.getImage());
                                memories.add(memo);
                            }
                            TJ.setMemories(memories);
                        } else {
                            Log.d("PROVA", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }


}
