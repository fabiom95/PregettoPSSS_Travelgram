package com.psss.travelgram.view.activity;

import android.content.Intent;
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
                    target.requestFocus();

                    if(target.getInputType() == 129)  // 129: password
                        target.setError(s,null);    // altrimenti le icone si sovrappongono
                    else target.setError(s);

                }catch(NullPointerException e) {e.printStackTrace();}
            }
        });
    }

    /*
    Intent intent = new Intent(Intent.ACTION_PICK,
            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    startActivityForResult(intent, 0);
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            Uri file = data.getData();

            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference();
            StorageReference imageRef = storageRef.child("prova/"+file.getLastPathSegment());
            UploadTask uploadTask = imageRef.putFile(file);

            // Register observers to listen for when the download is done or if it fails
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                    Toast.makeText(getApplicationContext(), "fallito", Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                    Toast.makeText(getApplicationContext(), "successo", Toast.LENGTH_SHORT).show();
                    // ...
                }
            });
        }
    }*/


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