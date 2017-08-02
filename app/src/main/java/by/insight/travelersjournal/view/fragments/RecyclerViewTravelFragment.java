package by.insight.travelersjournal.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.labo.kaji.fragmentanimations.CubeAnimation;
import com.labo.kaji.fragmentanimations.MoveAnimation;

import CustomFonts.CustomCollapsingToolbarLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import by.insight.travelersjournal.R;
import by.insight.travelersjournal.database.UtilRealm;
import by.insight.travelersjournal.model.Travel;
import by.insight.travelersjournal.view.adapter.TravelsAdapter;
import by.insight.travelersjournal.view.fragments.base.BaseFragment;
import io.realm.RealmResults;

import static by.insight.travelersjournal.tools.InitUtil.initRecyclerListenerTravel;
import static by.insight.travelersjournal.tools.InitUtil.initToolbar;


public class RecyclerViewTravelFragment extends BaseFragment {

    @BindView(R.id.rv_image_item_travel)
    ImageView mRvImageItemTravel;
    @BindView(R.id.recycler_view_travel_toolbar)
    Toolbar mRecyclerViewTravelToolbar;
    @BindView(R.id.recycler_view_travel_collapsing)
    CustomCollapsingToolbarLayout mRecyclerViewTravelCollapsing;
    @BindView(R.id.recycler_view_travel_appbar)
    AppBarLayout mRecyclerViewTravelAppbar;
    @BindView(R.id.recycler_view_travels)
    RecyclerView mRecyclerViewTravels;
    @BindView(R.id.add_travel_FAB)
    FloatingActionButton mAddTravelFAB;
    Unbinder unbinder;

    private TravelsAdapter mAdapter;

    private RealmResults<Travel> mTravelRealmResults;

    private UtilRealm mUtilRealm;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.travels_recycler_view_fragment, container, false);
        setHasOptionsMenu(true);
        unbinder = ButterKnife.bind(this, view);
        mUtilRealm = new UtilRealm();
        showTravels(mUtilRealm.getAllTravels());
        initToolbar(mRecyclerViewTravelToolbar, getActivity());
        initRecyclerListenerTravel(mRecyclerViewTravels, mAdapter, mUtilRealm, mTravelRealmResults, getContext());
        mRecyclerViewTravelAppbar.setExpanded(false);
        return view;

    }


    public void showTravels(RealmResults<Travel> travels) {
        this.mTravelRealmResults = travels;

        mAdapter = new TravelsAdapter(travels, getActivity());

        mRecyclerViewTravels.setAdapter(mAdapter);

        mAdapter.setEditTravelsFragmentListener(id -> mEditTravelsFragmentListener.onEditTravels(id));

        mAdapter.setOnItemTravelClickListener(id -> mSelectTravelsFragmentListener.onSelectTravel(id));


    }

    @OnClick(R.id.add_travel_FAB)
    protected void addTravel() {
        mAddTravelsFragmentListener.onAddTravels();
    }


    @Override
    public void onDestroy() {
        System.runFinalization();
        System.exit(0);
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
