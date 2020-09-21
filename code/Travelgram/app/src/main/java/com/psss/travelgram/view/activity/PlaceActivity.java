package com.psss.travelgram.view.activity;

import android.content.Intent;
import android.os.Bundle;

import com.psss.travelgram.R;
import com.psss.travelgram.view.fragment.ScratchMapFragment;
import com.psss.travelgram.SectionsPagerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


public class PlaceActivity extends AppCompatActivity {

    public static final String COUNTRY_NAME = "com.psss.travelgram.COUNTRY_NAME";
    private String countryName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_tab);

        Intent intent = getIntent();
        countryName = intent.getStringExtra(ScratchMapFragment.COUNTRY_NAME);

        TextView countryText = findViewById(R.id.country_name);
        countryText.setText(countryName);

        // l'adapter istanzia le pagine da mostrare nelle varie sezioni
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);


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