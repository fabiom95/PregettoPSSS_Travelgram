package com.psss.travelgram.view.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

import java.io.IOException;
import java.util.ArrayList;


public class ScratchMapFragment extends Fragment implements OnMapReadyCallback, OnFeatureClickListener {

    private ScratchMapViewModel scratchMapViewModel;
    private GeoJsonLayer layer;
    private ArrayList<String> visitedCountries;
    private ArrayList<String> wishedCountries;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        scratchMapViewModel = new ScratchMapViewModel();
        visitedCountries = new ArrayList<String>();
        wishedCountries = new ArrayList<String>();
    }



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

            scratchMapViewModel.getVisitedCountries().observe(this, new Observer<ArrayList<String>>() {
                @Override
                public void onChanged(@Nullable ArrayList<String> countries) {
                    visitedCountries = countries;
                    colorVisitedCountries();
                }
            });

            scratchMapViewModel.getWishedCountries().observe(this, new Observer<ArrayList<String>>() {
                @Override
                public void onChanged(@Nullable ArrayList<String> countries) {
                    wishedCountries = countries;
                    colorWishedCountries();
                }
            });

        } catch (JSONException e) { e.printStackTrace();
        } catch (IOException e) { e.printStackTrace();
        }
    }



    // al click dello stato apre la PlaceActivity
    @Override
    public void onFeatureClick(Feature feature) {
        String countryName = feature.getProperty("name");
        Intent intent = new Intent(getActivity(), PlaceActivity.class);
        intent.putExtra("countryName", countryName);
        intent.putExtra("isVisited", visitedCountries.contains(countryName));
        intent.putExtra("isWished", wishedCountries.contains(countryName));
        startActivityForResult(intent,0);
    }


    // al termine della PlaceActivity può colorare lo stato
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String countryName = data.getStringExtra("countryName");

        switch (resultCode){
            case 1: // base
                colorCountry(R.color.base, countryName);
                visitedCountries.remove(countryName);
                wishedCountries.remove(countryName);
                break;
            case 2: // visited
                colorCountry(R.color.visited, countryName);
                if(!visitedCountries.contains(countryName))
                    visitedCountries.add(countryName);
                break;
            case 3: // wish
                colorCountry(R.color.wish, countryName);
                if(!wishedCountries.contains(countryName))
                    wishedCountries.add(countryName);
                break;
            default:
                break;
        }
    }


    public GeoJsonPolygonStyle setStyle(int color){
        GeoJsonPolygonStyle style = new GeoJsonPolygonStyle();
        style.setFillColor(getResources().getColor(color));
        style.setStrokeWidth(4);
        return style;
    }


    public void colorCountry(int color, String countryName) {
        GeoJsonPolygonStyle style = setStyle(color);

        for (GeoJsonFeature feature : layer.getFeatures()) {
            String name = feature.getProperty("name");
            if (name.equals(countryName)) {
                feature.setPolygonStyle(style);
                return;
            }
        }
    }




    public void colorVisitedCountries() {
        GeoJsonPolygonStyle style = setStyle(R.color.visited);

        for (GeoJsonFeature feature : layer.getFeatures()) {
            String name = feature.getProperty("name");
            if (visitedCountries.contains(name))
                feature.setPolygonStyle(style);
        }
    }

    public void colorWishedCountries() {
        GeoJsonPolygonStyle style = setStyle(R.color.wish);

        for (GeoJsonFeature feature : layer.getFeatures()) {
            String name = feature.getProperty("name");
            if (wishedCountries != null && wishedCountries.contains(name))
                // coloriamo di giallo solo se non è gia stato visitato
                if(visitedCountries == null)
                    feature.setPolygonStyle(style);
                else if(!visitedCountries.contains(name))
                    feature.setPolygonStyle(style);
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




}