package com.psss.travelgram.viewmodel;


import android.content.Context;
import android.widget.EditText;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.psss.travelgram.R;
import com.psss.travelgram.model.entity.Traveler;
import com.psss.travelgram.model.repository.AuthRepository;

import java.util.Observable;
import java.util.Observer;


public class AuthViewModel extends ViewModel implements Observer {

    private MutableLiveData<String> taskResult;     // avvisa se l'autenticazione è riuscita
    private MutableLiveData<String> textError;      // avvisa se manca qualche campo
    private int targetID;                           // id dell'EditText su cui mostrare l'errore

    private Traveler traveler;


    public AuthViewModel() {
        taskResult = new MutableLiveData<>();
        textError = new MutableLiveData<>();
        traveler = new Traveler();
        traveler.addObserver(this);
        targetID = 0;
    }


    // ----------- operazioni sul Live Data -----------

    public MutableLiveData<String> getTaskResult() {return taskResult;}
    public MutableLiveData<String> getTextError() {return textError;}
    public int getTargetID() {return targetID;}

    public void setTaskResult(String value) { taskResult.setValue(value); }
    public void setTextError(String value) { textError.setValue(value); }


    // ----------- operazioni per il LOG IN --------------

    public boolean validCredentials(Context context, String emailText, String passwordText){
        String email = emailText.trim();   // trim elimina gli spazi all'inizio e alla fine
        String password = passwordText.trim();

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

    public void loginUser(String emailText, String passwordText) {
        String email = emailText.trim();   // trim elimina gli spazi all'inizio e alla fine
        String password = passwordText.trim();

        // inoltra la richiesta ad AuthRepository, che si interfaccia con Firebase
        // Il riferimento "this" alla classe stessa serve per ricevere messaggi da AuthRepository
        traveler.loginUser(email, password);
    }




    // ----------- operazioni per il SIGN UP -----------

    public boolean validCredentials(Context context, String usernameText, String emailText, String passwordText, String confirmPasswordText){
        String username = usernameText.trim();
        String email = emailText.trim();
        String password = passwordText.trim();
        String confirmPassword = confirmPasswordText.trim();

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

    public void signupUser(String usernameText, String emailText, String passwordText) {
        String username = usernameText.trim();
        String email = emailText.trim();
        String password = passwordText.trim();

        // inoltra la richiesta ad AuthRepository, che si interfaccia con Firebase
        // Il riferimento "this" alla classe stessa serve per ricevere messaggi da AuthRepository
        traveler.signupUser(username, email, password);

    }

    @Override
    public void update(Observable o, Object arg) {
        if(!arg.toString().equals("loaded"))
            setTaskResult(arg.toString());
    }
}