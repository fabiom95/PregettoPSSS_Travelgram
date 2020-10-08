package com.psss.travelgram.view.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.psss.travelgram.R;

import java.util.Calendar;


public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    // creazione del DialogFragment
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year  = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day   = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), R.style.CustomDatePickerDialogTheme,this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        ((TextView) getActivity().findViewById(R.id.date)).setText(day + "/" + (month+1) + "/" + year);
    }
}
