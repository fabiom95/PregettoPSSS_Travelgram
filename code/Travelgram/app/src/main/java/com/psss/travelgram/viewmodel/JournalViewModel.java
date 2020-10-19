package com.psss.travelgram.viewmodel;

import android.content.Context;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.psss.travelgram.model.entity.TravelJournal;
import com.psss.travelgram.model.entity.Traveler;
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
    private boolean following;      // indica se siamo nel tab "Friends Memories" o "My Memories"


    // costruttore
    public JournalViewModel(Context context, String countryName, boolean following) {
        jAdapter = new MutableLiveData<>();
        this.context = context;
        this.countryName = countryName;
        this.following = following;

        TJ = new TravelJournal();
        TJ.addObserver(this);

        if(following) {
            currentUser = new Traveler();
            currentUser.addObserver(this);
            currentUser.loadTraveler();
        }
        else if(countryName != null)
            TJ.loadMemories(countryName);   // carica le mie memory di un luogo
        else
            TJ.loadMemories();              // carica tutte le mie memory
    }


    // set e get
    public void setJAdapter(MemoryAdapter jAdapter) { this.jAdapter.setValue(jAdapter); }
    public MutableLiveData<MemoryAdapter> getJApadter() { return jAdapter; }



    // ricezione notifica dai Subject (il Traveler e il TravelJournal)
    @Override
    public void update(Observable o, Object arg) {

        // quando è pronto il currentUser:
        if(arg.toString().equals("loaded")){
            ArrayList<String> followingIDs = currentUser.getFollowing();
            TJ.loadMemories(countryName, followingIDs);    // carica tutte le memory di quelli che seguo (following)
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