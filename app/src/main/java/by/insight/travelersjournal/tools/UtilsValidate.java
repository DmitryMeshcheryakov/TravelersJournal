package by.insight.travelersjournal.tools;


import android.support.design.widget.TextInputLayout;

public class UtilsValidate {

    public static boolean isInfoValidate(TextInputLayout title, TextInputLayout descriptions) {
        return !title.getEditText().getText().toString().isEmpty() &&
                !descriptions.getEditText().getText().toString().isEmpty();
    }

    public static boolean isDateValidate(Long date) {
        if (date != null)
            return true;
        else
            return false;
    }

}
