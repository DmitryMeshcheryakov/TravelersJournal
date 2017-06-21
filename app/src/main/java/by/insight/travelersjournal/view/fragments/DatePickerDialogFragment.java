package by.insight.travelersjournal.view.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.DatePicker;
import java.util.Calendar;

import by.insight.travelersjournal.R;


public class CalendarDialogFragment extends DialogFragment  {
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    public CalendarDialogFragment() {
        // nothing to see here, move along
    }

    public CalendarDialogFragment(DatePickerDialog.OnDateSetListener callback) {
        mDateSetListener = (DatePickerDialog.OnDateSetListener) callback;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return  new DatePickerDialog(getActivity(),
                mDateSetListener,
                year,
                month,
                day);
    }


}
