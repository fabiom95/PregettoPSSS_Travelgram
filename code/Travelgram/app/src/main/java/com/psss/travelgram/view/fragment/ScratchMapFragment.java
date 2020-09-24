package com.psss.travelgram.view.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.maps.android.data.geojson.GeoJsonFeature;
import com.google.maps.android.data.geojson.GeoJsonLayer;
import com.google.maps.android.data.geojson.GeoJsonPolygon;
import com.google.maps.android.data.geojson.GeoJsonPolygonStyle;
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

import org.json.JSONException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;


import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class ScratchMapFragment extends Fragment implements OnMapReadyCallback {

    public static final String COUNTRY_NAME = "com.psss.travelgram.COUNTRY_NAME";
    private static final String TAG = ScratchMapFragment.class.getSimpleName();
    private GoogleMap scratchMap;
    private ScratchMapViewModel scratchMapViewModel;
    private GeoJsonLayer layer;


    // aggiunge il livello KML
    /*private void addLayer(){
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

    }*/

    // aggiunge il livello GeoJson
    private void addLayer(){
        try {
            if (layer != null) layer.removeLayerFromMap();

            layer = new GeoJsonLayer(scratchMap, R.raw.world_j, getActivity().getApplicationContext());
            layer.addLayerToMap();

            layer.setOnFeatureClickListener(new KmlLayer.OnFeatureClickListener() {
                @Override
                public void onFeatureClick(Feature feature) {
                    /*GeoJsonFeature geometry = new GeoJsonFeature(feature.getGeometry(), null,null,null);

                    GeoJsonPolygonStyle style = new GeoJsonPolygonStyle();
                    style.setFillColor(Color.RED);

                    geometry.setPolygonStyle(style);
                    layer.addFeature(geometry);*/

                    Intent intent = new Intent(getActivity(), PlaceActivity.class);
                    intent.putExtra(COUNTRY_NAME, feature.getProperty("name"));
                    startActivityForResult(intent,0);
                }
            });

        } catch (JSONException e) { e.printStackTrace();
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

        scratchMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(46,10), 3.5f));

        addLayer();

    }


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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == 1){
            Log.d("PROVA", "cambia colore");
        }
    }

}