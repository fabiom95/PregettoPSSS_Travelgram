package com.psss.travelgram.model.repository;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.psss.travelgram.view.activity.LoginActivity;
import com.psss.travelgram.view.activity.MainActivity;

import java.util.concurrent.Executor;


public class AuthRepository {

    private String result;
    FirebaseAuth mAuth;

    // costruttore
    public AuthRepository(){
        mAuth = FirebaseAuth.getInstance();
    }


    // LOG IN
    public Task<AuthResult> loginUser(String email, String password){
        // istanza di Firebase Authentication
        return mAuth.signInWithEmailAndPassword(email, password);
    }


    // SIGN UP

}
