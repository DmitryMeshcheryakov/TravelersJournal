package by.insight.travelersjournal.tools;


import android.support.design.widget.TextInputLayout;
import android.widget.TextView;
import static by.insight.travelersjournal.tools.DateFormatter.convertDateToString;


public class TextUtils {
    public static String textInputConvertString(TextInputLayout textInputLayout) {
        return textInputLayout.getEditText().getText().toString();
    }

    public static String textViewConvertString(TextView textView) {
        return textView.getText().toString();
    }

    public static String arrayDateKey(Long date, String key) {
        String textDate = convertDateToString(date);
        String[] arrayDate = textDate.split(" ");
        switch (key) {
            case "numberDayEvent": {
                return arrayDate[1];
            }
            case "monthAndYearEvent": {
                return arrayDate[2] + " " + arrayDate[3];
            }
            case "dayOfTheWeekEvent": {
                return arrayDate[0];
            }
            case "timeEvent": {
                return arrayDate[4];
            }
        }
        return null;
    }


}
