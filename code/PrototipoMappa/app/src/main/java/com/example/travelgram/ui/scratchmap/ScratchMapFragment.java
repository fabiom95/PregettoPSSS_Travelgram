package com.example.travelgram.ui.scratchmap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;


import com.example.travelgram.R;
import com.google.maps.android.data.kml.KmlLayer;
import com.google.maps.android.data.Feature;

import org.xmlpull.v1.XmlPullParserException;



public class ScratchMapFragment extends Fragment implements OnMapReadyCallback {

    private static final String TAG = ScratchMapFragment.class.getSimpleName();
    private GoogleMap scratchMap;



    @Override
    public void onMapReady(final GoogleMap scratchMap) {

        this.scratchMap = scratchMap;
        scratchMap.setMinZoomPreference(3.0f);
        scratchMap.setMaxZoomPreference(6.0f);
        scratchMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(46,10), 3.5f));

        // imposta lo stile della mappa
        try {
            scratchMap.setMapStyle( MapStyleOptions.loadRawResourceStyle(
                    getActivity().getApplicationContext(), R.raw.country_style));
        } catch (Resources.NotFoundException e) { Log.e(TAG, "Can't find style.");
        }

        // avvia il thread per caricare il livello KML
        new LoadXML().execute();
    }




    // ------------------------- THREAD ------------------------------------------------------------

    // thread che carica il livello KML
    private class LoadXML extends AsyncTask<String, Void, KmlLayer> {

        @Override
        protected KmlLayer doInBackground(String... params) {
            try {

                KmlLayer layer = new KmlLayer(scratchMap, R.raw.world, getActivity().getApplicationContext());
                return layer;

            } catch (Resources.NotFoundException e) { Log.e(TAG, "Can't find style. Error: ", e);
            } catch (java.io.IOException e) { Log.e(TAG, "File could not be read");
            } catch (XmlPullParserException e) { Log.e(TAG, "XML could not be parsed");
            }
            return null;
        }


        @Override
        protected void onPostExecute(KmlLayer layer) {
            layer.addLayerToMap();
            layer.setOnFeatureClickListener(new KmlLayer.OnFeatureClickListener() {
                @Override
                public void onFeatureClick(Feature feature) {
                    Toast.makeText(getActivity().getApplicationContext(), feature.getProperty("name"),Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    // ---------------------------------------------------------------------------------------------


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_scratchmap, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }


}