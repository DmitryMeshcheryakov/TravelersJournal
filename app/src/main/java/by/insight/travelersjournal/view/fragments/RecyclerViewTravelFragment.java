package by.insight.travelersjournal.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mikepenz.itemanimators.ScaleUpAnimator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import by.insight.travelersjournal.R;
import by.insight.travelersjournal.database.UtilRealm;
import by.insight.travelersjournal.model.Travel;

import by.insight.travelersjournal.view.adapter.TravelsAdapter;
import by.insight.travelersjournal.view.fragments.base.BaseFragment;
import io.realm.RealmResults;


public class RecyclerViewTravelFragment extends BaseFragment {

    @BindView(R.id.add_travel_FAB)
    FloatingActionButton mAddTravelFAB;

    @BindView(R.id.recycler_view_travels)
    RecyclerView mRecyclerViewTravel;

    @BindView(R.id.recycler_view_travel_toolbar)
    Toolbar mToolbar;

    private TravelsAdapter mAdapter;

    private RealmResults<Travel> mTravelRealmResults;

    private UtilRealm mUtilRealm;

    private AddTravelsFragmentListener mAddTravelsFragmentListener;

    public interface AddTravelsFragmentListener {
        void onAddTravels();
    }

    private EditTravelsFragmentListener mEditTravelsFragmentListener;

    public interface EditTravelsFragmentListener {
        void onEditTravels(String id);
    }

    private SelectTravelsFragmentListener mSelectTravelsFragmentListener;

    public interface SelectTravelsFragmentListener {
        void onSelectTravel(String id);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.travels_recycler_view_fragment, container, false);
        setHasOptionsMenu(true);
        ButterKnife.bind(this, view);
        init();
        initRecyclerListener();
        showTravels(mUtilRealm.getAllTravels());
        return view;

    }

    private void init() {
        mUtilRealm = new UtilRealm();
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
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
                mUtilRealm.deleteTravelById(mTravelRealmResults.get(viewHolder.getAdapterPosition()).getId());

                mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        });
        swipeToDismissTouchHelper.attachToRecyclerView(mRecyclerViewTravel);
    }

    public void showTravels(RealmResults<Travel> travels) {
        this.mTravelRealmResults = travels;

        mAdapter = new TravelsAdapter(travels, getActivity());

        mRecyclerViewTravel.setAdapter(mAdapter);

        mAdapter.setEditTravelsFragmentListener(new TravelsAdapter.EditTravelsFragmentListener() {
            @Override
            public void onEditTravels(String id) {
                mEditTravelsFragmentListener.onEditTravels(id);
            }
        });

        mAdapter.setOnItemTravelClickListener(new TravelsAdapter.OnItemTravelClickListener() {
            @Override
            public void onItemClick(String id) {
                mSelectTravelsFragmentListener.onSelectTravel(id);
            }
        });


    }

    @OnClick(R.id.add_travel_FAB)
    protected void addTravel() {
        mAddTravelsFragmentListener.onAddTravels();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mAddTravelsFragmentListener = (AddTravelsFragmentListener) context;
        mEditTravelsFragmentListener = (EditTravelsFragmentListener) context;
        mSelectTravelsFragmentListener = (SelectTravelsFragmentListener) context;

    }


    @Override
    public void onDetach() {
        super.onDetach();
        mAddTravelsFragmentListener = null;
        mEditTravelsFragmentListener = null;
        mSelectTravelsFragmentListener = null;

    }

    @Override
    public void onDestroy() {
        System.runFinalization();
        System.exit(0);
        super.onDestroy();
    }
}
