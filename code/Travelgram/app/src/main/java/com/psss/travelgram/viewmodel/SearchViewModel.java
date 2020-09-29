package com.psss.travelgram.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.psss.travelgram.MemoryAdapter;
import com.psss.travelgram.listaUtenti.TravelerAdapter;
import com.psss.travelgram.model.entity.TravelJournal;
import com.psss.travelgram.model.entity.TravelerList;

import java.util.Observable;
import java.util.Observer;


public class SearchViewModel extends ViewModel implements Observer {

    private MutableLiveData<TravelerAdapter> tAdapter;
    private TravelerList TL;
    private Context context;

    public SearchViewModel(Context context) {
        tAdapter = new MutableLiveData<>();
        TL = new TravelerList();
        TL.addObserver(this);
        this.context = context;
    }

    public MutableLiveData<TravelerAdapter> getAdapter() {
        return tAdapter;
    }

    public void setTAdapter(TravelerAdapter tAdapter){
        this.tAdapter.setValue(tAdapter);
    }

    public void searchTraveler(String s){
        TL.searchTraveler(s);
    }

    // TODO: magari togliere la dipendenza dal model
    @Override
    public void update(Observable o, Object arg) {
        setTAdapter(new TravelerAdapter(TL, context));
    }
}
