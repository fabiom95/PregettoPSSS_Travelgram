package com.example.travelgram.ui.scratchmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ScratchMapViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ScratchMapViewModel() {
        //mText = new MutableLiveData<>();
        //mText.setValue("This is the Scratch Map");
    }

    public LiveData<String> getText() {
        return mText;
    }
}