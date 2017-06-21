package by.insight.travelersjournal.realm;


import by.insight.travelersjournal.model.Event;
import by.insight.travelersjournal.model.Travel;
import io.realm.annotations.RealmModule;

@RealmModule (classes = {Event.class, Travel.class})
public class SimpleRealmModule {
}
