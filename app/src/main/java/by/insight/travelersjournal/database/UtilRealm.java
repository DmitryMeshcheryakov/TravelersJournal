package by.insight.travelersjournal.database;


import java.util.UUID;

import by.insight.travelersjournal.model.Event;
import by.insight.travelersjournal.model.ImageEvent;
import by.insight.travelersjournal.model.Travel;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class UtilRealm {

    public void addTravel(final String title, final String imagePath) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Travel travel = realm.createObject(Travel.class, UUID.randomUUID().toString());
                travel.setTitle(title);
                travel.setImagePath(imagePath);
                realm.insertOrUpdate(travel);

            }
        });
        realm.close();
    }

    public void updateTravel(final String id, final String title, final String imagePath) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Travel travel = realm.where(Travel.class).equalTo(RealmTable.ID, id).findFirst();
                travel.setTitle(title);
                travel.setImagePath(imagePath);
                realm.insertOrUpdate(travel);
            }
        });
        realm.close();

    }

    public void deleteTravelById(final String Id) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Travel t = realm.where(Travel.class).equalTo(RealmTable.ID, Id).findFirst();
                RealmList<Event> events = getAllEventsByTravelId(Id);
                for (Event event : events)
                {
                    event.getImageEvent().deleteAllFromRealm();
                }
                events.deleteAllFromRealm();
                t.deleteFromRealm();
            }
        });
        realm.close();
    }

    public Travel getTravelById(String id) {
        Realm realm = Realm.getDefaultInstance();
        Travel result = realm.where(Travel.class).equalTo(RealmTable.ID, id).findFirst();
        return result;

    }

    public RealmList<Event> getAllEventsByTravelId(String id) {
        Realm realm = Realm.getDefaultInstance();

        Travel travel = realm.where(Travel.class).equalTo(RealmTable.ID, id).findFirst();
        RealmList<Event> events = travel.getEvents();
        return events;
    }

    public RealmResults<Travel> getAllTravels() {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<Travel> query = realm.where(Travel.class);
        RealmResults<Travel> results = query.findAll();
        return results;
    }

    public void deleteEventById(final String id) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Event result = realm.where(Event.class).equalTo(RealmTable.ID, id).findFirst();
                RealmList<ImageEvent> imageEvents = getAllImagesEventsByEventId(id);
                imageEvents.deleteAllFromRealm();
                result.deleteFromRealm();
            }
        });
        realm.close();
    }


    public void addEventByTravelId(final String id, final String title, final String descriptions, final Long date, final String time, final RealmList<ImageEvent> imagePath) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Event realmEvent = realm.createObject(Event.class, UUID.randomUUID().toString());
                realmEvent.setTitle(title);
                realmEvent.setDescriptions(descriptions);
                realmEvent.setDate(date);
                realmEvent.setTime(time);
                RealmList<ImageEvent> imageEventRealmList = imagePath;
                if (!imageEventRealmList.isManaged()) {
                    for (ImageEvent imageEventList : imageEventRealmList) {
                        ImageEvent imageEvent = realm.createObject(ImageEvent.class, UUID.randomUUID().toString());
                        imageEvent.setImagePath(imageEventList.getImagePath());
                        realmEvent.getImageEvent().add(imageEvent);
                    }
                }
                Travel travel = realm.where(Travel.class).equalTo(RealmTable.ID, id).findFirst();
                travel.getEvents().add(realmEvent);
                realm.insertOrUpdate(travel);
            }
        });
        realm.close();
    }

    public void updateEventByTravelId(final String eventId, final String title, final String descriptions, final Long date, final String time, final RealmList<ImageEvent> imagePath) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Event event = realm.where(Event.class).equalTo(RealmTable.ID, eventId).findFirst();
                event.setTitle(title);
                event.setDescriptions(descriptions);
                event.setDate(date);
                event.setTime(time);
                RealmList<ImageEvent> imageEventRealmList = imagePath;
                if (!imageEventRealmList.isManaged()) {
                    for (ImageEvent imageEventList : imageEventRealmList) {
                        ImageEvent imageEvent = realm.createObject(ImageEvent.class, UUID.randomUUID().toString());
                        imageEvent.setImagePath(imageEventList.getImagePath());
                        event.getImageEvent().add(imageEvent);
                    }
                }
                realm.insertOrUpdate(event);
            }
        });
        realm.close();
    }


    public Event getEventById(String id) {
        Realm realm = Realm.getDefaultInstance();
        Event event = realm.where(Event.class).equalTo(RealmTable.ID, id).findFirst();
        return event;
    }


    public RealmResults<ImageEvent> getAllImageEvents() {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<ImageEvent> query = realm.where(ImageEvent.class);
        RealmResults<ImageEvent> results = query.findAll();
        return results;
    }

    public RealmResults<Event> getAllEvents() {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<Event> query = realm.where(Event.class);
        RealmResults<Event> results = query.findAll();
        return results;
    }

    public RealmList<ImageEvent> getAllImagesEventsByEventId(String id) {
        Realm realm = Realm.getDefaultInstance();

        Event event = realm.where(Event.class).equalTo(RealmTable.ID, id).findFirst();
        RealmList<ImageEvent> imageEvents = event.getImageEvent();

       return imageEvents;
    }

}



