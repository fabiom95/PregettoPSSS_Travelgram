package com.psss.travelgram.view.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.psss.travelgram.R;
import com.psss.travelgram.viewmodel.SearchViewModel;

import java.util.ArrayList;


public class TravelerAdapter extends RecyclerView.Adapter<TravelerAdapter.MyViewHolder> {
    private ArrayList<String> usernames;
    private Context context;
    private SearchViewModel searchViewModel;


    // ---------- classe innestata ----------
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView username;
        public ToggleButton followBtn;

        // costruttore
        public MyViewHolder(View v) {
            super(v);
            username = v.findViewById(R.id.username);
            followBtn = v.findViewById(R.id.followBtn);
        }
    }
    // ---------- fine classe innestata ----------


    // costruttore
    public TravelerAdapter(ArrayList<String> usernames, Context context, SearchViewModel searchViewModel){
        this.context = context;
        this.searchViewModel = searchViewModel;
        this.usernames = usernames;
    }


    // creazione delle views
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new MyViewHolder(v);
    }


    // Riempimento di una singola view
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        holder.username.setText(usernames.get(position));
        holder.followBtn.setChecked(searchViewModel.isFollowed(position));

        // follow / unfollow
        holder.followBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.followBtn.isChecked()) {
                    searchViewModel.follow(position);       // metto il follow al traveler
                    Toast.makeText(context,"follow " + usernames.get(position), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context,"unfollow " + usernames.get(position), Toast.LENGTH_SHORT).show();
                    searchViewModel.unfollow(position);     // tolgo il follow al traveler
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return usernames.size();
    }
}