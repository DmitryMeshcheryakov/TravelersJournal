package by.insight.travelersjournal.view.activity;


import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import org.reactivestreams.Subscription;

import by.insight.travelersjournal.R;
import by.insight.travelersjournal.presenters.ITravelPresenter;
import by.insight.travelersjournal.presenters.impl.TravelPresenter;
import by.insight.travelersjournal.view.activity.base.BaseActivity;
import by.insight.travelersjournal.view.fragments.AddEditTravelFragment;
import by.insight.travelersjournal.view.fragments.RecyclerViewTravelFragment;

public class TravelsActivity extends BaseActivity implements
           RecyclerViewTravelFragment.TravelsFragmentListener{



    private ITravelPresenter presenter;
    private RecyclerViewTravelFragment mRecyclerViewTravelFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travels);
        presenter = new TravelPresenter(this);
        mRecyclerViewTravelFragment = new RecyclerViewTravelFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.TravelFragmentContainer, mRecyclerViewTravelFragment);
        transaction.addToBackStack("RecyclerViewTravelFragment");
        transaction.commit();

    }

    @Override
    public void onAddTravels() {
              if(findViewById(R.id.TravelFragmentContainer) != null)
              {
                  showAddEditTravelFragment(R.id.TravelFragmentContainer);
              }
    }

    private void showAddEditTravelFragment(int ViewID) {
        final AddEditTravelFragment fragment = new AddEditTravelFragment();
            fragment.setListener(new AddEditTravelFragment.OnAddTravelClickListener() {
                @Override
                public void onAddTravelClickListener(String travelTitle, String travelDescriptions) {
                    presenter.addTravel(travelTitle, travelDescriptions);
                }
            });
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(ViewID, fragment);
            transaction.addToBackStack("AddEditTravelFragment");
            transaction.commit();
    }


}
