package com.psss.travelgram.model.repository;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.psss.travelgram.model.entity.Memory;
import com.psss.travelgram.viewmodel.InsertMemoryViewModel;

public class MemoryRepository {

    // TODO: eventuale classe AccessManager per la connessione al database
    private FirebaseStorage storage;
    private FirebaseFirestore db;  ////////////////////////////
    private InsertMemoryViewModel insertMemoryViewModel;
    private FirebaseUser currentFirebaseUser;
    //private Uri file;
    //private String state;

    // costruttore
    public MemoryRepository(){
        storage = FirebaseStorage.getInstance();
        // db = FirebaseFirestore.getInstance();
        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        insertMemoryViewModel = null;
    }

    /*
    public void setFile(Uri file) {
        this.file = file;
    }*/


    // ----------- INSERT MEMORY -----------

    // public void insertMemory(InsertMemoryViewModel insertMemoryVM){ // se si fa a livelli

    public void insertMemory(Memory memo, InsertMemoryViewModel insertMemoryVM){
        insertMemoryViewModel = insertMemoryVM;
        Uri file = memo.getFile();

        StorageReference memoRef = storage.getReference().child( currentFirebaseUser.getUid()+ "/" +file.getLastPathSegment());
        StorageMetadata metadata = new StorageMetadata.Builder()
                .setCustomMetadata("place",memo.getPlace())
                .setCustomMetadata("description", memo.getDescription())
                .build();

        //TODO: check campi non nulli
        //TODO: check immagine con quel nome già esiste
        UploadTask uploadTask = memoRef.putFile(file, metadata);

        // Listener
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                insertMemoryViewModel.setTaskResult("fallito");
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                insertMemoryViewModel.setTaskResult("successo");
            }
        });
    }

}
