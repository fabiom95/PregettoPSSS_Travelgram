package com.psss.travelgram.viewmodel;


import android.widget.EditText;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.psss.travelgram.model.repository.AuthRepository;


public class AuthViewModel extends ViewModel {

    private MutableLiveData<String> taskResult;
    private AuthRepository authRepo = new AuthRepository();


    public AuthViewModel() {
        taskResult = new MutableLiveData<>();
    }


    // ----------- operazioni sul Live Data -----------

    public LiveData<String> getText() {
        return taskResult;
    }

    public void updateTaskResult(String message) {
        taskResult.setValue(message);
    }



    // ----------- operazioni per il LOG IN --------------

    public String checkCredentials(EditText emailText, EditText passwordText){
        String email = emailText.getText().toString().trim();   // trim elimina gli spazi all'inizio e alla fine
        String password = passwordText.getText().toString().trim();

        if (email.isEmpty()) return "email is empty";
        if (password.isEmpty()) return "password is empty";
        return "OK";
    }

    public void loginUser(EditText emailText, EditText passwordText) {
        String email = emailText.getText().toString().trim();   // trim elimina gli spazi all'inizio e alla fine
        String password = passwordText.getText().toString().trim();

        // inoltra la richiesta ad AuthRepository, che si interfaccia con Firebase
        // Il riferimento "this" alla classe stessa serve per ricevere messaggi da AuthRepository
        authRepo.loginUser(email, password, this);
    }




    // ----------- operazioni per il SIGN UP -----------

    public String checkCredentials(EditText usernameText, EditText emailText, EditText passwordText, EditText confirmPasswordText){
        String username = usernameText.getText().toString().trim();
        String email = emailText.getText().toString().trim();
        String password = passwordText.getText().toString().trim();
        String confirmPassword = confirmPasswordText.getText().toString().trim();

        if (username.isEmpty()) return "username is empty"; //TODO: controllare che sia univoco
        if (email.isEmpty()) return "email is empty";
        if (password.isEmpty()) return "password is empty";
        if (password.length() < 6) return "password short";
        if (!confirmPassword.equals(password)) return "password mismatch";
        return "OK";
    }

    public void signupUser(EditText usernameText, EditText emailText, EditText passwordText) {
        String username = usernameText.getText().toString().trim();
        String email = emailText.getText().toString().trim();
        String password = passwordText.getText().toString().trim();

        // inoltra la richiesta ad AuthRepository, che si interfaccia con Firebase
        // Il riferimento "this" alla classe stessa serve per ricevere messaggi da AuthRepository
        authRepo.signupUser(username, email, password, this);
    }

}