package com.psss.travelgram.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.psss.travelgram.model.entity.Memory;

import java.util.Observable;
import java.util.Observer;


public class MemoryInfoViewModel implements Observer {

    private MutableLiveData<String> message;
    private Memory memory;


    // costruttore
    public MemoryInfoViewModel(){
        message = new MutableLiveData<>();
        message.setValue("");
        memory = new Memory();
        memory.addObserver(this);
    }

    // set e get
    public void setMessage(String message) { this.message.setValue(message); }
    public MutableLiveData<String> getMessage() { return message; }


    // get dei campi della Memory
    public String getImage() { return memory.getImageLink(); }
    public String getCountry() { return memory.getCountry(); }
    public String getCity() { return memory.getCity(); }
    public String getDescription() { return memory.getDescription(); }
    public String getDate() { return memory.getDate(); }
    public String getTravelerUsername() { return memory.getTravelerUsername(); }


    // caricamento Memory da Firestore
    public void loadMemory(String memID){
        memory.setId(memID);
        memory.loadMemory();
    }


    // eliminazione Memory da Firestore
    public void deleteMemory(){
        memory.deleteMemory();
    }


    // ricezione notifica dal Subject (la Memory)
    @Override
    public void update(Observable o, Object arg) {
        setMessage(arg.toString());
    }
}
