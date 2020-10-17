package com.psss.travelgram.view.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.maps.android.data.Layer.OnFeatureClickListener;
import com.google.maps.android.data.geojson.GeoJsonFeature;
import com.google.maps.android.data.geojson.GeoJsonLayer;
import com.google.maps.android.data.geojson.GeoJsonPolygonStyle;
import com.psss.travelgram.view.activity.InsertMemoryActivity;
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


public class ScratchMapFragment extends Fragment implements OnMapReadyCallback, OnFeatureClickListener, OnClickListener {

    private GeoJsonLayer layer;
    private ArrayList<String> visitedCountries;
    private ArrayList<String> wishedCountries;
    private String selectedCountry;

    private ScratchMapViewModel scratchMapViewModel;


    // creazione della view
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_scratchmap, container, false);
    }


    // appena esiste la view:
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        if (mapFragment != null)
            mapFragment.getMapAsync(this);

        scratchMapViewModel = new ScratchMapViewModel();
        visitedCountries = new ArrayList<String>();
        wishedCountries = new ArrayList<String>();

        // pulsante aggiungi memory
        FloatingActionButton addMemoryBtn = view.findViewById(R.id.addMemoryBtn);
        addMemoryBtn.setOnClickListener(this);
    }


    // appena è pronta la mappa:
    @Override
    public void onMapReady(final GoogleMap scratchMap) {

        // impostazioni camera
        scratchMap.setMinZoomPreference(3.0f);
        scratchMap.setMaxZoomPreference(6.0f);
        scratchMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(46,10), 4f));

        // stile grafico della mappa
        scratchMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                        getActivity().getApplicationContext(), R.raw.country_style));

        try {
            // livello GeoJson (i paesi del mondo)
            layer = new GeoJsonLayer(scratchMap, R.raw.world_j, getActivity().getApplicationContext());
            layer.addLayerToMap();

            // stile di default del livello
            GeoJsonPolygonStyle style = layer.getDefaultPolygonStyle();
            style.setFillColor(getResources().getColor(R.color.base));
            style.setStrokeWidth(4);
            style.setStrokeColor(getResources().getColor(R.color.baseDark));

            // rendiamo gli stati cliccabili
            layer.setOnFeatureClickListener(this);

            // si attiva la prima volta che carichiamo i dati della mappa da Firestore
            scratchMapViewModel.getFirstTime().observe(this, new Observer<Boolean>() {
                @Override
                public void onChanged(@Nullable Boolean firstTime) {
                    updateMap();
                }
            });

            // si attiva quando i dati della mappa su Firestore cambiano
            scratchMapViewModel.getVisitedCountries().observe(this, new Observer<ArrayList<String>>() {
                @Override
                public void onChanged(@Nullable ArrayList<String> countries) {
                    visitedCountries = countries;
                    updateCountry();
                }
            });

            // si attiva quando i dati della mappa su Firestore cambiano
            scratchMapViewModel.getWishedCountries().observe(this, new Observer<ArrayList<String>>() {
                @Override
                public void onChanged(@Nullable ArrayList<String> countries) {
                    wishedCountries = countries;
                    updateCountry();
                }
            });

        } catch (JSONException e) { e.printStackTrace();
        } catch (IOException e) { e.printStackTrace();
        }
    }



    // al click su uno stato apre una PlaceActivity.
    // Passa alla PlaceActivity il nome dello stato cliccato e i Boolean che indicano se lo
    // stato è contrassegnato come "visited" o come "wished"
    @Override
    public void onFeatureClick(Feature feature) {
        selectedCountry = feature.getProperty("name");
        Intent intent = new Intent(getActivity(), PlaceActivity.class);
        intent.putExtra("countryName", selectedCountry);
        intent.putExtra("isVisited", visitedCountries.contains(selectedCountry));
        intent.putExtra("isWished", wishedCountries.contains(selectedCountry));
        startActivity(intent);
    }


    // imposta uno stile grafico
    public GeoJsonPolygonStyle setStyle(int color, int colorDark){
        GeoJsonPolygonStyle style = new GeoJsonPolygonStyle();
        style.setFillColor(getResources().getColor(color));
        style.setStrokeWidth(4);
        style.setStrokeColor(getResources().getColor(colorDark));
        return style;
    }


    // aggiorna tutta la mappa
    public void updateMap() {
        GeoJsonPolygonStyle visitedStyle = setStyle(R.color.visited, R.color.visitedDark);
        GeoJsonPolygonStyle wishStyle = setStyle(R.color.wish, R.color.wishDark);

        for (GeoJsonFeature feature : layer.getFeatures()) {
            String country = feature.getProperty("name");
            if (visitedCountries.contains(country))
                feature.setPolygonStyle(visitedStyle);
            else if (wishedCountries.contains(country))
                feature.setPolygonStyle(wishStyle);
        }
    }


    // aggiorna lo stato selezionato
    public void updateCountry() {
        GeoJsonPolygonStyle visitedStyle = setStyle(R.color.visited, R.color.visitedDark);
        GeoJsonPolygonStyle wishStyle    = setStyle(R.color.wish, R.color.wishDark);
        GeoJsonPolygonStyle baseStyle    = setStyle(R.color.base, R.color.baseDark);

        for (GeoJsonFeature feature : layer.getFeatures()) {
            String country = feature.getProperty("name");

            // lavoriamo solo sullo stato selezionato
            if (country.equals(selectedCountry)) {
                if (visitedCountries.contains(country))
                    feature.setPolygonStyle(visitedStyle);
                else if (wishedCountries.contains(country))
                    feature.setPolygonStyle(wishStyle);
                else
                    feature.setPolygonStyle(baseStyle);
                return; // una volta trovato lo stato ci fermiamo
            }
        }
    }


    // gestione dei click sulla view
    @Override
    public void onClick(View view) {
        // pulsante aggiungi memory
        if (view.getId() == R.id.addMemoryBtn) {
            Intent intent = new Intent(getActivity(), InsertMemoryActivity.class);
            intent.putExtra("username", scratchMapViewModel.getUsername());
            startActivityForResult(intent, 0);
        }
    }

}