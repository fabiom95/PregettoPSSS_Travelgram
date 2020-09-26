package com.psss.travelgram.viewmodel;

import android.net.Uri;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.psss.travelgram.model.entity.Memory;

import static android.app.Activity.RESULT_OK;


public class InsertMemoryViewModel {

    private Memory memory;
    private MutableLiveData<String> taskResult;


    public InsertMemoryViewModel(){
        memory = new Memory();
        taskResult = new MutableLiveData<>();
    }



    // ----------- operazioni sul Live Data -----------

    public LiveData<String> getTaskResult() {return taskResult;}

    public void setTaskResult(String value) { taskResult.setValue(value); }




    public void insertMemory(int resultCode, Uri uri, AutoCompleteTextView country, EditText description){

        if (resultCode == RESULT_OK) {
            memory.setPlace(country.getText().toString());
            memory.setDescription(description.getText().toString());
            memory.insertMemory(uri, this);
        }
    }

}
