package by.insight.travelersjournal.view.fragments.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import org.reactivestreams.Subscription;

import io.reactivex.CompletableOnSubscribe;

public class BaseFragment extends Fragment {
    public void showMessage(String message){
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }


}
