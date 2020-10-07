package com.psss.travelgram.viewmodel;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.psss.travelgram.model.entity.Memory;
import com.psss.travelgram.model.entity.Traveler;

import java.util.Observable;
import java.util.Observer;


import static android.app.Activity.RESULT_OK;


public class InsertMemoryViewModel extends ViewModel implements Observer {

    private Memory memory;
    private MutableLiveData<String> taskResult;


    // costruttore
    public InsertMemoryViewModel(){
        taskResult = new MutableLiveData<>();
        memory = new Memory();
        memory.addObserver(this);
    }



    // set e get
    public void setTaskResult(String value) {
        taskResult.setValue(value);
    }
    public MutableLiveData<String> getTaskResult() {return taskResult;}


    // inserimento nuova Memory su Firestore
    public void insertMemory(int resultCode,
                             Uri uri,
                             String country,
                             String city,
                             String description,
                             String date,
                             String username){

        if (resultCode == RESULT_OK) {
            memory.setCountry(country);
            memory.setCity(city);
            memory.setDescription(description);
            memory.setDate(date);
            memory.setTravelerUsername(username);
            memory.createMemory(uri);
        }
    }


    // ricezione notifica dal Subject (la Memory)
    @Override
    public void update(Observable o, Object arg) {
        setTaskResult(arg.toString());
        Traveler traveler = new Traveler();
        traveler.addVisitedCountry(memory.getCountry());
    }
}
