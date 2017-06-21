package by.insight.travelersjournal.presenters.impl;

import by.insight.travelersjournal.model.Event;
import by.insight.travelersjournal.model.Travel;
import by.insight.travelersjournal.presenters.IEventPresenter;
import by.insight.travelersjournal.realm.EventRepository;
import by.insight.travelersjournal.realm.IEventRepository;
import by.insight.travelersjournal.realm.ITravelRepository;
import by.insight.travelersjournal.realm.TravelRepository;
import by.insight.travelersjournal.view.activity.EventsActivity;
import by.insight.travelersjournal.view.fragments.RecyclerViewEventFragment;
import io.realm.RealmList;
import io.realm.RealmResults;



public class EventPresenter implements IEventPresenter {

    private EventsActivity mEventsActivity;
    private RecyclerViewEventFragment mRecyclerViewEventFragment;

    private IEventRepository.OnDeleteEventCallback onDeleteEventCallback;
    private IEventRepository.OnSaveEventCallback onSaveEventCallback;
    private IEventRepository.OnGetAllEventsCallback onGetAllEventsCallback;
    private IEventRepository.OnGetEventByIdCallback onGetEventByIdCallback;
    private IEventRepository.OnGetEventsCallback onGetEventsCallback;
    private ITravelRepository.OnGetTravelByIdCallback onGetTravelByIdCallback;


    private IEventRepository eventRepository;
    private ITravelRepository travelRepository;

    public EventPresenter(EventsActivity view) {
        this.mEventsActivity = view;
        eventRepository = new EventRepository();
        travelRepository = new TravelRepository();
    }

    public EventPresenter(RecyclerViewEventFragment view)
    {
        this.mRecyclerViewEventFragment = view;
        eventRepository = new EventRepository();
        travelRepository = new TravelRepository();
    }



    @Override
    public void addEvent(Event event) {
        eventRepository.addEvent(event, onSaveEventCallback);
    }

    public void addEventByTravelId(Event event, String travelId) {
        eventRepository.addEventByTravelId(event, travelId, onSaveEventCallback);
    }

    @Override
    public void deleteEventByPosition(int position) {
        eventRepository.deleteEventByPosition(position, onDeleteEventCallback);
    }

    @Override
    public void deleteEventById(String studentId) {
        eventRepository.deleteEventById(studentId, onDeleteEventCallback);
    }

    @Override
    public void getAllEvents() {
        eventRepository.getAllEvents(onGetAllEventsCallback);
    }

    @Override
    public void getAllEventByTravelsId(String id) {
        eventRepository.getAllEventsByTravelId(id, onGetEventsCallback);
    }

    @Override
    public void getEventById(String id) {
        eventRepository.getEventById(id, onGetEventByIdCallback);
    }

    @Override
    public void getTravelById(String id) {
        travelRepository.getTravelById(id, onGetTravelByIdCallback);
    }

    @Override
    public void subscribeCallbacks() {
        onSaveEventCallback = new IEventRepository.OnSaveEventCallback() {
            @Override
            public void onSuccess() {
                mEventsActivity.showMessage("Added");
            }

            @Override
            public void onError(String message) {
                mRecyclerViewEventFragment.showMessage(message);
            }
        };
        onDeleteEventCallback = new IEventRepository.OnDeleteEventCallback() {
            @Override
            public void onSuccess() {
                mRecyclerViewEventFragment.showMessage("Deleted");
            }

            @Override
            public void onError(String message) {
                mRecyclerViewEventFragment.showMessage(message);
            }
        };
        onGetAllEventsCallback = new IEventRepository.OnGetAllEventsCallback() {
            @Override
            public void onSuccess(RealmResults<Event> event) {

            }

            @Override
            public void onError(String message) {
                mRecyclerViewEventFragment.showMessage(message);
            }
        };
        onGetEventByIdCallback = new IEventRepository.OnGetEventByIdCallback() {
            @Override
            public void onSuccess(Event event) {

            }

            @Override
            public void onError(String message) {

            }
        };
        onGetEventsCallback = new IEventRepository.OnGetEventsCallback() {
            @Override
            public void onSuccess(RealmList<Event> events) {
                mRecyclerViewEventFragment.showEvents(events);
            }

            @Override
            public void onError(String message) {
                mRecyclerViewEventFragment.showMessage(message);
            }
        };
        onGetTravelByIdCallback = new ITravelRepository.OnGetTravelByIdCallback() {
            @Override
            public void onSuccess(Travel travel) {
            }

            @Override
            public void onError(String message) {
                mRecyclerViewEventFragment.showMessage(message);
            }
        };
    }

    @Override
    public void unSubscribeCallbacks() {
        onDeleteEventCallback = null;
        onSaveEventCallback = null;
        onGetAllEventsCallback = null;
        onGetEventByIdCallback = null;
    }
}
