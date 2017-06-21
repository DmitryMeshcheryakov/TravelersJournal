package by.insight.travelersjournal.realm;

import by.insight.travelersjournal.model.Event;
import io.realm.RealmList;
import io.realm.RealmResults;

public interface IEventRepository {
    interface OnSaveEventCallback {
        void onSuccess();
        void onError(String message);
    }

    interface OnDeleteEventCallback {
        void onSuccess();
        void onError(String message);
    }

    interface OnGetEventByIdCallback {
        void onSuccess(Event event);
        void onError(String message);
    }

    interface OnGetAllEventsCallback {
        void onSuccess(RealmResults<Event> events);
        void onError(String message);
    }

    interface OnGetEventsCallback{
        void onSuccess(RealmList<Event> events);
        void onError(String message);
    }

    void addEvent(Event event, OnSaveEventCallback callback);

    void addEventByTravelId(Event event, String universityId, OnSaveEventCallback callback);

    void deleteEventById(String id, OnDeleteEventCallback callback);

    void deleteEventByPosition(int position, OnDeleteEventCallback callback);

    void getAllEvents(OnGetAllEventsCallback callback);

    void getAllEventsByTravelId(String id, OnGetEventsCallback callback);

    void getEventById(String id, OnGetEventByIdCallback callback);
}
