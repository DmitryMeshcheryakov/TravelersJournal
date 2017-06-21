package by.insight.travelersjournal.view.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mikepenz.itemanimators.ScaleUpAnimator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import by.insight.travelersjournal.AppConstant;
import by.insight.travelersjournal.R;
import by.insight.travelersjournal.model.Travel;
import by.insight.travelersjournal.presenters.ITravelPresenter;
import by.insight.travelersjournal.presenters.impl.TravelPresenter;
import by.insight.travelersjournal.view.activity.EventsActivity;
import by.insight.travelersjournal.view.adapter.TravelsAdapter;
import by.insight.travelersjournal.view.fragments.base.BaseFragment;
import io.realm.Realm;
import io.realm.RealmResults;


public class RecyclerViewTravelFragment extends BaseFragment {

    @BindView(R.id.add_travel_FAB)
    FloatingActionButton mAddTravelFAB;

    @BindView(R.id.recycler_view_travels)
    RecyclerView mRecyclerViewTravel;

    private TravelsAdapter mAdapter;
    private ITravelPresenter mPresenter;
    private RealmResults<Travel> mTravelRealmResults;
    private Realm mRealm;


    private TravelsFragmentListener mTravelsFragmentListener;

    public interface TravelsFragmentListener {
        void onAddTravels();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.subscribeCallbacks();
        mPresenter.getAllTravels();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_travels_fragment, container, false);
        ButterKnife.bind(this, view);
        init();
        initRecyclerListener();
        return view;

    }

    private void init()
    {
        mPresenter = new TravelPresenter(this);
        mRealm = Realm.getDefaultInstance();
    }

    private void initRecyclerListener() {
        mRecyclerViewTravel.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerViewTravel.setItemAnimator(new ScaleUpAnimator());
        ItemTouchHelper swipeToDismissTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                mPresenter.deleteTravelById(mTravelRealmResults.get(viewHolder.getAdapterPosition()).getId());
                mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        });
        swipeToDismissTouchHelper.attachToRecyclerView(mRecyclerViewTravel);
    }

    public void showTravels(RealmResults<Travel> travels) {
        this.mTravelRealmResults = travels;
        mAdapter = new TravelsAdapter(travels, getActivity());
        mRecyclerViewTravel.setAdapter(mAdapter);
        mAdapter.setOnItemTravelClickListener(new TravelsAdapter.OnItemTravelClickListener() {
            @Override
            public void onItemClick(String id) {
                Intent intent = new Intent(getActivity(), EventsActivity.class);
                intent.putExtra(AppConstant.KEY_BUNDLE, id);
                startActivity(intent);
            }
        });

    }

    @OnClick(R.id.add_travel_FAB)
    protected void addTravel() {
        mTravelsFragmentListener.onAddTravels();
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
    public void onStart() {
        super.onStart();
        mPresenter.subscribeCallbacks();
        mPresenter.getAllTravels();
    }


    @Override
    public void onStop() {
        super.onStop();
        mPresenter.unSubscribeCallbacks();
        mRealm = null;
    }

    @Override
    public void onDestroy() {
        System.runFinalization();
        System.exit(0);
        super.onDestroy();
    }
}
