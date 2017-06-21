package by.insight.travelersjournal.tools;


import android.os.Bundle;
import android.support.v4.app.Fragment;

public class BundleUtils {
    public static void BundleString(Fragment fragment, String key, String value) {
        Bundle bundle = new Bundle();
        bundle.putString(key, value);
        fragment.setArguments(bundle);
    }
}
