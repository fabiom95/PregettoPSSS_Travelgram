package com.psss.travelgram.view.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.psss.travelgram.R;
import com.psss.travelgram.viewmodel.AuthViewModel;


public class LoginActivity extends AppCompatActivity implements OnClickListener {

    private Button loginBtn;
    private ProgressBar progressBar;
    private EditText email, password;

    private AuthViewModel authViewModel;


    // creazione della view
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // credenziali
        email    = findViewById(R.id.loginEmail);
        password = findViewById(R.id.loginPassword);

        // testo per registrarsi
        TextView login = findViewById(R.id.clickSignup);
        login.setOnClickListener(this);

        // pulsante Log in
        loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(this);

        // progress bar
        progressBar = findViewById(R.id.progressBar);

        // view model
        authViewModel = new AuthViewModel();

        // si attiva al termine dell'operazione di login
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
                        loginBtn.setVisibility(View.VISIBLE);
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
    public void onClick(View v) {
        switch(v.getId()){

            // testo di sign up
            case R.id.clickSignup:
                startActivityForResult(new Intent(getApplicationContext(), SignUpActivity.class),0);
                break;

            // pulsante Log in
            case R.id.loginBtn:
                loginUser();
                break;
            default:
                break;
        }
    }


    // si attiva quando termina l'eventuale operazione di Sign up (per chiudere
    // anche la schermata di login)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 1)
            finish();
    }


    // Log in
    private void loginUser() {
        String emailText    = email.getText().toString();
        String passwordText = password.getText().toString();

        // controllo sul formato delle credenziali
        boolean isValid = authViewModel.validCredentials(
                getApplicationContext(),
                emailText,
                passwordText);

        if(isValid){
            progressBar.setVisibility(View.VISIBLE);
            loginBtn.setVisibility(View.GONE);
            authViewModel.loginUser(emailText, passwordText);
        }
    }


}