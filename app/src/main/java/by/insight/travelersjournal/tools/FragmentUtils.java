package by.insight.travelersjournal.tools;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import by.insight.travelersjournal.R;


public class FragmentUtils {

    public static Fragment initFragment(Fragment fragment) {
        return fragment;
    }

    public static void fragmentTransactionReplace(FragmentManager fragmentManager,
                                                  int ViewId,
                                                  Fragment fragment) {

        FragmentTransaction transaction = fragmentManager.beginTransaction();
      
        transaction.replace(ViewId, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public static void fragmentTransactionAdd(FragmentManager fragmentManager,
                                              int ViewId,
                                              Fragment fragment) {

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(ViewId, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
