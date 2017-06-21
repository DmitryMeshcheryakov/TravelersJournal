package by.insight.travelersjournal.tools;


import by.insight.travelersjournal.model.Event;

public class ItemUtils {
    public static Event saveEvent(String title,
                                  String descriptions,
                                  Long date,
                                  String time) {
        Event event = new Event();
        event.setTitle(title);
        event.setDescriptions(descriptions);
        event.setDate(date);
        event.setTime(time);
        return event;
    }

}
