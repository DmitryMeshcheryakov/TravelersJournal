package by.insight.travelersjournal.realm;

import android.util.Log;

import java.util.UUID;

import by.insight.travelersjournal.model.Event;
import by.insight.travelersjournal.model.Travel;
import by.insight.travelersjournal.app.SimpleRealmApp;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;


public class EventRepository implements IEventRepository {


    @Override
    public synchronized void addEvent(final Event event, OnSaveEventCallback callback) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Event realmEvent = realm.createObject(Event.class, UUID.randomUUID().toString());
                realmEvent.setTitle(event.getTitle());
                realmEvent.setDescriptions(event.getDescriptions());
                realm.insertOrUpdate(realmEvent);
            }
        });
        if (callback != null)
            callback.onSuccess();
    }

    @Override
    public synchronized void addEventByTravelId(final Event event, final String travelId, OnSaveEventCallback callback) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Event realmEvent = realm.createObject(Event.class, UUID.randomUUID().toString());
                realmEvent.setTitle(event.getTitle());
                realmEvent.setDescriptions(event.getDescriptions());
                Travel travel = realm.where(Travel.class).equalTo(RealmTable.ID, travelId).findFirst();
                travel.getEvents().add(realmEvent);
                realm.insertOrUpdate(realmEvent);
                realm.insertOrUpdate(travel);
            }
        });
        if (callback != null)
            callback.onSuccess();
    }

    @Override
    public synchronized void deleteEventById(final String id, OnDeleteEventCallback callback) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Event result = realm.where(Event.class).equalTo(RealmTable.ID, id).findFirst();
                result.deleteFromRealm();
            }
        });
        if (callback != null)
            callback.onSuccess();
    }

    @Override
    public synchronized void deleteEventByPosition(final int position, OnDeleteEventCallback callback) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmQuery<Event> query = realm.where(Event.class);
                RealmResults<Event> results = query.findAll();
                results.deleteFromRealm(position);
            }
        });
        if (callback != null)
            callback.onSuccess();
    }

    @Override
    public synchronized void getAllEvents(OnGetAllEventsCallback callback) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Event> results = realm.where(Event.class).findAll();

        if (callback != null)
            callback.onSuccess(results);
    }

    @Override
    public synchronized void getAllEventsByTravelId(String id, OnGetEventsCallback callback) {
        Realm realm = Realm.getDefaultInstance();

        Travel travel = realm.where(Travel.class).equalTo(RealmTable.ID, id).findFirst();
        RealmList<Event> events = travel.getEvents();

        if (callback != null)
            callback.onSuccess(events);

    }

    @Override
    public synchronized void getEventById(final String id, OnGetEventByIdCallback callback) {
        Realm realm = Realm.getDefaultInstance();
        Event event = realm.where(Event.class).equalTo(RealmTable.ID, id).findFirst();
        if (callback != null)
            callback.onSuccess(event);
    }
}
