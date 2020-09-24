package com.psss.travelgram.view.activity;

import android.content.Intent;
import android.os.Bundle;

import com.psss.travelgram.R;
import com.psss.travelgram.view.fragment.ScratchMapFragment;
import com.psss.travelgram.SectionsPagerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.psss.travelgram.viewmodel.PlaceViewModel;

import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;


public class PlaceActivity extends AppCompatActivity {

    public static final String COUNTRY_NAME = "com.psss.travelgram.COUNTRY_NAME";
    private String countryName;
    private PlaceViewModel placeViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        Intent intent = getIntent();
        countryName = intent.getStringExtra(ScratchMapFragment.COUNTRY_NAME);

        // l'adapter istanzia le pagine da mostrare nelle varie sezioni
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        // view model
        placeViewModel = new PlaceViewModel();

        // switch visited
        SwitchCompat visited = findViewById(R.id.visited);
        visited.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                       @Override
                       public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                           //TODO: controllare se effettivamente cambia
                           placeViewModel.addVisitedCountry(countryName);
                           setResult(1);    //0: country segnato come visited
                           setIntent();
                       }
                   });


                // Toolbar
                Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(countryName);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // bottone aggiungi memory
        FloatingActionButton addMemoryBtn = findViewById(R.id.addMemoryBtn);

        addMemoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), InsertMemoryActivity.class);
                intent.putExtra("countryName", countryName);
                startActivity(intent);
            }
        });
    }

}