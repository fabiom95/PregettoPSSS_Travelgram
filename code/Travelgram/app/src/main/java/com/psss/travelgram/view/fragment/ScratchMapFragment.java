package com.psss.travelgram.view.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.maps.android.data.Layer;
import com.google.maps.android.data.Layer.OnFeatureClickListener;
import com.google.maps.android.data.geojson.GeoJsonFeature;
import com.google.maps.android.data.geojson.GeoJsonLayer;
import com.google.maps.android.data.geojson.GeoJsonPolygonStyle;
import com.psss.travelgram.view.activity.PlaceActivity;
import com.psss.travelgram.viewmodel.ScratchMapViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;


import com.psss.travelgram.R;
import com.google.maps.android.data.Feature;

import org.json.JSONException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class ScratchMapFragment extends Fragment implements OnMapReadyCallback, OnFeatureClickListener {

    private ScratchMapViewModel scratchMapViewModel;
    private GeoJsonLayer layer;



    @Override
    public void onMapReady(final GoogleMap scratchMap) {

        scratchMap.setMinZoomPreference(3.0f);
        scratchMap.setMaxZoomPreference(6.0f);

        // stile della mappa di base
        scratchMap.setMapStyle( MapStyleOptions.loadRawResourceStyle(
                getActivity().getApplicationContext(), R.raw.country_style));

        scratchMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(46,10), 3.5f));

        try {
            layer = new GeoJsonLayer(scratchMap, R.raw.world_j, getActivity().getApplicationContext());
            layer.addLayerToMap();

            // stile del livello
            GeoJsonPolygonStyle style = layer.getDefaultPolygonStyle();
            style.setFillColor(getResources().getColor(R.color.base));
            style.setStrokeWidth(4);

            layer.setOnFeatureClickListener(this);

        } catch (JSONException e) { e.printStackTrace();
        } catch (IOException e) { e.printStackTrace();
        }
    }



    // al click dello stato apre la PlaceActivity
    @Override
    public void onFeatureClick(Feature feature) {
        Intent intent = new Intent(getActivity(), PlaceActivity.class);
        intent.putExtra("countryName", feature.getProperty("name"));
        startActivityForResult(intent,0);
    }


    // al termine della PlaceActivity può colorare lo stato
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode){
            case 1: // base
                colorCountry(R.color.base, data.getStringExtra("countryName"));
                break;
            case 2: // visited
                colorCountry(R.color.visited, data.getStringExtra("countryName"));
                break;
            case 3: // wish
                colorCountry(R.color.wish, data.getStringExtra("countryName"));
                break;
            default:
                break;
        }
    }


    // stile di uno specifico stato
    public void colorCountry(int color, String countryName) {
        GeoJsonPolygonStyle style = new GeoJsonPolygonStyle();
        style.setFillColor(getResources().getColor(color));
        style.setStrokeWidth(4);

        for (GeoJsonFeature feature : layer.getFeatures()) {
            if (feature.getProperty("name").equals(countryName)) {
                feature.setPolygonStyle(style);
                return;
            }
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