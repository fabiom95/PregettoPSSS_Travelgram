package com.psss.travelgram.model.entity;

import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Memory {

    private Uri file;

    public void insertMemory(){

        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        UploadTask uploadTask = storageRef.child("prova/"+file.getLastPathSegment()).putFile(file);

        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                //Toast.makeText(getApplicationContext(), "fallito", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                //Toast.makeText(getApplicationContext(), "successo", Toast.LENGTH_SHORT).show();
                // ...
            }
        });
    }

}
