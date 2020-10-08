package com.psss.travelgram.view.activity;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.psss.travelgram.R;
import com.psss.travelgram.viewmodel.AuthViewModel;


public class SignUpActivity extends AppCompatActivity implements OnClickListener {

    private Button signupBtn;
    private ProgressBar progressBar;
    private EditText username, email, password, confirmPassword;

    private AuthViewModel authViewModel;


    // create view
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // credenziali
        username        = findViewById(R.id.signupUsername);
        email           = findViewById(R.id.signupEmail);
        password        = findViewById(R.id.signupPassword);
        confirmPassword = findViewById(R.id.signupPassword2);

        // pulsante Sign Up
        signupBtn = findViewById(R.id.signupBtn);
        signupBtn.setOnClickListener(this);

        // progress bar
        progressBar = findViewById(R.id.progressBar);

        // view model
        authViewModel = new AuthViewModel();

        // si attiva al termine dell'operazione di signup
        authViewModel.getTaskResult().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                try{
                    if(s.equals("success")){
                        setResult(1);
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

        // si attiva quando c'è un errore da mostrare
        authViewModel.getTextError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                try{
                    EditText target = findViewById(authViewModel.getTargetID());

                    // personalizzazione icona di errore
                    Drawable icon = null;
                    if(target.getInputType() != 129) {  // 129: password type
                        icon = getDrawable(R.drawable.ic_error_24dp);
                        icon.setBounds(0, 0, icon.getIntrinsicWidth(), icon.getIntrinsicHeight());
                    }

                    // mostra errore
                    target.setError(s, icon);
                    target.requestFocus();

                }catch(NullPointerException e) {e.printStackTrace();}
            }
        });
    }


    // gestione dei click sulla view
    @Override
    public void onClick(View view) {
        // pulsante Sign up
        if (view.getId() == R.id.signupBtn) {
            registerUser();
        }
    }


    // Sign up
    private void registerUser() {
        String usernameText        = username.getText().toString();
        String emailText           = email.getText().toString();
        String passwordText        = password.getText().toString();
        String confirmPasswordText = confirmPassword.getText().toString();

        // controllo sul formato delle credenziali
        boolean isValid = authViewModel.validCredentials(
                getApplicationContext(),
                usernameText,
                emailText,
                passwordText,
                confirmPasswordText);

        if(isValid){
            progressBar.setVisibility(View.VISIBLE);
            signupBtn.setVisibility(View.GONE);
            authViewModel.signupUser(usernameText, emailText, passwordText);
        }
    }


}


