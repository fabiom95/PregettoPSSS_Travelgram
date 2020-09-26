package com.psss.travelgram.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.psss.travelgram.model.entity.TravelJournal;

public class JournalViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public JournalViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }


    public void createAdapter(){
        TravelJournal TJ = new TravelJournal();
        //mAdapter = new MemoryAdapter(TJ, getActivity());
    }


}