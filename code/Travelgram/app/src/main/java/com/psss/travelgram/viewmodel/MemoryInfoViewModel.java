package com.psss.travelgram.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.psss.travelgram.model.entity.Memory;

import java.util.Observable;
import java.util.Observer;

public class MemoryInfoViewModel implements Observer {

    private MutableLiveData<String> message;
    private Memory memory;

    public MemoryInfoViewModel(){
        message = new MutableLiveData<>();
        message.setValue("");
        memory = new Memory();
        memory.addObserver(this);
    }

    public void setMessage(String message){
        this.message.setValue(message);
    }
    public MutableLiveData<String> getMessage() {
        return message;
    }

    public String getImage(){
        return memory.getImageLink();
    }
    public String getCountry(){
        return memory.getCountry();
    }
    public String getCity(){
        return memory.getCity();
    }
    public String getDescription(){
        return memory.getDescription();
    }
    public String getDate(){
        return memory.getDate();
    }
    public String getTravelerUsername(){
        return memory.getTravelerUsername();
    }


    public void loadMemory(String memID){
        memory.setId(memID);
        memory.loadMemory();
    }

    public void deleteMemory(){
        memory.deleteMemory();
    }


    @Override
    public void update(Observable o, Object arg) {
        setMessage(arg.toString());
    }
}
