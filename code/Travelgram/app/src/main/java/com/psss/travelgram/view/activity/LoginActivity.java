package com.psss.travelgram.view.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

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


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // e-mail e password
        email = (EditText) findViewById(R.id.loginEmail);
        password = (EditText) findViewById(R.id.loginPassword);

        // testo per registrarsi
        TextView login = (TextView) findViewById(R.id.clickSignup);
        login.setOnClickListener(this);

        // bottone LogIn
        loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(this);

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
                        loginBtn.setVisibility(View.VISIBLE);
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
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.clickSignup:
                startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
                break;
            case R.id.loginBtn:
                loginUser();
                break;
            default:
                break;
        }
    }




    // ----------- LOG IN -----------

    private void loginUser() {
        // il controllo iniziale sul formato delle credenziali è affidato al ViewModel
        boolean isValid = authViewModel.validCredentials(getApplicationContext(), email, password);

        if(isValid){
            progressBar.setVisibility(View.VISIBLE);
            loginBtn.setVisibility(View.GONE);

            // la procedura di login è affidata al ViewModel, che a sua volta
            // l'affiderà ad AuthRepository (nel package "model")
            authViewModel.loginUser(email, password);
        }
    }


}