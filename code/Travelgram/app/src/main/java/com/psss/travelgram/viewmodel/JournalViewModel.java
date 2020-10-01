package com.psss.travelgram.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.psss.travelgram.view.adapter.MemoryAdapter;
import com.psss.travelgram.model.entity.TravelJournal;

import java.util.Observable;
import java.util.Observer;

public class JournalViewModel extends ViewModel implements Observer {

    private MutableLiveData<MemoryAdapter> jAdapter;
    private TravelJournal TJ;
    private Context context;

    public JournalViewModel(Context context, String countryName) {
        jAdapter = new MutableLiveData<>();
        TJ = new TravelJournal(countryName);
        TJ.addObserver(this);
        this.context = context;
    }

    public MutableLiveData<MemoryAdapter> getAdapter() {
        return jAdapter;
    }

    public void setJAdapter(MemoryAdapter jAdapter){
        this.jAdapter.setValue(jAdapter);
    }

    @Override
    public void update(Observable o, Object arg) {
        setJAdapter(new MemoryAdapter(TJ.getImageLinks(), context));
    }
}