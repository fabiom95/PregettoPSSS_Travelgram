package com.psss.travelgram.viewmodel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.psss.travelgram.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.maps.android.data.kml.KmlLayer;
import com.psss.travelgram.model.entity.Traveler;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class ScratchMapViewModel extends ViewModel implements Observer {

    //TODO: eventualmente rimuovere observer
    private MutableLiveData<String> mText;
    private Traveler traveler;

    public ScratchMapViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("ciao");
        traveler = Traveler.getInstance();
        traveler.addObserver(this);
    }

    public LiveData<String> getText() {
        return mText;
    }


    @Override
    public void update(Observable o, Object arg) {
        Log.d("PROVA", "ciao");
    }

}