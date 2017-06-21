package by.insight.travelersjournal.view.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import by.insight.travelersjournal.R;
import by.insight.travelersjournal.model.Event;
import by.insight.travelersjournal.presenters.IEventPresenter;
import by.insight.travelersjournal.presenters.impl.EventPresenter;
import by.insight.travelersjournal.realm.RealmTable;
import by.insight.travelersjournal.view.activity.base.BaseActivity;
import by.insight.travelersjournal.view.adapter.EventsAdapter;
import by.insight.travelersjournal.view.fragments.AddEditEventFragment;
import by.insight.travelersjournal.view.fragments.RecyclerViewEventFragment;
import io.realm.RealmList;

public class EventsActivity extends BaseActivity implements RecyclerViewEventFragment.EventsFragmentListener {
    private IEventPresenter presenter;
    private String travelsId;
    private RecyclerViewEventFragment mRecyclerViewEventFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        presenter = new EventPresenter(this);
        travelsId = getIntent().getStringExtra(RealmTable.ID);
        mRecyclerViewEventFragment = new RecyclerViewEventFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString(RealmTable.ID, travelsId);
        mRecyclerViewEventFragment.setArguments(bundle);
        transaction.add(R.id.EventFragmentContainer, mRecyclerViewEventFragment);
        transaction.commit();
    }

    @Override
    public void onAddEvent() {
        if (findViewById(R.id.EventFragmentContainer) != null) {
            showAddEditEventFragment(R.id.EventFragmentContainer);
        }
    }

    private void showAddEditEventFragment(int ViewId) {
        final AddEditEventFragment fragment = new AddEditEventFragment();
        Bundle bundle = new Bundle();
        bundle.putString(RealmTable.ID, travelsId);
        fragment.setArguments(bundle);
        fragment.setListener(new AddEditEventFragment.OnAddEventClickListener() {
            @Override
            public void onAddEventClickListener(Event event) {
                presenter.addEventByTravelId(event, travelsId);
                presenter.getAllEventByTravelsId(travelsId);

            }
        });

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(ViewId, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }






}
