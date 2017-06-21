package by.insight.travelersjournal.tools;


import java.text.SimpleDateFormat;

import java.util.Locale;

public class DateFormatter {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE dd MMMM yyyy HH:mm", Locale.getDefault());
    private static SimpleDateFormat dateFormatAdapter = new SimpleDateFormat("EEE, d MMM yyyy", Locale.getDefault());

    public static String convertDateToString(Long date) {
        return dateFormat.format(date);
    }

    public static String convertDateToStringForAdapter(Long date) {
        return dateFormatAdapter.format(date);
    }

    public static String convertTimeToString(int hourOfDay, int minute) {
        return addZeroForTime(hourOfDay) + " : " + addZeroForTime(minute);
    }

    public static String addZeroForTime(int time) {
        if (time < 10) {
            return String.valueOf("0" + time);
        }
        return String.valueOf(time);
    }


}
