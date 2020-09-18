package com.psss.travelgram.view.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.psss.travelgram.view.activity.PlaceActivity;
import com.psss.travelgram.viewmodel.ScratchMapViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;


import com.psss.travelgram.R;
import com.google.maps.android.data.kml.KmlLayer;
import com.google.maps.android.data.Feature;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;


public class ScratchMapFragment extends Fragment implements OnMapReadyCallback {

    public static final String COUNTRY_NAME = "com.example.travelgram.COUNTRY_NAME";
    private static final String TAG = ScratchMapFragment.class.getSimpleName();
    private GoogleMap scratchMap;
    private ScratchMapViewModel scratchMapViewModel;
    private KmlLayer layer;


    // aggiunge il livello KML
    private void addLayer(){
        try {
            if (layer != null) layer.removeLayerFromMap();

            layer = new KmlLayer(scratchMap, R.raw.world, getActivity().getApplicationContext());
            layer.addLayerToMap();

            layer.setOnFeatureClickListener(new KmlLayer.OnFeatureClickListener() {
                @Override
                public void onFeatureClick(Feature feature) {
                    Intent intent = new Intent(getActivity(), PlaceActivity.class);
                    intent.putExtra(COUNTRY_NAME, feature.getProperty("name"));
                    startActivity(intent);
                }
            });

        } catch (XmlPullParserException e) { e.printStackTrace();
        } catch (IOException e) { e.printStackTrace();
        }

    }


    @Override
    public void onMapReady(final GoogleMap scratchMap) {

        this.scratchMap = scratchMap;
        scratchMap.setMinZoomPreference(3.0f);
        scratchMap.setMaxZoomPreference(6.0f);

        // assegna lo stile
        scratchMap.setMapStyle( MapStyleOptions.loadRawResourceStyle(
                getActivity().getApplicationContext(), R.raw.country_style));

        //scratchMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(46,10), 3.5f));
        scratchMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lon), zoom));

        addLayer();

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (scratchMap != null) {
            CameraPosition pos = scratchMap.getCameraPosition();
            outState.putDouble("Lat", pos.target.latitude);
            outState.putDouble("Lon", pos.target.longitude);
            outState.putFloat("Zoom", pos.zoom);
        }
        Log.i(TAG,"stato salvato");
    }


    private double lat = 46;
    private double lon = 10;
    private float zoom = 3.5f;





    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if(savedInstanceState != null) {
            lat = savedInstanceState.getDouble("Lat");
            lon = savedInstanceState.getDouble("Lon");
            zoom = savedInstanceState.getFloat("Zoom");
        }
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