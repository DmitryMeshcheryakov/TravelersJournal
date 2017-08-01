package by.insight.travelersjournal.database;


import java.util.UUID;

import javax.inject.Singleton;

import by.insight.travelersjournal.model.DayEvents;
import by.insight.travelersjournal.model.Event;
import by.insight.travelersjournal.model.ImageEvent;
import by.insight.travelersjournal.model.Travel;
import by.insight.travelersjournal.view.fragments.AddEditEventFragment;
import by.insight.travelersjournal.view.fragments.AddEditTravelFragment;
import by.insight.travelersjournal.view.fragments.RecyclerViewTravelFragment;
import dagger.Component;
import dagger.Provides;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class UtilRealm {

    public void addTravel(final String title, final String imagePath) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(transaction -> {
            Travel travel = transaction.createObject(Travel.class, UUID.randomUUID().toString());
            travel.setTitle(title);
            travel.setImagePath(imagePath);
            transaction.insertOrUpdate(travel);
        });
        realm.close();

    }


    public void updateTravel(final String id, final String title, final String imagePath) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(transaction -> {
            Travel travel = transaction.where(Travel.class).equalTo(RealmTable.ID, id).findFirst();
            travel.setTitle(title);
            travel.setImagePath(imagePath);
            transaction.insertOrUpdate(travel);
        });
        realm.close();

    }


    public void deleteTravelById(final String id) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(transaction -> {
            Travel t = transaction.where(Travel.class).equalTo(RealmTable.ID, id).findFirst();
            for (DayEvents dayEvents : t.getDayEvents()) {
                if(dayEvents.getEventRealmList().size() > 0) {
                    for (Event event : dayEvents.getEventRealmList()) {
                            event.getImageEvent().deleteAllFromRealm();
                    }
                    dayEvents.getEventRealmList().deleteAllFromRealm();
                }
            }
            t.getDayEvents().deleteAllFromRealm();
            t.deleteFromRealm();
        });
        realm.close();
    }


    public Travel getTravelById(String id) {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(Travel.class).equalTo(RealmTable.ID, id).findFirst();

    }


    public RealmResults<Travel> getAllTravels() {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<Travel> query = realm.where(Travel.class);
        return query.findAll();
    }

    public void addDayEventsByTravelId(final String id, final String numberDay, final String descriptions) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(transaction -> {
            DayEvents dayEvents = transaction.createObject(DayEvents.class, UUID.randomUUID().toString());
            dayEvents.setNumberDay(numberDay);
            dayEvents.setDescriptions(descriptions);
            Travel travel = transaction.where(Travel.class).equalTo(RealmTable.ID, id).findFirst();
            travel.getDayEvents().add(dayEvents);
            transaction.insertOrUpdate(travel);
        });
        realm.close();
    }

    public void updateDayEventsByTravelId(final String id, final String numberDay, final String descriptions) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(transaction -> {
            DayEvents dayEvents = transaction.where(DayEvents.class).equalTo(RealmTable.ID, id).findFirst();
            dayEvents.setNumberDay(numberDay);
            dayEvents.setDescriptions(descriptions);
            transaction.insertOrUpdate(dayEvents);
        });
        realm.close();

    }


    public void deleteDayEventById(final String id) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(transaction -> {
            DayEvents dayEvents = transaction.where(DayEvents.class).equalTo(RealmTable.ID, id).findFirst();
            for (Event event : dayEvents.getEventRealmList()) {
                event.getImageEvent().deleteAllFromRealm();
                event.deleteFromRealm();
            }
            dayEvents.deleteFromRealm();
        });
        realm.close();
    }


    public RealmList<DayEvents> getAllDayEventsByTravelId(String id) {
        Realm realm = Realm.getDefaultInstance();
        Travel travel = realm.where(Travel.class).equalTo(RealmTable.ID, id).findFirst();
        return travel.getDayEvents();
    }


    public DayEvents getDayEventById(String id) {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(DayEvents.class).equalTo(RealmTable.ID, id).findFirst();
    }


    public RealmResults<DayEvents> getAllDayEvents() {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<DayEvents> query = realm.where(DayEvents.class);
        return query.findAll();
    }

    public void addEventByDayEventsId(final String id, final String title, final String descriptions, final Long date, final String time, final RealmList<ImageEvent> imagePath) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(transaction -> {
            Event realmEvent = transaction.createObject(Event.class, UUID.randomUUID().toString());
            realmEvent.setTitle(title);
            realmEvent.setDescriptions(descriptions);
            realmEvent.setDate(date);
            realmEvent.setTime(time);
            RealmList<ImageEvent> imageEventRealmList = imagePath;
            if (!imageEventRealmList.isManaged()) {
                for (ImageEvent imageEventList : imageEventRealmList) {
                    ImageEvent imageEvent = transaction.createObject(ImageEvent.class, UUID.randomUUID().toString());
                    imageEvent.setImagePath(imageEventList.getImagePath());
                    realmEvent.getImageEvent().add(imageEvent);
                }
            }
            DayEvents dayEvents = transaction.where(DayEvents.class).equalTo(RealmTable.ID, id).findFirst();
            dayEvents.getEventRealmList().add(realmEvent);
            transaction.insertOrUpdate(dayEvents);
        });
        realm.close();

    }


    public void updateEventByDayEventsId(final String eventId, final String title, final String descriptions, final Long date, final String time, final RealmList<ImageEvent> imagePath) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(transaction -> {
            Event event = transaction.where(Event.class).equalTo(RealmTable.ID, eventId).findFirst();
            event.setTitle(title);
            event.setDescriptions(descriptions);
            event.setDate(date);
            event.setTime(time);
            transaction.insertOrUpdate(event);
        });
        realm.close();
    }


    public void deleteEventById(final String id) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(transaction -> {
            Event event = transaction.where(Event.class).equalTo(RealmTable.ID, id).findFirst();
            RealmList<ImageEvent> imageEvents = realm.where(Event.class).equalTo(RealmTable.ID, id).findFirst().getImageEvent();
            imageEvents.deleteAllFromRealm();
            event.deleteFromRealm();
        });
        realm.close();

    }


    public RealmList<Event> getAllEventsByDayEventsId(String id) {
        Realm realm = Realm.getDefaultInstance();
        DayEvents dayEvents = realm.where(DayEvents.class).equalTo(RealmTable.ID, id).findFirst();
        return dayEvents.getEventRealmList();
    }


    public Event getEventById(String id) {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(Event.class).equalTo(RealmTable.ID, id).findFirst();
    }


    public RealmResults<Event> getAllEvents() {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<Event> query = realm.where(Event.class);
        return query.findAll();
    }


    public void addImageEvent(final String imagePath) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(transaction -> {
            ImageEvent imageEvent = transaction.createObject(ImageEvent.class, UUID.randomUUID().toString());
            imageEvent.setImagePath(imagePath);
            transaction.insertOrUpdate(imageEvent);
        });
        realm.close();

    }


    public void updateImageEvent(final String id, final String imagePath) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(transaction -> {
            ImageEvent imageEvent = transaction.where(ImageEvent.class).equalTo(RealmTable.ID, id).findFirst();
            imageEvent.setImagePath(imagePath);
            transaction.insertOrUpdate(imageEvent);
        });
        realm.close();
    }


    public void deleteImageEventById(final String id) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(transaction -> {
            ImageEvent imageEvent = transaction.where(ImageEvent.class).equalTo(RealmTable.ID, id).findFirst();
            imageEvent.deleteFromRealm();
        });
        realm.close();

    }


    public RealmResults<ImageEvent> getAllImageEvents() {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<ImageEvent> query = realm.where(ImageEvent.class);
        return query.findAll();
    }


    public RealmList<ImageEvent> getAllImagesEventsByEventId(String id) {
        Realm realm = Realm.getDefaultInstance();

        Event event = realm.where(Event.class).equalTo(RealmTable.ID, id).findFirst();

        return event.getImageEvent();
    }
}



