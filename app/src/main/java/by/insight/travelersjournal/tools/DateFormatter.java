package by.insight.travelersjournal.tools;

import java.text.SimpleDateFormat;
import java.util.Date;


public class DateFormatter {
    private static String DATE_PATTERN = "dd MM yyyy";

    public static String convertDateToString(Date date){
        return new SimpleDateFormat(DATE_PATTERN).format(date);
    }
}
