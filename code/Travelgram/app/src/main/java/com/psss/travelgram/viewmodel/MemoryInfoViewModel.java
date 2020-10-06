package com.psss.travelgram.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.psss.travelgram.model.entity.Memory;

import java.util.Observable;
import java.util.Observer;

public class MemoryInfoViewModel implements Observer {

    private MutableLiveData<Boolean> ready;
    private Memory memory;

    public MemoryInfoViewModel(){
        ready = new MutableLiveData<>();
        ready.setValue(false);

        memory = new Memory();
        memory.addObserver(this);
    }

    public void setReady(Boolean ready){
        this.ready.setValue(ready);
    }
    public MutableLiveData<Boolean> getReady() {
        return ready;
    }

    public String getImage(){
        return memory.getImage();
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


    public void loadMemory(String memID){
        memory.setId(memID);
        memory.loadMemory();
    }


    @Override
    public void update(Observable o, Object arg) {
        setReady(true);
    }
}
