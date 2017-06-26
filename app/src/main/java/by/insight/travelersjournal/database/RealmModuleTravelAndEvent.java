package by.insight.travelersjournal.database;


import by.insight.travelersjournal.model.Event;
import by.insight.travelersjournal.model.ImageEvent;
import by.insight.travelersjournal.model.Travel;
import io.realm.annotations.RealmModule;

@RealmModule(classes = {Event.class, Travel.class, ImageEvent.class})
public class RealmModuleTravelAndEvent {
}
