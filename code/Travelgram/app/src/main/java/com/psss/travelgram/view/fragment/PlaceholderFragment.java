package com.psss.travelgram.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.psss.travelgram.R;


public class PlaceholderFragment extends Fragment {

    public static PlaceholderFragment newInstance(String text, int icon) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putString("text", text);
        args.putInt("icon", icon);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_placeholder, container, false);

        TextView textView = root.findViewById(R.id.text);
        textView.setText(getArguments().getString("text"));

        ImageView image = root.findViewById(R.id.image);
        image.setImageDrawable(getResources().getDrawable(getArguments().getInt("icon")));

        return root;
    }
}