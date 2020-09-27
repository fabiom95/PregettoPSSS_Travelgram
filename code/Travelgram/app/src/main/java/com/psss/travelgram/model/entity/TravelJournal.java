package com.psss.travelgram.model.entity;

import android.util.Log;

import com.psss.travelgram.model.repository.MemoryRepository;
import com.psss.travelgram.model.repository.TravelJournalRepository_temp;
import com.psss.travelgram.viewmodel.JournalViewModel;

import java.util.ArrayList;
import java.util.Observable;

public class TravelJournal {

    private ArrayList<Memory> memories;
    private String travelerUID;
    private MemoryRepository memoryRepo;
    private JournalViewModel journalViewModel;      // osservatore

    public TravelJournal(JournalViewModel journalVM){
        memories = new ArrayList<>();
        memoryRepo = new MemoryRepository();
        journalViewModel = journalVM;
        loadMemories();
    }

    public ArrayList<Memory> getMemories(){
        return memories;
    }

    public void loadMemories(){
        memoryRepo.loadMemories(this);
    }

    public void setMemories(ArrayList<Memory> memories){
        this.memories = memories;
        journalViewModel.createAdapter();
    }


}
