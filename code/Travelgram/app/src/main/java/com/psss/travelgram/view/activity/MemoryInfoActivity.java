package com.psss.travelgram.view.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.psss.travelgram.R;
import com.psss.travelgram.viewmodel.MemoryInfoViewModel;

public class MemoryInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private MemoryInfoViewModel memoryInfoViewModel;


    // creazione della view
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_info);

        // parametri passati dal MemoryAdapter
        Intent intent = getIntent();
        String memID = intent.getStringExtra("memID");
        final Boolean isMine = intent.getBooleanExtra("isMine",false);


        final ImageView image       = findViewById(R.id.image);
        final TextView  country     = findViewById(R.id.country);
        final TextView  city        = findViewById(R.id.city);
        final TextView  description = findViewById(R.id.description);
        final TextView  date        = findViewById(R.id.date);
        final TextView  user        = findViewById(R.id.username);

        final ImageView deleteBtn = findViewById(R.id.deleteBtn);
        deleteBtn.setOnClickListener(this);

        // view model
        memoryInfoViewModel = new MemoryInfoViewModel();
        memoryInfoViewModel.loadMemory(memID);

        // si attiva quando termina l'operazione di caricamento Memory da Firestore
        memoryInfoViewModel.getMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String message) {
                switch (message) {
                    case "info loaded":
                        country.setText(memoryInfoViewModel.getCountry());
                        city.setText(memoryInfoViewModel.getCity());
                        description.setText(memoryInfoViewModel.getDescription());
                        date.setText(memoryInfoViewModel.getDate());

                        if(!isMine) {
                            user.setText(memoryInfoViewModel.getTravelerUsername());
                            deleteBtn.setVisibility(View.VISIBLE);
                            findViewById(R.id.username_layout).setVisibility(View.VISIBLE);
                            findViewById(R.id.username_line).setVisibility(View.VISIBLE);
                        }

                        Glide.with(getApplicationContext())
                                .load(memoryInfoViewModel.getImage())
                                .apply(new RequestOptions().override(600))      // immagine a dimensione ridotta
                                .thumbnail(0.2f)                                // thumbnail per il caricamento
                                .fitCenter()
                                .into(image);
                        break;

                    case "removed":
                        finish();
                        break;
                }
            }
        });
    }


    // gestione dei click sulla view
    @Override
    public void onClick(View v) {
        // pulsante elimina Memory
        if (v.getId() == R.id.deleteBtn)
            memoryInfoViewModel.deleteMemory();
    }
}