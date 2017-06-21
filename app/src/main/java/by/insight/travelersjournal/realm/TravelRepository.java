package by.insight.travelersjournal.realm;


import java.util.UUID;

import by.insight.travelersjournal.model.Travel;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;


public class TravelRepository implements ITravelRepository {

    @Override
    public void addTravel(final Travel travel, OnAddTravelCallback callBack) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Travel t = realm.createObject(Travel.class, UUID.randomUUID().toString());
                t.setTitle(travel.getTitle());
                t.setDescriptions(travel.getDescriptions());
                t.setImagePath(travel.getImagePath());
                realm.insertOrUpdate(t);

            }
        });

        if (callBack != null) {
            callBack.onSuccess();
        }
    }

    @Override
    public void deleteTravelById(final String Id, OnDeleteTravelCallback callback) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Travel t = realm.where(Travel.class).equalTo(RealmTable.ID, Id).findFirst();
                t.deleteFromRealm();

            }
        });
        if (callback != null)
            callback.onSuccess();

    }

    @Override
    public void deleteTravelByPosition(final int position, OnDeleteTravelCallback callback) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmQuery<Travel> query = realm.where(Travel.class);
                RealmResults<Travel> results = query.findAll();
                results.deleteFromRealm(position);
            }
        });
        if (callback != null)
            callback.onSuccess();
    }

    @Override
    public void getTravelById(final String id, OnGetTravelByIdCallback callback) {
        Realm realm = Realm.getDefaultInstance();
        Travel result = realm.where(Travel.class).equalTo(RealmTable.ID, id).findFirst();
        if (callback != null)
            callback.onSuccess(result);

    }

    @Override
    public void getAllTravels(OnGetAllTravelCallback callback) {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<Travel> query = realm.where(Travel.class);
        RealmResults<Travel> results = query.findAll();

        if (callback != null) {
            callback.onSuccess(results);
        }

    }


}
