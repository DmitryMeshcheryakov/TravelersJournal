package by.insight.travelersjournal.view.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import CustomFonts.CustomCollapsingToolbarLayout;
import CustomFonts.CustomTextEditInputLayout;
import CustomFonts.CustomTextInputLayout;
import CustomFonts.CustomTextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import by.insight.travelersjournal.AppConstant;
import by.insight.travelersjournal.R;
import by.insight.travelersjournal.database.UtilRealm;
import by.insight.travelersjournal.model.Event;
import by.insight.travelersjournal.model.ImageEvent;
import by.insight.travelersjournal.tools.UtilsValidate;
import by.insight.travelersjournal.view.adapter.EventViewPagerAdapter;
import by.insight.travelersjournal.view.fragments.base.BaseFragment;
import by.insight.travelersjournal.view.fragments.dialog_fragment.DatePickerDialogFragment;
import by.insight.travelersjournal.view.fragments.dialog_fragment.TimePickerDialogFragment;
import io.realm.RealmList;

import static by.insight.travelersjournal.tools.DateFormatter.convertTimeToString;
import static by.insight.travelersjournal.tools.DialogFragmentUtils.getInstanceDialogFragment;
import static by.insight.travelersjournal.tools.InitUtil.initToolbar;
import static by.insight.travelersjournal.tools.IntentUtils.addImagesFromGallery;
import static by.insight.travelersjournal.tools.IntentUtils.addPhotoFromCamera;
import static by.insight.travelersjournal.tools.TextUtils.arrayDateKey;
import static by.insight.travelersjournal.tools.TextUtils.textInputConvertString;
import static by.insight.travelersjournal.tools.TextUtils.textViewConvertString;
import static by.insight.travelersjournal.tools.UriUtils.getPath;
import static by.insight.travelersjournal.tools.UtilsValidate.isDateValidate;


public class AddEditEventFragment extends BaseFragment {


    @BindView(R.id.add_edit_event_toolbar)
    Toolbar mAddEditEventToolbar;
    @BindView(R.id.event_ViewPager)
    ViewPager mEventViewPager;
    @BindView(R.id.add_edit_event_collapsing)
    CustomCollapsingToolbarLayout mAddEditEventCollapsing;
    @BindView(R.id.add_edit_day_event_appbar)
    AppBarLayout mAddEditDayEventAppbar;
    @BindView(R.id.number_day_event)
    CustomTextView mNumberDayEvent;
    @BindView(R.id.day_of_the_week_event)
    CustomTextView mDayOfTheWeekEvent;
    @BindView(R.id.month_and_year_event)
    CustomTextView mMonthAndYearEvent;
    @BindView(R.id.time_event)
    CustomTextView mTimeEvent;
    @BindView(R.id.title_event_EditText)
    CustomTextEditInputLayout mTitleEventEditText;
    @BindView(R.id.title_event_TextInputLayout)
    CustomTextInputLayout mTitleEventTextInputLayout;
    @BindView(R.id.customTextEditInputLayout)
    CustomTextEditInputLayout mCustomTextEditInputLayout;
    @BindView(R.id.description_event_TextInputLayout)
    CustomTextInputLayout mDescriptionEventTextInputLayout;
    @BindView(R.id.nestedScrollView2)
    NestedScrollView mNestedScrollView2;
    @BindView(R.id.save_event_FAB)
    FloatingActionButton mSaveEventFAB;
    Unbinder unbinder;

    private boolean isTimeVal = false;

    private RealmList<ImageEvent> mImageEvents = new RealmList<>();

    private List<String> imagePath = new ArrayList<>();

    private Long mDate = new Date().getTime();

    private String mEventId;

    private UtilRealm mUtilRealm;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_edit_event_fragment, container, false);
        setHasOptionsMenu(true);
        unbinder = ButterKnife.bind(this, view);
        init();
        initToolbar(mAddEditEventToolbar, getActivity());
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mEventId = bundle.getString(AppConstant.KEY_EVENT_ID);
            if (mEventId != null) {
                showEvent(mEventId);
            }
        }
        return view;

    }

    private void init() {
        mUtilRealm = new UtilRealm();
        mAddEditEventCollapsing.setTitle(" ");
        getCurrentDateAndTime(mDate);
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
        if (UtilsValidate.isInfoValidate(mTitleEventTextInputLayout)) {

            if (mAddEventListener != null) {
                mAddEventListener.onAddEventClickListener(
                        textInputConvertString(mTitleEventTextInputLayout),
                        textInputConvertString(mDescriptionEventTextInputLayout),
                        mDate,
                        textViewConvertString(mTimeEvent),
                        mImageEvents);
            } else {

                mEditEventClickListener.onEditEventClickListener(mEventId,
                        textInputConvertString(mTitleEventTextInputLayout),
                        textInputConvertString(mDescriptionEventTextInputLayout),
                        mDate,
                        textViewConvertString(mTimeEvent),
                        mImageEvents);
            }

            getFragmentManager().popBackStack();
        }
    }

    @OnClick({R.id.day_of_the_week_event, R.id.month_and_year_event, R.id.number_day_event})
    public void getCurrentCalendar() {
        getInstanceDialogFragment(new DatePickerDialogFragment(onDateSetListener))
                .show(getFragmentManager(), AppConstant.CHANGE_DATE);

    }

    @OnClick(R.id.time_event)
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
                        imagePath.add(path);
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
        mEventViewPager.setAdapter(new EventViewPagerAdapter(getContext(), events));

    }


    private DatePickerDialog.OnDateSetListener onDateSetListener = (view, year, month, dayOfMonth) -> {
        Calendar calendar = new GregorianCalendar(year, month, dayOfMonth);
        mDate = calendar.getTimeInMillis();
        isTimeVal = true;
        getCurrentDateAndTime(mDate);
    };

    private TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            mTimeEvent.setText(convertTimeToString(hourOfDay, minute));
        }
    };


    private void showEvent(String id) {
        Event event = mUtilRealm.getEventById(id);
        mTitleEventTextInputLayout.getEditText().setText(event.getTitle());
        mDescriptionEventTextInputLayout.getEditText().setText(event.getDescriptions());
        getCurrentDateAndTime(event.getDate());
        RealmList<ImageEvent> imageEvents = mUtilRealm.getEventById(mEventId).getImageEvent();
        mEventViewPager.setAdapter(new EventViewPagerAdapter(getContext(), imageEvents));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.add_edit_event_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.add_PhotoGallery_btn: {
                startActivityForResult(Intent.createChooser
                        (addImagesFromGallery(), "Select File"), AppConstant.REQEST_GALLERY);
                return true;
            }
            case R.id.addB_PhotoCamera_btn: {
                startActivityForResult(addPhotoFromCamera(),
                        AppConstant.REQEST_CAMERA);
                return true;
            }
            case android.R.id.home: {
                getFragmentManager().popBackStack();
            }
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }
}
