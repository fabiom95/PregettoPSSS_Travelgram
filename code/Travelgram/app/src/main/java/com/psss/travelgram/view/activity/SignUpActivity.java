package com.psss.travelgram.view.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.psss.travelgram.R;
import com.psss.travelgram.viewmodel.AuthViewModel;


public class SignUpActivity extends AppCompatActivity implements OnClickListener {

    private Button signupBtn;
    private ProgressBar progressBar;
    private EditText username, email, password, confirmPassword;
    private AuthViewModel authViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // ViewModel
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        authViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                try{
                    if(s.equals("success")){
                        finish();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }
                    else {
                        progressBar.setVisibility(View.GONE);
                        signupBtn.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                    }
                }catch(NullPointerException e) {e.printStackTrace();}
            }
        });

        // e-mail e password
        username = (EditText) findViewById(R.id.signupUsername);
        email = (EditText) findViewById(R.id.signupEmail);
        password = (EditText) findViewById(R.id.signupPassword);
        confirmPassword = (EditText) findViewById(R.id.signupPassword2);

        // bottone SignUp
        signupBtn = findViewById(R.id.signupBtn);
        signupBtn.setOnClickListener(this);

        // progress bar
        progressBar = findViewById(R.id.progressBar);
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signupBtn:
                registerUser();
                break;

            //TODO: bottone freccia indietro
        }
    }




    // ----------- SIGN UP -----------

    private void registerUser() {
        // il controllo iniziale sul formato delle credenziali è affidato al ViewModel
        String result = authViewModel.checkCredentials(username, email, password, confirmPassword);

        // in base al risultato, aggiorna la schermata
        switch(result){
            case "username is empty":
                username.setError(getString(R.string.username_required));
                username.requestFocus();
                break;

            case "email is empty":
                email.setError(getString(R.string.email_required));
                email.requestFocus();
                break;

            case "password is empty":
                password.setError(getString(R.string.password_required));
                password.requestFocus();
                break;

            case "password short":
                password.setError(getString(R.string.password_too_short));
                password.requestFocus();
                break;

            case "password mismatch":
                confirmPassword.setError(getString(R.string.password_mismatch));
                confirmPassword.requestFocus();
                break;

            case "OK":
                progressBar.setVisibility(View.VISIBLE);
                signupBtn.setVisibility(View.GONE);

                // la procedura di registrazione è affidata al ViewModel, che a sua volta
                // l'affiderà ad AuthRepository (nel package "model")
                authViewModel.signupUser(username, email, password);
                break;

            default:
                break;
        }
    }


}


