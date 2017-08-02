package by.insight.travelersjournal.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.RelativeLayout;

import com.labo.kaji.fragmentanimations.CubeAnimation;
import com.labo.kaji.fragmentanimations.MoveAnimation;

import CustomFonts.CustomTextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import by.insight.travelersjournal.AppConstant;
import by.insight.travelersjournal.R;
import by.insight.travelersjournal.database.UtilRealm;
import by.insight.travelersjournal.model.DayEvents;
import by.insight.travelersjournal.model.Event;
import by.insight.travelersjournal.view.adapter.DayEventsAdapter;
import by.insight.travelersjournal.view.adapter.EventsAdapter;
import by.insight.travelersjournal.view.fragments.base.BaseFragment;
import io.realm.RealmList;

import static by.insight.travelersjournal.tools.InitUtil.initRecyclerListenerDayEvent;
import static by.insight.travelersjournal.tools.InitUtil.initToolbar;


public class RecyclerViewDayEventFragment extends BaseFragment {


    @BindView(R.id.add_edit_day_event_rv_toolbar)
    Toolbar mAddEditDayEventRvToolbar;
    @BindView(R.id.name_travel_day_event_rv)
    CustomTextView mNameTravelEventRv;
    @BindView(R.id.recycler_view_day_event)
    RecyclerView mRecyclerViewDayEvent;
    Unbinder unbinder;
    private RealmList<DayEvents> mDayEventsRealmList;
    private DayEventsAdapter mEventsAdapter;
    private UtilRealm mUtilRealm;
    private String mTravelsId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.day_event_recycler_view_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        mUtilRealm = new UtilRealm();
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mTravelsId = bundle.getString(AppConstant.KEY_TRAVEL_ID);
            showDayEvents(mUtilRealm.getAllDayEventsByTravelId(mTravelsId));
            mNameTravelEventRv.setText(mUtilRealm.getTravelById(mTravelsId).getTitle());
        }
        initToolbar(mAddEditDayEventRvToolbar, getActivity());
        initRecyclerListenerDayEvent(mRecyclerViewDayEvent, mEventsAdapter, mUtilRealm, mDayEventsRealmList, getContext());
        return view;
    }

    public void showDayEvents(RealmList<DayEvents> dayEventsRealmList) {
        this.mDayEventsRealmList = dayEventsRealmList;

        mEventsAdapter = new DayEventsAdapter(dayEventsRealmList, getContext());

        mRecyclerViewDayEvent.setAdapter(mEventsAdapter);

        //    mEventsAdapter.setEditDayEventFragmentListener(id -> mEditEventsFragmentListener.onEditEvent(id));

        mEventsAdapter.setOnItemDayEventClickListener(id -> mSelectDayEventsFragmentListener.onSelectDayEvent(id));
    }


    @OnClick(R.id.add_day_event_FAB)
    protected void addDayEvent()
    {
        mAddDayEventFragmentListener.onAddDayEvent();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
