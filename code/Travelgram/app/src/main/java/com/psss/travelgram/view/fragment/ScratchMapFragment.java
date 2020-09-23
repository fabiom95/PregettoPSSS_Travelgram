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
import android.widget.Toast;

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


import java.io.File;
import java.io.IOException;
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

        scratchMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(46,10), 3.5f));

        //addLayer();



        ////////////////////////
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(getResources().openRawResource(R.raw.world));

            // Get employee by tag name
            //use item(0) to get the first node with tage name "employee"
            document.get
            Node italia = document.getElementsByTagName("Placemark").item(0);
            // update employee , set the id to 10
            //NamedNodeMap attribute = italia.getAttributes();
            //Node nodeAttr = attribute.getNamedItem("name");
            //Toast.makeText(getActivity(), nodeAttr, Toast.LENGTH_SHORT).show();
            /*nodeAttr.setTextContent("10");

            // append a new node to the first employee
            Element address = document.createElement("address");

            address.appendChild(document.createTextNode("34 Stanley St."));

            employee.appendChild(address);

            // loop the employee node and update salary value, and delete a node
            NodeList nodes = employee.getChildNodes();

            for (int i = 0; i < nodes.getLength(); i++) {

                Node element = nodes.item(i);

                if ("salary".equals(element.getNodeName())) {
                    element.setTextContent("2000000");
                }

                // remove firstname
                if ("firstname".equals(element.getNodeName())) {
                    employee.removeChild(element);
                }

            }

            // write the DOM object to the file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();

            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);

            StreamResult streamResult = new StreamResult(new File(xmlFilePath));
            transformer.transform(domSource, streamResult);

            System.out.println("The XML File was ");*/

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        /*} catch (TransformerException tfe) {
            tfe.printStackTrace();*/
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (SAXException sae) {
            sae.printStackTrace();
        }


    //////////////////







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


}