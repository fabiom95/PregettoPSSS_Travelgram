package com.psss.travelgram.view.activity;

import android.content.Intent;
import android.os.Bundle;

import com.psss.travelgram.R;
import com.psss.travelgram.view.adapter.SectionsPagerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.psss.travelgram.viewmodel.PlaceViewModel;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;


public class PlaceActivity extends AppCompatActivity implements OnClickListener {

    private String countryName;
    private Intent intent;

    private PlaceViewModel placeViewModel;
    private SectionsPagerAdapter sectionsPagerAdapter;


    // create view
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        // parametri passati dallo ScratchMapFragment
        intent = getIntent();
        countryName = intent.getStringExtra("countryName");

        // l'adapter istanzia le pagine da mostrare nelle varie sezioni
        sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager(), countryName);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        // view model
        placeViewModel = new PlaceViewModel(countryName);

        // Toolbar
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(countryName);
        mToolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // pulsante aggiungi memory
        FloatingActionButton addMemoryBtn = findViewById(R.id.addMemoryBtn);
        addMemoryBtn.setOnClickListener(this);
    }


    // creazione dei pulsanti sulla Toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.place_menu, menu);

        MenuItem visited = menu.findItem(R.id.visited);
        MenuItem wish = menu.findItem(R.id.wish);

        visited.setChecked(intent.getBooleanExtra("isVisited", false));
        wish.setChecked(intent.getBooleanExtra("isWished", false));

        visited.setIcon(visited.isChecked() ? R.drawable.place_visited_checked : R.drawable.place_visited);   // if then else
        wish.setIcon(wish.isChecked() ? R.drawable.place_wish_checked : R.drawable.place_wish);

        return true;
    }


    // gestione dei click sulla view
    @Override
    public void onClick(View view) {
        // pulsante aggiungi memory
        if (view.getId() == R.id.addMemoryBtn) {
            Intent intent = new Intent(getApplicationContext(), InsertMemoryActivity.class);
            intent.putExtra("countryName", countryName);
            intent.putExtra("username", placeViewModel.getUsername());
            startActivityForResult(intent, 0);
        }
    }


    // si attiva quando termina l'operazione di creazione memory (per chiudere la schermata)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 1)
            finish();
    }


    // gestione dei click sulla Toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            // pulsante visited
            case R.id.visited:
                item.setChecked(!item.isChecked());

                if(item.isChecked()){
                    item.setIcon(R.drawable.place_visited_checked);
                    placeViewModel.addVisitedCountry();
                }
                else{
                    item.setIcon(R.drawable.place_visited);
                    placeViewModel.removeVisitedCountry();
                }
                return true;


            // pulsante wish
            case R.id.wish:
                item.setChecked(!item.isChecked());

                if(item.isChecked()){
                    item.setIcon(R.drawable.place_wish_checked);
                    placeViewModel.addWishedCountry();
                }
                else{
                    item.setIcon(R.drawable.place_wish);
                    placeViewModel.removeWishedCountry();
                }
                return true;


            // azione non riconosciuta
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}