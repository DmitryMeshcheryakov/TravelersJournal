package by.insight.travelersjournal;


import java.util.Arrays;
import java.util.List;

import by.insight.travelersjournal.database.RealmTable;

public class AppConstant {

    public static final String PHOTO_ALBUM = "travel";

    public static final List<String> FILE_EXTN = Arrays.asList("jpg", "jpeg",
            "png");

    public static final int REQEST_CAMERA = 10;

    public static final int REQEST_GALLERY = 11;

    public final static int REQEST_PHOTO_CODE = 1046;

    public static final String CHANGE_DATE = "changeDate";

    public static final String CHANGE_TIME = "changeTime";

    public static final int TRAVEL_CONTAINER = R.id.fragment_container;

    public static final String KEY_BUNDLE = RealmTable.ID;

    public static final String KEY_NUMBER_DAY = "numberDayEvent";

    public static final String KEY_MONTH_AND_YEAR = "monthAndYearEvent";

    public static final String KEY_DAY_OF_THE_WEEK = "dayOfTheWeekEvent";

    public static final String KEY_TIME = "timeEvent";

    public static final String KEY_EVENT_ID = "Event_ID";
    public static final String KEY_TRAVEL_ID = "Travel_ID";

    public static final  String IMAGES = "image/*";

}
