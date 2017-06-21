package by.insight.travelersjournal.presenters.impl;


import by.insight.travelersjournal.model.Event;
import by.insight.travelersjournal.model.ImageEvent;
import by.insight.travelersjournal.model.Travel;
import by.insight.travelersjournal.presenters.IImageEventPresenter;
import by.insight.travelersjournal.realm.EventRepository;
import by.insight.travelersjournal.realm.IEventRepository;
import by.insight.travelersjournal.realm.IImageEventRepository;
import by.insight.travelersjournal.realm.ITravelRepository;
import by.insight.travelersjournal.realm.ImageEventRepository;
import by.insight.travelersjournal.view.activity.EventsActivity;
import by.insight.travelersjournal.view.fragments.AddEditEventFragment;
import io.realm.RealmList;
import io.realm.RealmResults;

public class ImageEventPresenter implements IImageEventPresenter {

    private EventsActivity mEventsActivity;
    private IEventRepository mIEventRepository;
    private IImageEventRepository mIImageEventRepository;
    private AddEditEventFragment mAddEditEventFragment;

    private IImageEventRepository.OnDeleteImageEventCallback mOnDeleteImageEventCallback;
    private IImageEventRepository.OnSaveImageEventCallback mOnSaveImageEventCallback;
    private IImageEventRepository.OnGetAllImagesEventsCallback mOnGetAllImagesEventsCallback;
    private IImageEventRepository.OnGetImageEventByIdCallback mOnGetImageEventByIdCallback;
    private IImageEventRepository.OnGetImagesEventsCallback mOnGetImagesEventsCallback;
    private IEventRepository.OnGetEventByIdCallback mOnGetEventByIdCallback;


    public ImageEventPresenter(AddEditEventFragment view)
    {
        this.mAddEditEventFragment = view;
        mIEventRepository = new EventRepository();
        mIImageEventRepository = new ImageEventRepository();
    }
    public ImageEventPresenter(EventsActivity view)
    {
        this.mEventsActivity = view;
        mIEventRepository = new EventRepository();
        mIImageEventRepository = new ImageEventRepository();
    }

    @Override
    public void addImageEvent(ImageEvent imageEvent) {
          mIImageEventRepository.addImageEvent(imageEvent, mOnSaveImageEventCallback);
    }

    @Override
    public void addImageEventByEventId(ImageEvent imageEvent, String eventId) {
        mIImageEventRepository.addImageEventByEventId(imageEvent, eventId, mOnSaveImageEventCallback);
    }

    @Override
    public void deleteImageEventByPosition(int position) {
        mIImageEventRepository.deleteImageEventByPosition(position, mOnDeleteImageEventCallback);
    }

    @Override
    public void deleteImageEventById(String imageEventId) {
        mIImageEventRepository.deleteImageEventById(imageEventId, mOnDeleteImageEventCallback);
    }

    @Override
    public void getAllImagesEvents() {
        mIImageEventRepository.getAllImagesEvents(mOnGetAllImagesEventsCallback);
    }

    @Override
    public void getAllImageEventByEventsId(String id) {
        mIImageEventRepository.getAllImagesEventsByEventId(id, mOnGetImagesEventsCallback);
    }

    @Override
    public void getImageEventById(String id) {
        mIImageEventRepository.getImageEventById(id, mOnGetImageEventByIdCallback);
    }

    @Override
    public void getEventById(String id) {
        mIEventRepository.getEventById(id, mOnGetEventByIdCallback);
    }

    @Override
    public void subscribeCallbacks() {
        mOnSaveImageEventCallback = new IImageEventRepository.OnSaveImageEventCallback() {
            @Override
            public void onSuccess() {
                mEventsActivity.showMessage("Added");
            }

            @Override
            public void onError(String message) {
                mEventsActivity.showMessage(message);
            }
        };
        mOnDeleteImageEventCallback = new IImageEventRepository.OnDeleteImageEventCallback() {
            @Override
            public void onSuccess() {
                mEventsActivity.showMessage("Deleted");
            }

            @Override
            public void onError(String message) {
                mEventsActivity.showMessage(message);
            }
        };
        mOnGetAllImagesEventsCallback = new IImageEventRepository.OnGetAllImagesEventsCallback() {
            @Override
            public void onSuccess(RealmResults<ImageEvent> imageEvents) {

            }

            @Override
            public void onError(String message) {
                mEventsActivity.showMessage(message);
            }
        };
        mOnGetImageEventByIdCallback = new IImageEventRepository.OnGetImageEventByIdCallback() {
            @Override
            public void onSuccess(ImageEvent event) {

            }

            @Override
            public void onError(String message) {

            }
        };
        mOnGetImagesEventsCallback = new IImageEventRepository.OnGetImagesEventsCallback() {
            @Override
            public void onSuccess(RealmList<ImageEvent> imageEvents) {
                mAddEditEventFragment.showImagesEvents(imageEvents);
            }

            @Override
            public void onError(String message) {
                mEventsActivity.showMessage(message);
            }
        };
        mOnGetEventByIdCallback = new IEventRepository.OnGetEventByIdCallback() {
            @Override
            public void onSuccess(Event event) {
            }

            @Override
            public void onError(String message) {
                mEventsActivity.showMessage(message);
            }
        };
    }

    @Override
    public void unSubscribeCallbacks() {
        mOnDeleteImageEventCallback = null;
        mOnSaveImageEventCallback = null;
        mOnGetAllImagesEventsCallback = null;
        mOnGetImageEventByIdCallback = null;

    }
}
