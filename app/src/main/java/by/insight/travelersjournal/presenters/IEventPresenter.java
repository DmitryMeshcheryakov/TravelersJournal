package by.insight.travelersjournal.presenters;

import by.insight.travelersjournal.model.Event;

/**
 * Created by Dmitry on 21.05.2017.
 */

public interface IEventPresenter extends IBasePresenter {
    void addEvent(Event event);

    void addEventByTravelId(Event event, String travelId);

    void deleteEventByPosition(int position);

    void deleteEventById(String eventId);

    void getAllEvents();

    void getAllEventByTravelsId(String id);

    void getEventById(String id);

    void getTravelById(String id);
}
