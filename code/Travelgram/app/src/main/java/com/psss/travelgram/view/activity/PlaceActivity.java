package com.psss.travelgram.view.activity;

import android.content.Intent;
import android.os.Bundle;

import com.psss.travelgram.R;
import com.psss.travelgram.SectionsPagerAdapter;
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
    private PlaceViewModel placeViewModel;
    private MenuItem visited;
    private MenuItem wish;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        // riceve il nome del paese
        Intent intent = getIntent();
        countryName = intent.getStringExtra("countryName");

        // l'adapter istanzia le pagine da mostrare nelle varie sezioni
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        // view model
        placeViewModel = new PlaceViewModel();

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

        // bottone aggiungi memory
        FloatingActionButton addMemoryBtn = findViewById(R.id.addMemoryBtn);
        addMemoryBtn.setOnClickListener(this);

    }


    @Override
    public void onBackPressed() {
        if(visited.isChecked())
            placeViewModel.addVisitedCountry(countryName);
        //else remove visited
        // if wish...else

        // calcolo risultato
        Intent intent = new Intent();
        intent.putExtra("countryName", countryName);
        if(visited.isChecked())
            setResult(2, intent);   // visited
        else if (wish.isChecked())
            setResult(3, intent);   // wish
        else
            setResult(1, intent);   // base
        super.onBackPressed();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.addMemoryBtn:
                Intent intent = new Intent(getApplicationContext(), InsertMemoryActivity.class);
                intent.putExtra("countryName", countryName);
                startActivity(intent);
                break;

            default:
                break;
        }
    }


    // per i bottoni della toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.visited:
                item.setChecked(!item.isChecked());
                item.setIcon(item.isChecked() ? R.drawable.place_visited_checked : R.drawable.place_visited);   // if then else
                return true;

            case R.id.wish:
                item.setChecked(!item.isChecked());
                item.setIcon(item.isChecked() ? R.drawable.place_wish_checked : R.drawable.place_wish);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.place_menu, menu);
        visited = menu.findItem(R.id.visited);
        wish = menu.findItem(R.id.wish);

        return true;
    }

}