package by.insight.travelersjournal.database;


public interface RealmTable {
    String ID = "id";

    interface Travel {
        String TITLE = "title";
        String DESCRIPTIONS = "descriptions";
        String IMAGE_PATH = "image_path";
    }

    interface DayEvent {
        String NUMBER_DAY = "number_day";
        String DESCRIPTIONS = "descriptions";
    }

    interface Event {
        String TITLE = "title";
        String DESCRIPTIONS = "descriptions";
        String DATE = "date";
        String TIME = "time";
    }

    interface ImageEvent {
        String IMAGE_PATH = "image_path";
    }
}
