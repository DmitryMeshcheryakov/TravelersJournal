package by.insight.travelersjournal.view.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import by.insight.travelersjournal.AppConstant;
import by.insight.travelersjournal.R;
import by.insight.travelersjournal.presenters.ITravelPresenter;
import by.insight.travelersjournal.presenters.impl.TravelPresenter;
import by.insight.travelersjournal.tools.FragmentUtils;
import by.insight.travelersjournal.view.activity.base.BaseActivity;
import by.insight.travelersjournal.view.fragments.AddEditTravelFragment;
import by.insight.travelersjournal.view.fragments.RecyclerViewTravelFragment;


public class TravelsActivity extends BaseActivity implements
        RecyclerViewTravelFragment.TravelsFragmentListener {

    private ITravelPresenter mPresenter;
    private RecyclerViewTravelFragment mRecyclerViewTravelFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travels);
        init();
        FragmentUtils.fragmentTransactionAdd(getSupportFragmentManager(),
                AppConstant.TRAVEL_CONTAINER,
                mRecyclerViewTravelFragment);
    }

    private void init() {
        mPresenter = new TravelPresenter(this);
        mRecyclerViewTravelFragment = new RecyclerViewTravelFragment();
    }

    @Override
    public void onAddTravels() {
        if (isViewIdValidate(AppConstant.TRAVEL_CONTAINER)) {
            final AddEditTravelFragment fragment = new AddEditTravelFragment();
            fragment.setListener(new AddEditTravelFragment.OnAddTravelClickListener() {
                @Override
                public void onAddTravelClickListener(String travelTitle, String travelDescriptions, String travelImagePath) {
                    mPresenter.addTravel(travelTitle, travelDescriptions, travelImagePath);
                }
            });
            FragmentUtils.fragmentTransactionReplace(getSupportFragmentManager(),
                    AppConstant.TRAVEL_CONTAINER,
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

