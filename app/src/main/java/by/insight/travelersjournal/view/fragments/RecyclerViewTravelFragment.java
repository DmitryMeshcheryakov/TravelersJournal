package by.insight.travelersjournal.view.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import by.insight.travelersjournal.R;
import by.insight.travelersjournal.app.SimpleRealmApp;
import by.insight.travelersjournal.model.Travel;
import by.insight.travelersjournal.presenters.ITravelPresenter;
import by.insight.travelersjournal.presenters.impl.TravelPresenter;
import by.insight.travelersjournal.realm.RealmTable;
import by.insight.travelersjournal.view.activity.EventsActivity;

import by.insight.travelersjournal.view.adapter.TravelsAdapter;
import by.insight.travelersjournal.view.fragments.base.BaseFragment;
import io.realm.Realm;
import io.realm.RealmResults;


public class RecyclerViewTravelFragment extends BaseFragment {



    @BindView(R.id.addTravelsFAB)
    FloatingActionButton fbAdd;

    @BindView(R.id.recyclerviewTravels)
   RecyclerView rvTravels;

    private TravelsAdapter adapter;
    private ITravelPresenter presenter;
    private RealmResults<Travel> travels;
    private Realm mRealm;

    private  TravelsFragmentListener mTravelsFragmentListener;

    public interface TravelsFragmentListener {
        void onAddTravels();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mTravelsFragmentListener = (TravelsFragmentListener) context;

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mTravelsFragmentListener = null;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.subscribeCallbacks();
        presenter.getAllTravels();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.content_travels_fragment, container, false);
        presenter = new TravelPresenter(this);
        mRealm = Realm.getDefaultInstance();
        ButterKnife.bind(this, view);
        initRecyclerListener();
        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.subscribeCallbacks();
        presenter.getAllTravels();
    }



    @Override
    public void onStop() {
        super.onStop();
        presenter.unSubscribeCallbacks();
    }


    private void initRecyclerListener() {
        rvTravels.setLayoutManager(new LinearLayoutManager(getContext()));
        rvTravels.setItemAnimator(new DefaultItemAnimator());

        ItemTouchHelper swipeToDismissTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                presenter.deleteTravelById(travels.get(viewHolder.getAdapterPosition()).getId());
                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        });
        swipeToDismissTouchHelper.attachToRecyclerView(rvTravels);
    }

    public void showTravels(RealmResults<Travel> travels) {
        this.travels = travels;
        adapter = new TravelsAdapter(travels);
        adapter.setOnItemClickListener(new TravelsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String id) {
                Intent intent = new Intent(getActivity(), EventsActivity.class);
                intent.putExtra(RealmTable.ID, id);
                startActivity(intent);
            }
        });
        rvTravels.setAdapter(adapter);
    }

    @OnClick(R.id.addTravelsFAB)
    protected void addTravel()
    {
        mTravelsFragmentListener.onAddTravels();
    }

}
