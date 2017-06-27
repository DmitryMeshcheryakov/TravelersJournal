package by.insight.travelersjournal.view.activity;

import android.os.Bundle;

import by.insight.travelersjournal.AppConstant;
import by.insight.travelersjournal.R;
import by.insight.travelersjournal.database.UtilRealm;
import by.insight.travelersjournal.model.ImageEvent;
import by.insight.travelersjournal.tools.FragmentUtils;
import by.insight.travelersjournal.view.activity.base.BaseActivity;
import by.insight.travelersjournal.view.fragments.AddEditEventFragment;
import by.insight.travelersjournal.view.fragments.AddEditTravelFragment;
import by.insight.travelersjournal.view.fragments.DetailEventFragment;
import by.insight.travelersjournal.view.fragments.RecyclerViewEventFragment;
import by.insight.travelersjournal.view.fragments.RecyclerViewTravelFragment;
import io.realm.RealmList;

import static by.insight.travelersjournal.tools.BundleUtils.BundleString;
import static by.insight.travelersjournal.tools.FragmentUtils.fragmentTransactionReplace;


public class MainActivity extends BaseActivity implements
        RecyclerViewTravelFragment.AddTravelsFragmentListener,
        RecyclerViewTravelFragment.EditTravelsFragmentListener,
        RecyclerViewEventFragment.OnAddEventsFragmentListener,
        RecyclerViewEventFragment.OnEditEventsFragmentListener,
        RecyclerViewTravelFragment.SelectTravelsFragmentListener,
        RecyclerViewEventFragment.OnSelectEventsFragmentListener,
DetailEventFragment.OnSelectEditEventFragmentListener{

    private RecyclerViewTravelFragment mRecyclerViewTravelFragment;
    private String mTravelsId;
    private UtilRealm mUtilRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        init();
        FragmentUtils.fragmentTransactionAdd(getSupportFragmentManager(),
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
            fragment.setAddEventClickListener(new AddEditEventFragment.OnAddEventClickListener() {
                @Override
                public void onAddEventClickListener(String title, String descriptions, Long date, String time, RealmList<ImageEvent> imagePath) {
                    mUtilRealm.addEventByTravelId(mTravelsId, title, descriptions, date, time, imagePath);
                }
            });

            fragmentTransactionReplace(getSupportFragmentManager(),
                    AppConstant.TRAVEL_CONTAINER,
                    fragment);
        }
    }

    @Override
    public void onAddTravels() {
        if (isViewIdValidate(AppConstant.TRAVEL_CONTAINER)) {
            AddEditTravelFragment fragment = new AddEditTravelFragment();
            fragment.setAddTravelListener(new AddEditTravelFragment.OnAddTravelClickListener() {
                @Override
                public void onAddTravelClickListener(String travelTitle, String travelImagePath) {
                    mUtilRealm.addTravel(travelTitle, travelImagePath);
                }
            });
            FragmentUtils.fragmentTransactionReplace(getSupportFragmentManager(),
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
            fragment.setEditEventClickListener(new AddEditEventFragment.OnEditEventClickListener() {
                @Override
                public void onEditEventClickListener(String id, String title, String descriptions, Long date, String time, RealmList<ImageEvent> imagePath) {
                    mUtilRealm.updateEventByTravelId(id, title, descriptions, date, time, imagePath);
                }
            });

            fragmentTransactionReplace(getSupportFragmentManager(),
                    AppConstant.TRAVEL_CONTAINER,
                    fragment);
        }
    }

    @Override
    public void onEditTravels(String id) {
        if (isViewIdValidate(AppConstant.TRAVEL_CONTAINER)) {
            AddEditTravelFragment fragment = new AddEditTravelFragment();
            fragment.setEditTravelListener(new AddEditTravelFragment.OnEditTravelClickListener() {
                @Override
                public void onEditTravelClickListener(String id, String travelTitle, String travelImagePath) {

                    mUtilRealm.updateTravel(id, travelTitle, travelImagePath);
                }
            });
            BundleString(fragment, AppConstant.KEY_TRAVEL_ID, id);
            FragmentUtils.fragmentTransactionReplace(getSupportFragmentManager(),
                    AppConstant.TRAVEL_CONTAINER,
                    fragment);
        }
    }

    @Override
    public void onSelectTravel(String id) {
        if (isViewIdValidate(AppConstant.TRAVEL_CONTAINER)) {
            mTravelsId = id;
            RecyclerViewEventFragment mRecyclerViewEventFragment = new RecyclerViewEventFragment();
            BundleString(mRecyclerViewEventFragment,
                    AppConstant.KEY_TRAVEL_ID,
                    id);
            FragmentUtils.fragmentTransactionReplace(getSupportFragmentManager(),
                    AppConstant.TRAVEL_CONTAINER,
                    mRecyclerViewEventFragment);
        }
    }

    @Override
    public void onSelectEvent(String id) {
        if (isViewIdValidate(AppConstant.TRAVEL_CONTAINER)) {
            DetailEventFragment fragment = new DetailEventFragment();
            BundleString(fragment,
                    AppConstant.KEY_EVENT_ID,
                    id);
            FragmentUtils.fragmentTransactionReplace(getSupportFragmentManager(),
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
            fragment.setEditEventClickListener(new AddEditEventFragment.OnEditEventClickListener() {
                @Override
                public void onEditEventClickListener(String id, String title, String descriptions, Long date, String time, RealmList<ImageEvent> imagePath) {
                    mUtilRealm.updateEventByTravelId(id, title, descriptions, date, time, imagePath);
                }
            });

            fragmentTransactionReplace(getSupportFragmentManager(),
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
