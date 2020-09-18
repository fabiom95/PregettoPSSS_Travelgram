package com.psss.travelgram.view.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import com.google.firebase.auth.FirebaseUser;
import com.psss.travelgram.R;
import com.psss.travelgram.viewmodel.DashboardViewModel;


public class SignUpActivity extends AppCompatActivity implements OnClickListener {

    private Button signupBtn;
    private EditText editTextEmail, editTextPassword;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // istanza di Firebase Authentication
        mAuth = FirebaseAuth.getInstance();

        // e-mail e password
        editTextEmail = (EditText) findViewById(R.id.signupEmail);
        editTextPassword = (EditText) findViewById(R.id.signupPassword);

        // bottone SignUp
        signupBtn = findViewById(R.id.signupBtn);
        signupBtn.setOnClickListener(this);

    }



    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();   // trim elimina gli spazi all'inizio e alla fine
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty()) {
            editTextEmail.setError(getString(R.string.email_required));
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError(getString(R.string.password_required));
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError(getString(R.string.password_too_short));
            editTextPassword.requestFocus();
            return;
        }

        findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
        signupBtn.setVisibility(View.GONE);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    finish();   // per chiudere l'activity (non ha senso tornarci successivamente)
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
                else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    signupBtn.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signupBtn:
                registerUser();
                break;

            /*case R.id.textViewLogin:
                finish();
                startActivity(new Intent(this, MainActivity.class));
                break;*/
        }
    }

}


