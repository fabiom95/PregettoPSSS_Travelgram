package com.psss.travelgram.viewmodel;


import android.content.Context;
import android.widget.EditText;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.psss.travelgram.R;
import com.psss.travelgram.model.repository.AuthRepository;


public class AuthViewModel extends ViewModel {

    private MutableLiveData<String> taskResult;     // avvisa se l'autenticazione è riuscita
    private MutableLiveData<String> textError;      // avvisa se manca qualche campo
    private int targetID;
    private AuthRepository authRepo;


    public AuthViewModel() {
        taskResult = new MutableLiveData<>();
        textError = new MutableLiveData<>();
        authRepo = new AuthRepository();
        targetID = 0;
    }


    // ----------- operazioni sul Live Data -----------

    public LiveData<String> getTaskResult() {return taskResult;}
    public LiveData<String> getTextError() {return textError;}
    public int getTargetID() {return targetID;}

    public void setTaskResult(String value) { taskResult.setValue(value); }
    public void setTextError(String value) { textError.setValue(value); }


    // ----------- operazioni per il LOG IN --------------

    public boolean validCredentials(Context context, EditText emailText, EditText passwordText){
        String email = emailText.getText().toString().trim();   // trim elimina gli spazi all'inizio e alla fine
        String password = passwordText.getText().toString().trim();

        if (email.isEmpty()) {
            targetID = R.id.loginEmail;
            setTextError(context.getString(R.string.email_required));
            return false;
        }
        if (password.isEmpty()) {
            targetID = R.id.loginPassword;
            setTextError(context.getString(R.string.password_required));
            return false;
        }
        return true;
    }

    public void loginUser(EditText emailText, EditText passwordText) {
        String email = emailText.getText().toString().trim();   // trim elimina gli spazi all'inizio e alla fine
        String password = passwordText.getText().toString().trim();

        // inoltra la richiesta ad AuthRepository, che si interfaccia con Firebase
        // Il riferimento "this" alla classe stessa serve per ricevere messaggi da AuthRepository
        authRepo.loginUser(email, password, this);
    }




    // ----------- operazioni per il SIGN UP -----------

    public boolean validCredentials(Context context, EditText usernameText, EditText emailText, EditText passwordText, EditText confirmPasswordText){
        String username = usernameText.getText().toString().trim();
        String email = emailText.getText().toString().trim();
        String password = passwordText.getText().toString().trim();
        String confirmPassword = confirmPasswordText.getText().toString().trim();

        if (username.isEmpty()) {
            targetID = R.id.signupUsername;
            setTextError(context.getString(R.string.username_required));
            return false;
            //TODO: controllare che sia univoco
        }
        if (email.isEmpty()) {
            targetID = R.id.signupEmail;
            setTextError(context.getString(R.string.email_required));
            return false;
        }
        if (password.isEmpty()) {
            targetID = R.id.signupPassword;
            setTextError(context.getString(R.string.password_required));
            return false;
        }
        if (password.length() < 6) {
            targetID = R.id.signupPassword;
            setTextError(context.getString(R.string.password_too_short));
            return false;
        }
        if (!confirmPassword.equals(password)) {
            targetID = R.id.signupPassword2;
            setTextError(context.getString(R.string.password_mismatch));
            return false;
        }
        return true;
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