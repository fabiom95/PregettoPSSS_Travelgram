package com.psss.travelgram;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.psss.travelgram.model.entity.Memory;
import com.psss.travelgram.model.entity.TravelJournal;

import java.util.ArrayList;


public class MemoryAdapter extends RecyclerView.Adapter<MemoryAdapter.MyViewHolder> {
    private ArrayList<Memory> memories;
    private Context context;

    // ---- classe innestata
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView country;

        public MyViewHolder(View v) {
            super(v);
            country = v.findViewById(R.id.country);
            image = v.findViewById(R.id.image);
        }
    }
    // ---- fine classe innestata


    // Costruttore
    public MemoryAdapter(TravelJournal TJ, Context context) {
        this.memories = TJ.getMemories();
        this.context = context;
    }


    // Create new views (invoked by the layout manager)
    @Override
    public MemoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.journal_item, parent, false);
        return new MyViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.country.setText(memories.get(position).getPlace());
        Glide.with(context)
                .load(memories.get(position).getImage())
                .apply(new RequestOptions().override(500))      // immagine a dimensione ridotta
                .thumbnail(0.2f)                                // thumbnail per il caricamento
                .into(holder.image);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return memories.size();
    }
}
