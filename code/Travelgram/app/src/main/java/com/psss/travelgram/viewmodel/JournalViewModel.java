package com.psss.travelgram.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.psss.travelgram.model.entity.TravelJournal;
import com.psss.travelgram.model.entity.Traveler;
import com.psss.travelgram.model.entity.TravelerList;
import com.psss.travelgram.view.adapter.MemoryAdapter;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class JournalViewModel extends ViewModel implements Observer {

    private MutableLiveData<MemoryAdapter> jAdapter;
    private Traveler currentUser;
    private TravelJournal TJ;
    private Context context;
    private String countryName;
    private Boolean following; // indica se siamo nella schermata dei following o in quella nostra

    public JournalViewModel(Context context, String countryName, Boolean following) {
        jAdapter = new MutableLiveData<>();
        this.countryName = countryName;
        this.following = following;

        TJ = new TravelJournal();
        TJ.addObserver(this);

        if(following) {
            currentUser = new Traveler();
            currentUser.loadTraveler();
            currentUser.addObserver(this);
        }
        else if(countryName != null)
            TJ.loadMemories(countryName);   // carica le mie memory di un luogo
        else
            TJ.loadMemories();              // carica tutte le mie memory

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

        // quando è pronto il currentUser:
        if(arg.toString().equals("loaded")){
            ArrayList<String> following = currentUser.getFollowing();
            TJ.loadMemories(countryName, following);    // carica tutte le memory di quelli che seguo (following)
        }

        else if(arg.toString().equals("TJ ready")) {
            if(countryName == null)
                setJAdapter(new MemoryAdapter(TJ.getMemoryIDs(), TJ.getImageLinks(), null, TJ.getCountries(), context));
            else if (following)
                setJAdapter(new MemoryAdapter(TJ.getMemoryIDs(), TJ.getImageLinks(), TJ.getUsernames(), null, context));
            else
                setJAdapter(new MemoryAdapter(TJ.getMemoryIDs(), TJ.getImageLinks(), null, null, context));
        }
    }
}