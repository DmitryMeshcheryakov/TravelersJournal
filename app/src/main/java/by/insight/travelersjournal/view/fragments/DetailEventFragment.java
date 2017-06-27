package by.insight.travelersjournal.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.Timer;
import java.util.TimerTask;

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
import io.realm.RealmList;

import static by.insight.travelersjournal.tools.TextUtils.arrayDateKey;


public class DetailEventFragment extends Fragment {
    @BindView(R.id.detail_event_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.detail_event_collapsing)
    CollapsingToolbarLayout mCollapsingToolbarLayout;

    @BindView(R.id.detail_title_event)
    TextView mDetailTitleEvent;

    @BindView(R.id.detail_descriptions_event)
    TextView mDetailDescriptionsEvent;

    @BindView(R.id.detail_number_day_event)
    TextView mDetailNumberDayEvent;

    @BindView(R.id.detail_day_of_the_week_event)
    TextView mDetailDayOfTheWeekEvent;

    @BindView(R.id.detail_month_and_year_event)
    TextView mDetailMonthAndYearEvent;

    @BindView(R.id.detail_time_event)
    TextView mDetailTimeEvent;

    @BindView(R.id.detail_event_ViewPager)
    ViewPager mDetailEventViewPager;

    private UtilRealm mUtilRealm;
    private Unbinder mUnbinder;
    private String mEventId;
    private Timer mTimer;

    private OnSelectEditEventFragmentListener mOnSelectEditEventFragmentListener;

    public interface OnSelectEditEventFragmentListener {
        void OnSelectEditEvent(String id);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_event_fragment, container, false);
        setHasOptionsMenu(true);
        mUnbinder = ButterKnife.bind(this, view);
        init();
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
        mCollapsingToolbarLayout.setTitle(" ");
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    private void showEvent(String id) {
        Event event = mUtilRealm.getEventById(id);
        mDetailTitleEvent.setText(event.getTitle());
        mDetailDescriptionsEvent.setText(event.getDescriptions());
        mDetailTimeEvent.setText(event.getTime());
        currentDate(event.getDate());
        final RealmList<ImageEvent> imageEvents = mUtilRealm.getEventById(id).getImageEvent();
        mDetailEventViewPager.setAdapter(new EventViewPagerAdapter(getContext(), imageEvents));

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                mDetailEventViewPager.post(new Runnable() {
                    @Override
                    public void run() {
                        mDetailEventViewPager.setCurrentItem((mDetailEventViewPager.getCurrentItem()+1)%imageEvents.size());
                    }
                });
            }
        };

        mTimer = new Timer();
        mTimer.schedule(timerTask, 3000, 3000);
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
    public void onDestroyView() {
        mTimer.cancel();
        super.onDestroyView();
        mUnbinder.unbind();

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
    public void onAttach(Context context) {
        super.onAttach(context);
        mOnSelectEditEventFragmentListener = (OnSelectEditEventFragmentListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOnSelectEditEventFragmentListener = null;
    }
}
