package by.insight.travelersjournal.view.activity;


import android.os.Bundle;
import android.util.Log;

import by.insight.travelersjournal.AppConstant;
import by.insight.travelersjournal.R;
import by.insight.travelersjournal.model.Event;
import by.insight.travelersjournal.model.ImageEvent;
import by.insight.travelersjournal.presenters.IEventPresenter;
import by.insight.travelersjournal.presenters.IImageEventPresenter;
import by.insight.travelersjournal.presenters.impl.EventPresenter;
import by.insight.travelersjournal.presenters.impl.ImageEventPresenter;
import by.insight.travelersjournal.tools.FragmentUtils;
import by.insight.travelersjournal.view.activity.base.BaseActivity;
import by.insight.travelersjournal.view.adapter.EventsAdapter;
import by.insight.travelersjournal.view.fragments.AddEditEventFragment;
import by.insight.travelersjournal.view.fragments.RecyclerViewEventFragment;
import io.realm.RealmList;

import static by.insight.travelersjournal.tools.BundleUtils.BundleString;
import static by.insight.travelersjournal.tools.FragmentUtils.fragmentTransactionReplace;


public class EventsActivity extends BaseActivity
        implements RecyclerViewEventFragment.EventsFragmentListener{

    private IEventPresenter mEventPresenter;
    private IImageEventPresenter mImageEventPresenter;
    private String mTravelsId;
    private RecyclerViewEventFragment mRecyclerViewEventFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        init();
    }

    private void init() {
        mEventPresenter = new EventPresenter(this);
        mImageEventPresenter = new ImageEventPresenter(this);
        mRecyclerViewEventFragment = new RecyclerViewEventFragment();
        mTravelsId = getIntent().getStringExtra(AppConstant.KEY_BUNDLE);
        BundleString(mRecyclerViewEventFragment,
                AppConstant.KEY_BUNDLE,
                mTravelsId);
        FragmentUtils.fragmentTransactionAdd(getSupportFragmentManager(),
                AppConstant.EVENT_CONTAINER,
                mRecyclerViewEventFragment);
    }

    @Override
    public void onAddEvent() {
        if (isViewIdValidate(AppConstant.EVENT_CONTAINER)) {
            final AddEditEventFragment fragment = new AddEditEventFragment();
            BundleString(fragment,
                    AppConstant.KEY_BUNDLE,
                    mTravelsId);
            fragment.setEventListener(new AddEditEventFragment.OnAddEventClickListener() {
                @Override
                public void onAddEventClickListener(Event event) {
                    mEventPresenter.addEventByTravelId(event, mTravelsId);
                    mEventPresenter.getAllEventByTravelsId(mTravelsId);

                }
            });

            fragmentTransactionReplace(getSupportFragmentManager(),
                    AppConstant.EVENT_CONTAINER,
                    fragment);
        }
    }

    private boolean isViewIdValidate(int ViewID) {
        if (findViewById(ViewID) != null)
            return true;
        else
            return false;

    }

}
