package com.psss.travelgram.model.repository;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.psss.travelgram.model.entity.Traveler;
import com.psss.travelgram.viewmodel.AuthViewModel;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class AuthRepository {

    private FirebaseAuth mAuth;

    // costruttore
    public AuthRepository(){
        mAuth = FirebaseAuth.getInstance();
    }


    // ----------- LOG IN -----------

    public void loginUser(String email, String password, final Traveler traveler){

        // registrazione su Firebase
        Task<AuthResult> task = mAuth.signInWithEmailAndPassword(email, password);

        // in ascolto dell'evento "onComplete"
        task.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful())
                        // comunica al ViewModel i dati da mostrare alla View
                        traveler.ready("success");
                    else
                        traveler.ready(task.getException().getMessage());
                }
        });
    }



    // ----------- SIGN UP -----------

    public void signupUser(final String username, final String email, final String password, final Traveler traveler){

         // registrazione su Firebase
        Task<AuthResult> task = mAuth.createUserWithEmailAndPassword(email, password);

        // in ascolto dell'evento "onComplete"
        task.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    // comunica al ViewModel i dati da mostrare alla View
                    traveler.createUser(username); // controllare permessi database
                    traveler.ready("success");
                }
                else
                    traveler.ready(task.getException().getMessage());
            }
        });
    }


}
