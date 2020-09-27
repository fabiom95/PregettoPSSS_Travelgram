package com.psss.travelgram.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.psss.travelgram.MemoryAdapter;
import com.psss.travelgram.R;
import com.psss.travelgram.model.entity.Memory;
import com.psss.travelgram.model.entity.TravelJournal;
import com.psss.travelgram.view.activity.MainActivity;
import com.psss.travelgram.viewmodel.JournalViewModel;

public class JournalFragment extends Fragment {

    private JournalViewModel journalViewModel;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;


    public static JournalFragment newInstance() {
        JournalFragment fragment = new JournalFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_journal, container, false);

        recyclerView = (RecyclerView) root.findViewById(R.id.memory_recycler);
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        // view model
        journalViewModel = new JournalViewModel(getActivity());

        // aspetta il via per l'azione successiva
        journalViewModel.getAdapter().observe(getViewLifecycleOwner(), new Observer<MemoryAdapter>() {
            @Override
            public void onChanged(@Nullable MemoryAdapter adapter) {
                recyclerView.setAdapter(adapter);
            }
        });

        return root;
    }


}