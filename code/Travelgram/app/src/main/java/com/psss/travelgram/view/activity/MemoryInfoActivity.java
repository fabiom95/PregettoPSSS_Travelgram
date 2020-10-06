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

public class MemoryInfoActivity extends AppCompatActivity {

    private MemoryInfoViewModel memoryInfoViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_info);

        // riceve l'id della memory
        Intent intent = getIntent();
        String memID = intent.getStringExtra("memID");
        String username = intent.getStringExtra("username");

        Log.d("PROVA", "id: "+memID);

        final ImageView image = findViewById(R.id.image);
        final TextView country = findViewById(R.id.country);
        final TextView city = findViewById(R.id.city);
        final TextView description = findViewById(R.id.description);
        final TextView date = findViewById(R.id.date);

        TextView user = findViewById(R.id.username);

        if(username != null) {
            user.setText(username);
            findViewById(R.id.username_layout).setVisibility(View.VISIBLE);
            findViewById(R.id.username_line).setVisibility(View.VISIBLE);
        }

        // view model
        memoryInfoViewModel = new MemoryInfoViewModel();
        memoryInfoViewModel.loadMemory(memID);

        memoryInfoViewModel.getReady().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean ready) {
                if(ready){
                    country.setText(memoryInfoViewModel.getCountry());
                    city.setText(memoryInfoViewModel.getCity());
                    description.setText(memoryInfoViewModel.getDescription());
                    date.setText(memoryInfoViewModel.getDate());

                    Glide.with(getApplicationContext())
                            .load(memoryInfoViewModel.getImage())
                            .apply(new RequestOptions().override(600))      // immagine a dimensione ridotta
                            .thumbnail(0.2f)                                // thumbnail per il caricamento
                            .fitCenter()
                            .into(image);

                }
            }
        });
    }
}