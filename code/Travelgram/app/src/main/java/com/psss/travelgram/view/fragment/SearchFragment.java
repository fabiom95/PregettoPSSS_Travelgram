package com.psss.travelgram.view.fragment;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.psss.travelgram.R;
import com.psss.travelgram.view.adapter.TravelerAdapter;
import com.psss.travelgram.viewmodel.SearchViewModel;


public class SearchFragment extends Fragment {

    private RecyclerView recyclerView;

    private SearchViewModel searchViewModel;


    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_search, container, false);

        recyclerView = (RecyclerView) root.findViewById(R.id.search_recycler);
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // view model
        searchViewModel = new SearchViewModel(getActivity());

        // aspetta il via per l'azione successiva
        searchViewModel.getAdapter().observe(getViewLifecycleOwner(), new Observer<TravelerAdapter>() {
            @Override
            public void onChanged(@Nullable TravelerAdapter adapter) {
                recyclerView.setAdapter(adapter);
            }
        });

        // Search bar
        EditText search_bar = root.findViewById(R.id.search_bar);
        search_bar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchViewModel.searchTraveler(charSequence.toString().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });

        return root;
    }

}

