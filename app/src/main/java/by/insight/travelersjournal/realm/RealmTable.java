package by.insight.travelersjournal.realm;



public interface RealmTable {
    String ID = "id";

    interface Travel
    {
        String TITLE = "title";
        String DESCRIPTION = "description";
    }

    interface Event
    {
        String TITLE = "title";
        String DESCRIPTION = "description";
//        String DATE = "date";
//        String TIME = "time";
    }
}
