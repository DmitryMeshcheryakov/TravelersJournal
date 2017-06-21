package by.insight.travelersjournal.view.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import by.insight.travelersjournal.AppConstant;
import by.insight.travelersjournal.R;
import by.insight.travelersjournal.model.Event;
import by.insight.travelersjournal.model.ImageEvent;
import by.insight.travelersjournal.tools.UtilsValidate;
import by.insight.travelersjournal.view.adapter.EventViewPagerAdapter;
import by.insight.travelersjournal.view.adapter.EventsAdapter;
import by.insight.travelersjournal.view.fragments.base.BaseFragment;
import io.realm.RealmList;

import static by.insight.travelersjournal.tools.DateFormatter.convertTimeToString;
import static by.insight.travelersjournal.tools.DialogFragmentUtils.getInstanceDialogFragment;
import static by.insight.travelersjournal.tools.IntentUtils.addImagesFromGallery;
import static by.insight.travelersjournal.tools.IntentUtils.addPhotoFromCamera;
import static by.insight.travelersjournal.tools.ItemUtils.saveEvent;
import static by.insight.travelersjournal.tools.TextUtils.arrayDateKey;
import static by.insight.travelersjournal.tools.TextUtils.textInputConvertString;
import static by.insight.travelersjournal.tools.TextUtils.textViewConvertString;
import static by.insight.travelersjournal.tools.UriUtils.getPath;
import static by.insight.travelersjournal.tools.UtilsValidate.isDateValidate;


public class AddEditEventFragment extends BaseFragment {

    @BindView(R.id.title_event_TextInputLayout)
    TextInputLayout mTitleEventTextInputLayout;

    @BindView(R.id.description_event_TextInputLayout)
    TextInputLayout mDescriptionsEventTextInputLayout;

    @BindView(R.id.save_event_FAB)
    FloatingActionButton mSaveEventFAB;

    @BindView(R.id.number_day_event)
    TextView mNumberDayEvent;

    @BindView(R.id.month_and_year_event)
    TextView mMonthAndYearEvent;

    @BindView(R.id.day_of_the_week_event)
    TextView mDayOfTheWeekEvent;

    @BindView(R.id.time_event)
    TextView mTimeEvent;

    @BindView(R.id.event_ViewPager)
    ViewPager mEventViewPager;

    private boolean isTimeVal = false;

    private RealmList<ImageEvent> mImageEvents = new RealmList<>();

    private Long mDate = new Date().getTime();

    private Unbinder mUnbinder;
    private OnAddEventClickListener mEventListener;


    public interface OnAddEventClickListener {
        void onAddEventClickListener(Event event);

    }

    public void setEventListener(OnAddEventClickListener listener) {
        this.mEventListener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_edit_event, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        getCurrentDateAndTime(mDate);
        return view;

    }


    private void getCurrentDateAndTime(Long date) {
        if (isDateValidate(date) & !isTimeVal) {
            currentDate(date);
            mTimeEvent.setText(arrayDateKey(date, AppConstant.KEY_TIME));
        } else {
            currentDate(date);
        }
    }

    private void currentDate(Long date) {
        mDayOfTheWeekEvent.setText(arrayDateKey(date, AppConstant.KEY_DAY_OF_THE_WEEK));
        mMonthAndYearEvent.setText(arrayDateKey(date, AppConstant.KEY_MONTH_AND_YEAR));
        mNumberDayEvent.setText(arrayDateKey(date, AppConstant.KEY_NUMBER_DAY));
    }


    @OnClick(R.id.save_event_FAB)
    public void onClickSaveEvent() {
        if (UtilsValidate.isInfoValidate(mTitleEventTextInputLayout, mDescriptionsEventTextInputLayout)) {

            mEventListener.onAddEventClickListener(saveEvent(
                    textInputConvertString(mTitleEventTextInputLayout),
                    textInputConvertString(mDescriptionsEventTextInputLayout),
                    mDate,
                    textViewConvertString(mTimeEvent)

            ));

            getFragmentManager().popBackStack();
        }
    }

    @OnClick(R.id.calendar_layout)
    public void getCurrentCalendar() {
        getInstanceDialogFragment(new DatePickerDialogFragment(onDateSetListener))
                .show(getFragmentManager(), AppConstant.CHANGE_DATE);

    }

    @OnClick(R.id.time_layout)
    public void getCurrentTime() {
        getInstanceDialogFragment(new TimePickerDialogFragment(onTimeSetListener))
                .show(getFragmentManager(), AppConstant.CHANGE_TIME);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null || resultCode != Activity.RESULT_OK) return;

        switch (requestCode) {
            case AppConstant.REQEST_CAMERA: {
                Uri uri = data.getData();
                String path = getPath(getContext(), uri);
                ImageEvent mImageEvent = new ImageEvent();
                mImageEvent.setImagePath(path);
                mImageEvents.add(mImageEvent);


            }
            case AppConstant.REQEST_GALLERY: {

                ClipData clipData = data.getClipData();

                if (clipData != null) {
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        Uri uri = item.getUri();
                        String path = getPath(getContext(), uri);
                        ImageEvent mImageEvent = new ImageEvent();
                        mImageEvent.setImagePath(path);
                        mImageEvents.add(mImageEvent);

                    }
                }

                showImagesEvents(mImageEvents);
            }


        }


    }

    public void showImagesEvents(RealmList<ImageEvent> events) {
        this.mImageEvents = events;
        mEventViewPager.setAdapter(new EventViewPagerAdapter(getContext(), mImageEvents));

    }


    @OnClick(R.id.addB_PhotoCamera_btn)
    public void addPhotoCamera() {
        startActivityForResult(addPhotoFromCamera(),
                AppConstant.REQEST_CAMERA);

    }


    @OnClick(R.id.add_PhotoGallery_btn)
    public void addImageGallery() {
        startActivityForResult(Intent.createChooser
                (addImagesFromGallery(), "Select File"), AppConstant.REQEST_GALLERY);

    }


    private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            Calendar calendar = new GregorianCalendar(year, month, dayOfMonth);
            mDate = calendar.getTimeInMillis();
            isTimeVal = true;
            getCurrentDateAndTime(mDate);
        }
    };

    private TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            mTimeEvent.setText(convertTimeToString(hourOfDay, minute));
        }
    };


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }


}
