package by.insight.travelersjournal.realm;

import by.insight.travelersjournal.model.Travel;
import io.realm.RealmResults;


public interface ITravelRepository extends IBaseRepository {
    interface OnAddTravelCallback {
        void onSuccess();
        void onError(String message);
    }

    interface OnGetAllTravelCallback {
        void onSuccess(RealmResults<Travel> travel);
        void onError(String message);
    }

    interface OnGetTravelByIdCallback {
        void onSuccess(Travel travel);
        void onError(String message);
    }

    interface OnDeleteTravelCallback {
        void onSuccess();
        void onError(String message);
    }

    void addTravel(Travel travel, OnAddTravelCallback callback);

    void deleteTravelById(String Id, OnDeleteTravelCallback callback);

    void deleteTravelByPosition(int position, OnDeleteTravelCallback callback);

    void getAllTravels(OnGetAllTravelCallback callback);

    void getTravelById(String id, OnGetTravelByIdCallback callback);

}
