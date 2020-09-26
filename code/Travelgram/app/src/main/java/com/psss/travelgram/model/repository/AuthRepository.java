package com.psss.travelgram.model.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.psss.travelgram.viewmodel.AuthViewModel;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;


public class AuthRepository {

    private String result;
    private AuthViewModel authViewModel;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    // costruttore
    public AuthRepository(){
        authViewModel = null;
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }


    // ----------- LOG IN -----------

    public void loginUser(String email, String password, AuthViewModel authVM){
        authViewModel = authVM;

        // registrazione su Firebase
        Task<AuthResult> task = mAuth.signInWithEmailAndPassword(email, password);

        // in ascolto dell'evento "onComplete"
        task.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful())
                        // comunica al ViewModel i dati da mostrare alla View
                        authViewModel.setTaskResult("success");
                    else
                        authViewModel.setTaskResult(task.getException().getMessage());
                }
        });
    }



    // ----------- SIGN UP -----------

    public void signupUser(final String username, final String email, final String password, AuthViewModel authVM){
        authViewModel = authVM;

         // registrazione su Firebase
        Task<AuthResult> task = mAuth.createUserWithEmailAndPassword(email, password);

        // in ascolto dell'evento "onComplete"
        task.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    // comunica al ViewModel i dati da mostrare alla View
                    createUser(username); // controllare permessi database
                    authViewModel.setTaskResult("success");

                }
                else
                    authViewModel.setTaskResult(task.getException().getMessage());
            }
        });
    }

    // Salvataggio dati utenti su Firestore
    public void createUser(String username){
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Map<String, Object> data = new HashMap<>();
        data.put("Username", username);

        db.collection("Travelers")
                .document(userID)
                .set(data);
    }

}
