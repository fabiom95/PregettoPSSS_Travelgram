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

public class FollowingJournalViewModel extends ViewModel implements Observer {

    private MutableLiveData<MemoryAdapter> jAdapter;
    private Traveler currentUser;
    private TravelerList TL;
    private Context context;
    private String countryName;

    ArrayList<String> allFriendsImageLinks;
    ArrayList<String> usernames;

    public FollowingJournalViewModel(Context context, String countryName) {
        jAdapter = new MutableLiveData<>();
        this.countryName = countryName;

        currentUser = new Traveler();
        currentUser.loadTraveler();
        currentUser.addObserver(this);

        TL = new TravelerList();
        TL.addObserver(this);

        this.context = context;

        allFriendsImageLinks = new ArrayList<>();
        usernames = new ArrayList<>();


/*
        TravelerList ()

        for(String id : following){
            TravelJournal TJ = new TravelJournal(countryName, id);
            TJ.loadFriendMemories(countryName, id);
            allFriendsImageLinks.addAll(TJ.getImageLinks());
            usernames.addAll()
        }*/
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
            TL.loadFollowingTravelers(following);
        }

        // quando è pronta la lista di Following dentro TL:
        else if(arg.toString().equals("Travelers ready")) {
            TL.linkJournals(countryName);
        }

        else if(arg.toString().equals("TJ ready"))
            setJAdapter(new MemoryAdapter(TL.getImageLinks(), TL.getTotalUsernames(), null, context));
    }
}