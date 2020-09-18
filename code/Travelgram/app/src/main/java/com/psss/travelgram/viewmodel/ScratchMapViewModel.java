package com.psss.travelgram.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.psss.travelgram.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.maps.android.data.kml.KmlLayer;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class ScratchMapViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ScratchMapViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("ciao");
    }

    public LiveData<String> getText() {
        return mText;
    }

}