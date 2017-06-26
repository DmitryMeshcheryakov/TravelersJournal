package by.insight.travelersjournal.view.fragments.dialog_fragment;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import java.util.Calendar;


public class DatePickerDialogFragment extends DialogFragment  {

    private DatePickerDialog.OnDateSetListener mDateSetListener;

    public DatePickerDialogFragment() {
    }

    public DatePickerDialogFragment(DatePickerDialog.OnDateSetListener callback) {
        mDateSetListener = (DatePickerDialog.OnDateSetListener) callback;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        return  new DatePickerDialog(getActivity(),
                mDateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
    }


}
