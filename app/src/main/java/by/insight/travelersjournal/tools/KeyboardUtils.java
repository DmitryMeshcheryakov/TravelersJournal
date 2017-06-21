package by.insight.travelersjournal.tools;


import android.app.Activity;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class KeyboardUtils {

    public static void hideTheKeyboard(Activity activity, View view) {
        ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    public static void hideOrShowFAB(String input, FloatingActionButton floatingActionButton) {
        if (input.trim().length() != 0) {
            floatingActionButton.show();
        } else {
            floatingActionButton.hide();
        }
    }

}
