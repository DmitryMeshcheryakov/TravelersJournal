package by.insight.travelersjournal.view.fragments.base;

import android.support.v4.app.Fragment;
import android.widget.Toast;

public class BaseFragment extends Fragment {
    public void showMessage(String message){
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }
}
