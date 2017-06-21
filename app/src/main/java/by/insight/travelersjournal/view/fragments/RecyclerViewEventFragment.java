package by.insight.travelersjournal.view.fragments;

import android.content.Context;
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
import butterknife.Unbinder;
import by.insight.travelersjournal.R;
import by.insight.travelersjournal.model.Event;
import by.insight.travelersjournal.presenters.IEventPresenter;
import by.insight.travelersjournal.presenters.impl.EventPresenter;
import by.insight.travelersjournal.realm.RealmTable;
import by.insight.travelersjournal.view.adapter.EventsAdapter;
import by.insight.travelersjournal.view.fragments.base.BaseFragment;
import io.realm.RealmList;


public class RecyclerViewEventFragment extends BaseFragment {

    private static final String TAG = "RecyclerViewEventFragment";

    private IEventPresenter presenter;

    @BindView(R.id.addEventFAB)
    FloatingActionButton fbAdd;

    @BindView(R.id.recyclerviewEvent)
    RecyclerView rvEvents;

    private EventsAdapter adapter;

    private RealmList<Event> events;
    private String travelsId;
    private Unbinder unbinder;

    private EventsFragmentListener mEventsFragmentListener;

    // Метод обратного вызова , реализуемый MainActivity
    public interface EventsFragmentListener {
        // вызывается при нажатии кнопки добавления
        void onAddEvent();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_event_fragment, container, false);
        presenter = new EventPresenter(this);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            travelsId = bundle.getString(RealmTable.ID);
            Log.e(TAG, travelsId);
        }
        unbinder = ButterKnife.bind(this, view);
        initRecyclerListener();
        return view;
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
        unbinder.unbind();

    }


    @Override
    public void onStart() {
        super.onStart();
        presenter.subscribeCallbacks();
        presenter.getTravelById(travelsId);
        presenter.getAllEventByTravelsId(travelsId);
    }


    @Override
    public void onStop() {
        super.onStop();
        presenter.unSubscribeCallbacks();
    }

    @OnClick(R.id.addEventFAB)
    public void addEvent() {
        mEventsFragmentListener.onAddEvent();
    }

    private void initRecyclerListener() {
        rvEvents.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvEvents.setItemAnimator(new DefaultItemAnimator());

        ItemTouchHelper swipeToDismissTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override

            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                presenter.deleteEventById(events.get(viewHolder.getAdapterPosition()).getId());
                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        });
        swipeToDismissTouchHelper.attachToRecyclerView(rvEvents);
    }

    public void showEvents(RealmList<Event> events) {
        this.events = events;
        adapter = new EventsAdapter(events);
        rvEvents.setAdapter(adapter);
    }


}
