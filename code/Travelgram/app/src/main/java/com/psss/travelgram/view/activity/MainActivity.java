package com.psss.travelgram.view.activity;

import android.os.Bundle;
import android.view.MenuItem;

import com.psss.travelgram.R;
import com.psss.travelgram.view.fragment.JournalFragment;
import com.psss.travelgram.view.fragment.NotificationsFragment;
import com.psss.travelgram.view.fragment.ScratchMapFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.psss.travelgram.view.fragment.SearchFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


public class MainActivity extends AppCompatActivity {

    final Fragment fragment1 = new ScratchMapFragment();
    final Fragment fragment2 = JournalFragment.newInstance();
    final Fragment fragment3 = new NotificationsFragment();
    //final Fragment fragment4 = new SearchFragment();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = fragment1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        //fm.beginTransaction().add(R.id.nav_host_fragment, fragment4, "4").hide(fragment4).commit();
        fm.beginTransaction().add(R.id.nav_host_fragment, fragment3, "3").hide(fragment3).commit();
        fm.beginTransaction().add(R.id.nav_host_fragment, fragment2, "2").hide(fragment2).commit();
        fm.beginTransaction().add(R.id.nav_host_fragment, fragment1, "1").commit();
        getSupportActionBar().setTitle("Scratch-map");

        navView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_scratchmap:
                        fm.beginTransaction().hide(active).show(fragment1).commit();
                        getSupportActionBar().setTitle("Scratch-map");
                        active = fragment1;
                        return true;

                    case R.id.navigation_dashboard:
                        fm.beginTransaction().hide(active).show(fragment2).commit();
                        getSupportActionBar().setTitle("Dashboard");
                        active = fragment2;
                        return true;

                    case R.id.navigation_notifications:
                        fm.beginTransaction().hide(active).show(fragment3).commit();
                        getSupportActionBar().setTitle("Notifications");
                        active = fragment3;
                        return true;

                    //caso 4 della search
                }
                    return false;
                }
        }
        );
    }
}
