package by.insight.travelersjournal.realm;


import java.util.UUID;


import by.insight.travelersjournal.model.Event;
import by.insight.travelersjournal.model.ImageEvent;
import by.insight.travelersjournal.model.Travel;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class ImageEventRepository implements IImageEventRepository {

    @Override
    public void addImageEvent(final ImageEvent imageEvent, OnSaveImageEventCallback callback) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                ImageEvent realmImageEvent = realm.createObject(ImageEvent.class, UUID.randomUUID().toString());
                realmImageEvent.setImagePath(imageEvent.getImagePath());
                realm.insertOrUpdate(realmImageEvent);
            }
        });
        if (callback != null)
            callback.onSuccess();
    }

    @Override
    public void addImageEventByEventId(final ImageEvent imageEvent, final String eventId, OnSaveImageEventCallback callback) {

        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                ImageEvent realmImageEvent = realm.createObject(ImageEvent.class, UUID.randomUUID().toString());
                realmImageEvent.setImagePath(imageEvent.getImagePath());
                Event event = realm.where(Event.class).equalTo(RealmTable.ID, eventId).findFirst();
                event.getImageEvent().add(realmImageEvent);
                realm.insertOrUpdate(realmImageEvent);
                realm.insertOrUpdate(event);
            }
        });
        if (callback != null)
            callback.onSuccess();
    }

    @Override
    public void deleteImageEventById(final String id, OnDeleteImageEventCallback callback) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                ImageEvent result = realm.where(ImageEvent.class).equalTo(RealmTable.ID, id).findFirst();
                result.deleteFromRealm();
            }
        });
        if (callback != null)
            callback.onSuccess();
    }

    @Override
    public void deleteImageEventByPosition(final int position, OnDeleteImageEventCallback callback) {

        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmQuery<ImageEvent> query = realm.where(ImageEvent.class);
                RealmResults<ImageEvent> results = query.findAll();
                results.deleteFromRealm(position);
            }
        });
        if (callback != null)
            callback.onSuccess();
    }

    @Override
    public void getAllImagesEvents(OnGetAllImagesEventsCallback callback) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<ImageEvent> results = realm.where(ImageEvent.class).findAll();

        if (callback != null)
            callback.onSuccess(results);

    }

    @Override
    public void getAllImagesEventsByEventId(String id, OnGetImagesEventsCallback callback) {
        Realm realm = Realm.getDefaultInstance();

        Event event = realm.where(Event.class).equalTo(RealmTable.ID, id).findFirst();
        RealmList<ImageEvent> imageEvents = event.getImageEvent();

        if (callback != null)
            callback.onSuccess(imageEvents);
    }

    @Override
    public void getImageEventById(String id, OnGetImageEventByIdCallback callback) {
        Realm realm = Realm.getDefaultInstance();
        ImageEvent imageEvent = realm.where(ImageEvent.class).equalTo(RealmTable.ID, id).findFirst();
        if (callback != null)
            callback.onSuccess(imageEvent);

    }
}
