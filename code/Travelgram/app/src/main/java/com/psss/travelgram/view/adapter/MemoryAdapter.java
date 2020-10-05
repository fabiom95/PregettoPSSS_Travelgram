package com.psss.travelgram.view.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.psss.travelgram.R;

import java.util.ArrayList;


public class MemoryAdapter extends RecyclerView.Adapter<MemoryAdapter.MyViewHolder> {
    private ArrayList<String> imageLinks;
    private ArrayList<String> usernames;
    private ArrayList<String> countries;
    private Context context;

    // ---- classe innestata
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView username;
        public TextView country;

        public MyViewHolder(View v) {
            super(v);
            image = v.findViewById(R.id.image);
            username = v.findViewById(R.id.username);
            country = v.findViewById(R.id.country);

            username.setVisibility(View.GONE);
            country.setVisibility(View.GONE);
        }
    }
    // ---- fine classe innestata


    // Costruttore
    public MemoryAdapter(ArrayList<String> imageLinks, ArrayList<String> usernames, ArrayList<String> countries, Context context) {
        this.imageLinks = imageLinks;
        this.usernames = usernames;
        this.countries = countries;
        this.context = context;
    }


    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.journal_item, parent, false);
        return new MyViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        if(usernames != null){
            holder.username.setVisibility(View.VISIBLE);
            holder.username.setText(usernames.get(position));
        }

        if(countries != null){
            holder.country.setVisibility(View.VISIBLE);
            holder.country.setText(countries.get(position));
        }

        // immagine
        Glide.with(context)
                .load(imageLinks.get(position))
                .apply(new RequestOptions().override(700))      // immagine a dimensione ridotta
                .thumbnail(0.2f)                                // thumbnail per il caricamento
                .into(holder.image);
    }


    @Override
    public int getItemCount() {
        return imageLinks.size();
    }
}
