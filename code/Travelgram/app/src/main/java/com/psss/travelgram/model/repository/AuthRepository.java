package com.psss.travelgram.model.repository;

import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.psss.travelgram.model.entity.Traveler;


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
                        traveler.callback("success");
                    else
                        traveler.callback(task.getException().getMessage());
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
                    traveler.createTraveler(username);
                    traveler.callback("success");
                }
                else
                    traveler.callback(task.getException().getMessage());
            }
        });
    }


}
