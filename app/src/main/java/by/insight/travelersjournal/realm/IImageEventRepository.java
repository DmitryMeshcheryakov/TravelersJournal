package by.insight.travelersjournal.realm;



import by.insight.travelersjournal.model.ImageEvent;
import io.realm.RealmList;
import io.realm.RealmResults;

public interface IImageEventRepository {

    interface OnSaveImageEventCallback {
        void onSuccess();

        void onError(String message);
    }

    interface OnDeleteImageEventCallback {
        void onSuccess();

        void onError(String message);
    }

    interface OnGetImageEventByIdCallback {
        void onSuccess(ImageEvent event);

        void onError(String message);
    }

    interface OnGetAllImagesEventsCallback {
        void onSuccess(RealmResults<ImageEvent> events);

        void onError(String message);
    }

    interface OnGetImagesEventsCallback {
        void onSuccess(RealmList<ImageEvent> events);

        void onError(String message);
    }

    void addImageEvent(ImageEvent imageEvent, IImageEventRepository.OnSaveImageEventCallback callback);

    void addImageEventByEventId(ImageEvent imageEvent, String eventId, IImageEventRepository.OnSaveImageEventCallback callback);

    void deleteImageEventById(String id, IImageEventRepository.OnDeleteImageEventCallback callback);

    void deleteImageEventByPosition(int position, IImageEventRepository.OnDeleteImageEventCallback callback);

    void getAllImagesEvents(IImageEventRepository.OnGetAllImagesEventsCallback callback);

    void getAllImagesEventsByEventId(String id, IImageEventRepository.OnGetImagesEventsCallback callback);

    void getImageEventById(String id, IImageEventRepository.OnGetImageEventByIdCallback callback);
}
