package by.insight.travelersjournal.view.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import by.insight.travelersjournal.AppConstant;
import by.insight.travelersjournal.R;
import by.insight.travelersjournal.database.UtilRealm;
import by.insight.travelersjournal.model.ImageEvent;
import by.insight.travelersjournal.tools.FragmentUtils;
import by.insight.travelersjournal.view.activity.base.BaseActivity;
import by.insight.travelersjournal.view.fragments.AddEditDayEventFragment;
import by.insight.travelersjournal.view.fragments.AddEditEventFragment;
import by.insight.travelersjournal.view.fragments.AddEditTravelFragment;
import by.insight.travelersjournal.view.fragments.DetailEventFragment;
import by.insight.travelersjournal.view.fragments.RecyclerViewDayEventFragment;
import by.insight.travelersjournal.view.fragments.RecyclerViewEventFragment;
import by.insight.travelersjournal.view.fragments.RecyclerViewTravelFragment;
import by.insight.travelersjournal.view.fragments.base.BaseFragment;
import io.realm.RealmList;

import static by.insight.travelersjournal.tools.BundleUtils.BundleString;
import static by.insight.travelersjournal.tools.FragmentUtils.fragmentTransactionAdd;
import static by.insight.travelersjournal.tools.FragmentUtils.fragmentTransactionReplace;


public class MainActivity extends BaseActivity implements
        BaseFragment.AddTravelsFragmentListener,
        BaseFragment.EditTravelsFragmentListener,
        BaseFragment.OnAddEventsFragmentListener,
        BaseFragment.OnEditEventsFragmentListener,
        BaseFragment.SelectTravelsFragmentListener,
        BaseFragment.OnSelectEventsFragmentListener,
        BaseFragment.OnSelectEditEventFragmentListener,
        BaseFragment.OnSelectDayEventsFragmentListener,
        BaseFragment.AddDayEventFragmentListener {

    private RecyclerViewTravelFragment mRecyclerViewTravelFragment;
    private String mTravelsId;
    private String mDayEventId;
    private UtilRealm mUtilRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        init();
        fragmentTransactionAdd(getSupportFragmentManager(),
                AppConstant.TRAVEL_CONTAINER,
                mRecyclerViewTravelFragment);
    }


    private void init() {
        mUtilRealm = new UtilRealm();
        mRecyclerViewTravelFragment = new RecyclerViewTravelFragment();
    }

    @Override
    public void onAddEvent() {
        if (isViewIdValidate(AppConstant.TRAVEL_CONTAINER)) {
            AddEditEventFragment fragment = new AddEditEventFragment();
            fragment.setAddEventClickListener((title, descriptions, date, time, imagePath) -> mUtilRealm
                    .addEventByDayEventsId(mDayEventId, title, descriptions, date, time, imagePath));

            fragmentTransactionReplace(getSupportFragmentManager(),
                    AppConstant.TRAVEL_CONTAINER,
                    fragment);
        }
    }

    @Override
    public void onAddTravels() {
        if (isViewIdValidate(AppConstant.TRAVEL_CONTAINER)) {
            AddEditTravelFragment fragment = new AddEditTravelFragment();
            fragment.setAddTravelListener((travelTitle, travelImagePath) -> mUtilRealm
                    .addTravel(travelTitle, travelImagePath));
            fragmentTransactionReplace(getSupportFragmentManager(),
                    AppConstant.TRAVEL_CONTAINER,
                    fragment);
        }
    }

    @Override
    public void onEditEvent(String id) {
        if (isViewIdValidate(AppConstant.TRAVEL_CONTAINER)) {
            AddEditEventFragment fragment = new AddEditEventFragment();
            BundleString(fragment,
                    AppConstant.KEY_EVENT_ID,
                    id);
            fragment.setEditEventClickListener((id1, title, descriptions, date, time, imagePath) -> mUtilRealm
                    .updateEventByDayEventsId(id1, title, descriptions, date, time, imagePath));

            fragmentTransactionReplace(getSupportFragmentManager(),
                    AppConstant.TRAVEL_CONTAINER,
                    fragment);
        }
    }

    @Override
    public void onEditTravels(String id) {
        if (isViewIdValidate(AppConstant.TRAVEL_CONTAINER)) {
            AddEditTravelFragment fragment = new AddEditTravelFragment();
            fragment.setEditTravelListener((id1, travelTitle, travelImagePath) -> mUtilRealm
                    .updateTravel(id1, travelTitle, travelImagePath));
            BundleString(fragment, AppConstant.KEY_TRAVEL_ID, id);
            fragmentTransactionReplace(getSupportFragmentManager(),
                    AppConstant.TRAVEL_CONTAINER,
                    fragment);
        }
    }

    @Override
    public void onSelectTravel(String id) {
        if (isViewIdValidate(AppConstant.TRAVEL_CONTAINER)) {
            mTravelsId = id;
            RecyclerViewDayEventFragment mRecyclerViewDayEventFragment = new RecyclerViewDayEventFragment();
            BundleString(mRecyclerViewDayEventFragment,
                    AppConstant.KEY_TRAVEL_ID,
                    id);
            fragmentTransactionReplace(getSupportFragmentManager(),
                    AppConstant.TRAVEL_CONTAINER,
                    mRecyclerViewDayEventFragment);
        }
    }

    @Override
    public void onSelectEvent(String id) {
        if (isViewIdValidate(AppConstant.TRAVEL_CONTAINER)) {
            DetailEventFragment fragment = new DetailEventFragment();
            BundleString(fragment,
                    AppConstant.KEY_EVENT_ID,
                    id);
            fragmentTransactionReplace(getSupportFragmentManager(),
                    AppConstant.TRAVEL_CONTAINER,
                    fragment);
        }
    }

    @Override
    public void OnSelectEditEvent(String id) {
        if (isViewIdValidate(AppConstant.TRAVEL_CONTAINER)) {
            AddEditEventFragment fragment = new AddEditEventFragment();
            BundleString(fragment,
                    AppConstant.KEY_EVENT_ID,
                    id);
            fragment.setEditEventClickListener((id1, title, descriptions, date, time, imagePath) -> mUtilRealm
                    .updateEventByDayEventsId(id1, title, descriptions, date, time, imagePath));

            fragmentTransactionReplace(getSupportFragmentManager(),
                    AppConstant.TRAVEL_CONTAINER,
                    fragment);
        }
    }

    @Override
    public void onAddDayEvent() {
        if (isViewIdValidate(AppConstant.TRAVEL_CONTAINER)) {
            AddEditDayEventFragment fragment = new AddEditDayEventFragment();
            fragment.setAddDayEventListener((numberDay, descriptions) -> mUtilRealm
                    .addDayEventsByTravelId(mTravelsId ,numberDay, descriptions));
            fragmentTransactionReplace(getSupportFragmentManager(),
                    AppConstant.TRAVEL_CONTAINER,
                    fragment);
        }
    }

    @Override
    public void onSelectDayEvent(String id) {
        if (isViewIdValidate(AppConstant.TRAVEL_CONTAINER)) {
            mDayEventId = id;
            RecyclerViewEventFragment mRecyclerViewEventFragment = new RecyclerViewEventFragment();
            BundleString(mRecyclerViewEventFragment,
                    AppConstant.KEY_DAY_EVENT_ID,
                    id);
            fragmentTransactionReplace(getSupportFragmentManager(),
                    AppConstant.TRAVEL_CONTAINER,
                    mRecyclerViewEventFragment);
        }

    }

    private boolean isViewIdValidate(int ViewID) {
        if (findViewById(ViewID) != null)
            return true;
        else
            return false;
    }


}
