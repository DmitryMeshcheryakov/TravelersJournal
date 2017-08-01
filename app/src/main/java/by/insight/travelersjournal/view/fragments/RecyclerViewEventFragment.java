package by.insight.travelersjournal.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.Date;

import CustomFonts.CustomTextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import by.insight.travelersjournal.AppConstant;
import by.insight.travelersjournal.R;
import by.insight.travelersjournal.database.UtilRealm;
import by.insight.travelersjournal.model.Event;
import by.insight.travelersjournal.view.adapter.EventsAdapter;
import by.insight.travelersjournal.view.fragments.base.BaseFragment;
import io.realm.RealmList;

import static by.insight.travelersjournal.tools.InitUtil.initRecyclerListenerEvent;
import static by.insight.travelersjournal.tools.InitUtil.initToolbar;
import static by.insight.travelersjournal.tools.TextUtils.arrayDateKey;
import static by.insight.travelersjournal.tools.UtilsValidate.isDateValidate;


public class RecyclerViewEventFragment extends BaseFragment {


    @BindView(R.id.add_edit_event_rv_toolbar)
    Toolbar mAddEditEventRvToolbar;
    @BindView(R.id.number_day_event_rv)
    CustomTextView mNumberDayEventRv;
    @BindView(R.id.day_of_the_week_event_rv)
    CustomTextView mDayOfTheWeekEventRv;
    @BindView(R.id.month_and_year_event_rv)
    CustomTextView mMonthAndYearEventRv;
    @BindView(R.id.date_layout_rv)
    RelativeLayout mDateLayoutRv;
    @BindView(R.id.recycler_view_event)
    RecyclerView mRecyclerViewEvent;
    @BindView(R.id.add_event_FAB)
    FloatingActionButton mAddEventFAB;
    Unbinder unbinder;
    private EventsAdapter mAdapter;

    private RealmList<Event> mEventRealmList;
    private String mDayEventId;
    private UtilRealm mUtilRealm;
    private Long mDate = new Date().getTime();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_recycler_view_fragment, container, false);
        setHasOptionsMenu(true);
        unbinder = ButterKnife.bind(this, view);
        mUtilRealm = new UtilRealm();
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mDayEventId = bundle.getString(AppConstant.KEY_DAY_EVENT_ID);
            showEvents(mUtilRealm.getAllEventsByDayEventsId(mDayEventId));
        }
        initToolbar(mAddEditEventRvToolbar, getActivity());
        initRecyclerListenerEvent(mRecyclerViewEvent, mAdapter, mUtilRealm, mEventRealmList, getContext());
        getCurrentDate(mDate);
        return view;
    }


    public void showEvents(RealmList<Event> events) {
        this.mEventRealmList = events;

        mAdapter = new EventsAdapter(events, getContext());

        mRecyclerViewEvent.setAdapter(mAdapter);

        mAdapter.setEditEventFragmentListener(id -> mEditEventsFragmentListener.onEditEvent(id));

        mAdapter.setOnItemEventClickListener(id -> mSelectEventsFragmentListener.onSelectEvent(id));
    }

    @OnClick(R.id.add_event_FAB)
    public void addEvent() {
        mAddEventsFragmentListener.onAddEvent();
    }

    private void getCurrentDate(Long date) {
        if (isDateValidate(date)) {
            mDayOfTheWeekEventRv.setText(arrayDateKey(date, AppConstant.KEY_DAY_OF_THE_WEEK));
            mMonthAndYearEventRv.setText(arrayDateKey(date, AppConstant.KEY_MONTH_AND_YEAR));
            mNumberDayEventRv.setText(arrayDateKey(date, AppConstant.KEY_NUMBER_DAY));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }

}
