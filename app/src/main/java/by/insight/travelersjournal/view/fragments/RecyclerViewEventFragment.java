package by.insight.travelersjournal.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import by.insight.travelersjournal.model.Event;
import by.insight.travelersjournal.presenters.IEventPresenter;
import by.insight.travelersjournal.presenters.impl.EventPresenter;

import by.insight.travelersjournal.view.adapter.EventsAdapter;
import by.insight.travelersjournal.view.fragments.base.BaseFragment;
import io.realm.RealmList;


public class RecyclerViewEventFragment extends BaseFragment {

    @BindView(R.id.add_event_FAB)
    FloatingActionButton mAddEventFAB;

    @BindView(R.id.recycler_view_event)
    RecyclerView mRecyclerViewEvent;

    private EventsAdapter mAdapter;

    private RealmList<Event> mEventRealmList;
    private String mTravelsId;
    private Unbinder mUnbinder;

    private EventsFragmentListener mEventsFragmentListener;
    private IEventPresenter mPresenter;

    public interface EventsFragmentListener {
        void onAddEvent();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_event_fragment, container, false);
        mPresenter = new EventPresenter(this);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mTravelsId = bundle.getString(AppConstant.KEY_BUNDLE);
        }

        mUnbinder = ButterKnife.bind(this, view);
        initRecyclerListener();
        return view;
    }


    @OnClick(R.id.add_event_FAB)
    public void addEvent() {
        mEventsFragmentListener.onAddEvent();
    }

    private void initRecyclerListener() {
        mRecyclerViewEvent.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerViewEvent.setItemAnimator(new DefaultItemAnimator());

        ItemTouchHelper swipeToDismissTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override

            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                mPresenter.deleteEventById(mEventRealmList.get(viewHolder.getAdapterPosition()).getId());
                mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        });
        swipeToDismissTouchHelper.attachToRecyclerView(mRecyclerViewEvent);
    }

    public void showEvents(RealmList<Event> events) {
        this.mEventRealmList = events;
        mAdapter = new EventsAdapter(events, getContext());
        mRecyclerViewEvent.setAdapter(mAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mEventsFragmentListener = (EventsFragmentListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mEventsFragmentListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();

    }


    @Override
    public void onStart() {
        super.onStart();
        mPresenter.subscribeCallbacks();
        mPresenter.getTravelById(mTravelsId);
        mPresenter.getAllEventByTravelsId(mTravelsId);
    }


    @Override
    public void onStop() {
        super.onStop();
        mPresenter.unSubscribeCallbacks();
    }


}
