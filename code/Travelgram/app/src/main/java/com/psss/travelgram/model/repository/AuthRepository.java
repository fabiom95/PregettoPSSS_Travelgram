package com.psss.travelgram.model.repository;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.psss.travelgram.viewmodel.AuthViewModel;



public class AuthRepository {

    private String result;
    private AuthViewModel authViewModel;
    private FirebaseAuth mAuth;


    // costruttore
    public AuthRepository(){
        authViewModel = null;
        mAuth = FirebaseAuth.getInstance();
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
                        authViewModel.updateTaskResult("success");
                    else
                        authViewModel.updateTaskResult(task.getException().getMessage());
                }
        });
    }



    // ----------- SIGN UP -----------

    public void signupUser(String username, String email, String password, AuthViewModel authVM){
        authViewModel = authVM;

        // TODO: inserire anche lo username nel database Firestore

        // registrazione su Firebase
        Task<AuthResult> task = mAuth.createUserWithEmailAndPassword(email, password);

        // in ascolto dell'evento "onComplete"
        task.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                    // comunica al ViewModel i dati da mostrare alla View
                    authViewModel.updateTaskResult("success");
                else
                    authViewModel.updateTaskResult(task.getException().getMessage());
            }
        });
    }

}
