package by.insight.travelersjournal.presenters;


import by.insight.travelersjournal.model.ImageEvent;

public interface IImageEventPresenter extends IBasePresenter {
    void addImageEvent(ImageEvent imageEvent);

    void addImageEventByEventId(ImageEvent imageEvent, String eventId);

    void deleteImageEventByPosition(int position);

    void deleteImageEventById(String imageEventId);

    void getAllImagesEvents();

    void getAllImageEventByEventsId(String id);

    void getImageEventById(String id);

    void getEventById(String id);

}
