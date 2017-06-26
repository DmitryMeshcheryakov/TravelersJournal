package by.insight.travelersjournal.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import by.insight.travelersjournal.AppConstant;
import by.insight.travelersjournal.R;
import by.insight.travelersjournal.database.UtilRealm;
import by.insight.travelersjournal.model.Event;


import by.insight.travelersjournal.view.adapter.EventsAdapter;
import by.insight.travelersjournal.view.fragments.base.BaseFragment;
import io.realm.RealmList;


public class RecyclerViewEventFragment extends BaseFragment {

    @BindView(R.id.add_event_FAB)
    FloatingActionButton mAddEventFAB;

    @BindView(R.id.recycler_view_event)
    RecyclerView mRecyclerViewEvent;

    @BindView(R.id.add_edit_event_rv_toolbar)
    Toolbar mToolbar;

    private EventsAdapter mAdapter;

    private RealmList<Event> mEventRealmList;
    private String mTravelsId;
    private Unbinder mUnbinder;

    private OnAddEventsFragmentListener mAddEventsFragmentListener;

    public interface OnAddEventsFragmentListener {
        void onAddEvent();
    }

    private OnEditEventsFragmentListener mEditEventsFragmentListener;

    public interface OnEditEventsFragmentListener {
        void onEditEvent(String id);
    }

    private UtilRealm mUtilRealm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_recycler_view_fragment, container, false);
        setHasOptionsMenu(true);
        mUnbinder = ButterKnife.bind(this, view);
        mUtilRealm = new UtilRealm();
        initToolbar();
        initRecyclerListener();
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mTravelsId = bundle.getString(AppConstant.KEY_TRAVEL_ID);
            showEvents(mUtilRealm.getAllEventsByTravelId(mTravelsId));
        }

        return view;
    }

    private void initToolbar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void initRecyclerListener() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerViewEvent.setLayoutManager(layoutManager);
        mRecyclerViewEvent.setItemAnimator(new DefaultItemAnimator());
        ItemTouchHelper swipeToDismissTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override

            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                mUtilRealm.deleteEventById(mEventRealmList.get(viewHolder.getAdapterPosition()).getId());
                mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        });
        swipeToDismissTouchHelper.attachToRecyclerView(mRecyclerViewEvent);
    }

    public void showEvents(RealmList<Event> events) {
        this.mEventRealmList = events;

        mAdapter = new EventsAdapter(events, getContext());

        mRecyclerViewEvent.setAdapter(mAdapter);

        mAdapter.setEditEventFragmentListener(new EventsAdapter.EditEventFragmentListener() {
            @Override
            public void onEditEvent(String id) {
                mEditEventsFragmentListener.onEditEvent(id);
            }
        });

        mAdapter.setOnItemEventClickListener(new EventsAdapter.OnItemEventClickListener() {
            @Override
            public void onItemClick(String id) {

            }
        });
    }

    @OnClick(R.id.add_event_FAB)
    public void addEvent() {
        mAddEventsFragmentListener.onAddEvent();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mAddEventsFragmentListener = (OnAddEventsFragmentListener) context;
        mEditEventsFragmentListener = (OnEditEventsFragmentListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mAddEventsFragmentListener = null;
        mEditEventsFragmentListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();

    }

}
