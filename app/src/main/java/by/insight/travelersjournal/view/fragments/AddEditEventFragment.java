package by.insight.travelersjournal.view.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import by.insight.travelersjournal.R;
import by.insight.travelersjournal.model.Event;


public class AddEditEventFragment extends Fragment {

    @BindView(R.id.titleEventTextInput)
    TextInputLayout titleTextInputLayout;

    @BindView(R.id.descriptionEventTextInput)
    TextInputLayout descriptionsTextInputLayout;

    @BindView(R.id.saveEventFAB)
    FloatingActionButton saveEventFAB;

    @BindView(R.id.numberDayEvent)
    TextView numberDayEvent;

    @BindView(R.id.monthAndYearEvent)
    TextView monthAndYearEvent;

    @BindView(R.id.dayOfTheWeekEvent)
    TextView dayOfTheWeekEvent;

    private String date;
    private long time;

    private Unbinder unbinder;
    private OnAddEventClickListener mListener;

    public interface OnAddEventClickListener {
        void onAddEventClickListener(Event event);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_edit_event, container, false);
        unbinder = ButterKnife.bind(this, view);
         Bundle bundle = this.getArguments();
         date = bundle.getString("date");
        if(date != null) {
            dayOfTheWeekEvent.setText(date.toString());
        }
         return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.saveEventFAB)
    public void onClickSaveEvent() {
        if (isEventInfoValidate()) {
            Event event = new Event();
            event.setTitle(titleTextInputLayout.getEditText().getText().toString());
            event.setDescriptions(descriptionsTextInputLayout.getEditText().getText().toString());

            mListener.onAddEventClickListener(event);

            getFragmentManager().popBackStack();
        }
    }

    @OnClick(R.id.calendarLayout)
    public void getCurrentCalendar()
    {
        CalendarFragment calendarFragment = new CalendarFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.EventFragmentContainer, calendarFragment);
        fragmentTransaction.addToBackStack("Calendar");
        fragmentTransaction.commit();
    }

    private boolean isEventInfoValidate() {
        return !titleTextInputLayout.getEditText().getText().toString().isEmpty() &&
                !descriptionsTextInputLayout.getEditText().getText().toString().isEmpty();

    }

    public void setListener(OnAddEventClickListener listener) {
        this.mListener = listener;
    }


}
