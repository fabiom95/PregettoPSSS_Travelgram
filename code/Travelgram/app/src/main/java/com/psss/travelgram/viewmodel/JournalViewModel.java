package com.psss.travelgram.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.psss.travelgram.MemoryAdapter;
import com.psss.travelgram.model.entity.TravelJournal;

public class JournalViewModel extends ViewModel {

    private MutableLiveData<MemoryAdapter> mAdapter;
    private TravelJournal TJ;
    private Context context;

    public JournalViewModel(Context context) {
        mAdapter = new MutableLiveData<>();
        TJ = new TravelJournal(this);
        this.context = context;
    }

    public LiveData<MemoryAdapter> getAdapter() {
        return mAdapter;
    }


    public void createAdapter(){
        mAdapter.setValue(new MemoryAdapter(TJ, context));
    }


}