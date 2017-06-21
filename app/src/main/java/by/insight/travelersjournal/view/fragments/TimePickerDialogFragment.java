package by.insight.travelersjournal.view.fragments;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import java.util.Calendar;

public class TimePickerDialogFragment extends DialogFragment {

    private TimePickerDialog.OnTimeSetListener onTimeSetListener;

    public TimePickerDialogFragment() {
    }

    public TimePickerDialogFragment(TimePickerDialog.OnTimeSetListener callback) {
        onTimeSetListener = (TimePickerDialog.OnTimeSetListener) callback;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        return new TimePickerDialog(getActivity(),
                onTimeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true);
    }
}
