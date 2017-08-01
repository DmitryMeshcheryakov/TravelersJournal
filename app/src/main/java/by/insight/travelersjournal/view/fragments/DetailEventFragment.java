package by.insight.travelersjournal.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
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

import java.util.Timer;
import java.util.TimerTask;

import CustomFonts.CustomCollapsingToolbarLayout;
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
import by.insight.travelersjournal.view.adapter.EventViewPagerAdapter;
import by.insight.travelersjournal.view.fragments.base.BaseFragment;
import io.realm.RealmList;

import static by.insight.travelersjournal.tools.InitUtil.initToolbar;
import static by.insight.travelersjournal.tools.TextUtils.arrayDateKey;


public class DetailEventFragment extends BaseFragment {

    @BindView(R.id.detail_event_ViewPager)
    ViewPager mDetailEventViewPager;
    @BindView(R.id.detail_event_toolbar)
    Toolbar mDetailEventToolbar;
    @BindView(R.id.detail_event_collapsing)
    CustomCollapsingToolbarLayout mDetailEventCollapsing;
    @BindView(R.id.detail_event_appbar)
    AppBarLayout mDetailEventAppbar;
    @BindView(R.id.detail_number_day_event)
    CustomTextView mDetailNumberDayEvent;
    @BindView(R.id.detail_day_of_the_week_event)
    CustomTextView mDetailDayOfTheWeekEvent;
    @BindView(R.id.detail_month_and_year_event)
    CustomTextView mDetailMonthAndYearEvent;
    @BindView(R.id.detail_time_event)
    CustomTextView mDetailTimeEvent;
    @BindView(R.id.detail_title_event)
    CustomTextView mDetailTitleEvent;
    @BindView(R.id.detail_title_event_TIL)
    CustomTextInputLayout mDetailTitleEventTIL;
    @BindView(R.id.detail_descriptions_event)
    CustomTextView mDetailDescriptionsEvent;
    @BindView(R.id.detail_description_event_TIL)
    CustomTextInputLayout mDetailDescriptionEventTIL;
    @BindView(R.id.edit_event_FAB)
    FloatingActionButton mEditEventFAB;
    Unbinder unbinder;
    private UtilRealm mUtilRealm;
    private String mEventId;
    private Timer mTimer;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_event_fragment, container, false);
        setHasOptionsMenu(true);
        unbinder = ButterKnife.bind(this, view);
        init();
        initToolbar(mDetailEventToolbar, getActivity());
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
        mDetailEventCollapsing.setTitle(" ");

    }

    private void showEvent(String id) {
        Event event = mUtilRealm.getEventById(id);
        mDetailTitleEvent.setText(event.getTitle());
        mDetailDescriptionsEvent.setText(event.getDescriptions());
        mDetailTimeEvent.setText(event.getTime());
        currentDate(event.getDate());
        RealmList<ImageEvent> imageEvents = mUtilRealm.getAllImagesEventsByEventId(id);
        mDetailEventViewPager.setAdapter(new EventViewPagerAdapter(getContext(), imageEvents));
        if (imageEvents.size() > 1) {
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {

                    mDetailEventViewPager.post(() -> mDetailEventViewPager
                            .setCurrentItem((mDetailEventViewPager.getCurrentItem() + 1) % imageEvents.size()));
                }

            };
            mTimer = new Timer();
            mTimer.schedule(timerTask, 3000, 3000);
        }


    }

    private void currentDate(Long date) {
        mDetailDayOfTheWeekEvent.setText(arrayDateKey(date, AppConstant.KEY_DAY_OF_THE_WEEK));
        mDetailMonthAndYearEvent.setText(arrayDateKey(date, AppConstant.KEY_MONTH_AND_YEAR));
        mDetailNumberDayEvent.setText(arrayDateKey(date, AppConstant.KEY_NUMBER_DAY));
    }

    @OnClick(R.id.edit_event_FAB)
    public void EditEvent() {
        mOnSelectEditEventFragmentListener.OnSelectEditEvent(mEventId);
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.detail_event_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home: {
                getFragmentManager().popBackStack();
            }
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onDestroyView() {
        if(mTimer != null) {
            mTimer.cancel();
        }
        super.onDestroyView();
        unbinder.unbind();

    }

}
