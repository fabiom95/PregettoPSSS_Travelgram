package com.psss.travelgram.view.activity;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

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

        // ViewModel
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        // aspetta il via per l'azione successiva
        authViewModel.getTaskResult().observe(this, new Observer<String>() {
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

        // mostra errore
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
        boolean isValid = authViewModel.validCredentials(getApplicationContext(), username, email, password, confirmPassword);

        if(isValid){
            progressBar.setVisibility(View.VISIBLE);
            signupBtn.setVisibility(View.GONE);

            // la procedura di registrazione è affidata al ViewModel, che a sua volta
            // l'affiderà ad AuthRepository (nel package "model")
            authViewModel.signupUser(username, email, password);
        }
    }


}


