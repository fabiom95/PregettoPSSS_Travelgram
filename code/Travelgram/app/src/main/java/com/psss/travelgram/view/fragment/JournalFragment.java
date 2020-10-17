package com.psss.travelgram.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.psss.travelgram.R;
import com.psss.travelgram.view.adapter.MemoryAdapter;
import com.psss.travelgram.viewmodel.JournalViewModel;


public class JournalFragment extends Fragment {

    private RecyclerView recyclerView;
    private JournalViewModel journalViewModel;


    // Android Best Practice: usare uno Static Factory Method al posto
    // del costruttore per passare argomenti al nuovo Fragment
    public static JournalFragment newInstance(String countryName, boolean following) {
        JournalFragment fragment = new JournalFragment();
        Bundle bundle = new Bundle();
        bundle.putString("countryName", countryName);
        bundle.putBoolean("following", following);
        fragment.setArguments(bundle);
        return fragment;
    }


    // creazione della view
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_journal, container, false);

        recyclerView = root.findViewById(R.id.memory_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // view model
        journalViewModel = new JournalViewModel(getActivity(),
                getArguments().getString("countryName"),
                getArguments().getBoolean("following"));

        // si attiva quando è pronto il MemoryAdapter
        journalViewModel.getJApadter().observe(getViewLifecycleOwner(), new Observer<MemoryAdapter>() {
            @Override
            public void onChanged(@Nullable MemoryAdapter adapter) {
                recyclerView.setAdapter(adapter);
            }
        });

        return root;
    }

}