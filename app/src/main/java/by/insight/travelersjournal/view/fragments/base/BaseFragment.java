package by.insight.travelersjournal.view.fragments.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Toast;

import com.labo.kaji.fragmentanimations.MoveAnimation;
import com.mikepenz.itemanimators.ScaleUpAnimator;

import org.reactivestreams.Subscription;

import butterknife.Unbinder;
import by.insight.travelersjournal.database.UtilRealm;
import by.insight.travelersjournal.model.Event;
import by.insight.travelersjournal.model.ImageEvent;
import by.insight.travelersjournal.model.Travel;
import io.reactivex.CompletableOnSubscribe;
import io.realm.RealmList;
import io.realm.RealmResults;

public class BaseFragment extends Fragment {

    protected AddTravelsFragmentListener mAddTravelsFragmentListener;

    public interface AddTravelsFragmentListener {
        void onAddTravels();
    }

    protected EditTravelsFragmentListener mEditTravelsFragmentListener;

    public interface EditTravelsFragmentListener {
        void onEditTravels(String id);
    }

    protected SelectTravelsFragmentListener mSelectTravelsFragmentListener;

    public interface SelectTravelsFragmentListener {
        void onSelectTravel(String id);
    }

    protected OnAddTravelClickListener mAddTravelListener;

    public void setAddTravelListener(OnAddTravelClickListener listener) {
        this.mAddTravelListener = listener;
    }

    public interface OnAddTravelClickListener {
        void onAddTravelClickListener(String travelTitle, String imagePath);
    }

    protected OnEditTravelClickListener mOnEditTravelClickListener;

    public void setEditTravelListener(OnEditTravelClickListener listener) {
        this.mOnEditTravelClickListener = listener;
    }

    public interface OnEditTravelClickListener {
        void onEditTravelClickListener(String id, String travelTitle, String imagePath);
    }

    protected OnAddEventClickListener mAddEventListener;


    public interface OnAddEventClickListener {
        void onAddEventClickListener(String title, String descriptions, Long date, String time, RealmList<ImageEvent> imagePath);
    }

    public void setAddEventClickListener(OnAddEventClickListener listener) {
        this.mAddEventListener = listener;
    }

    protected OnEditEventClickListener mEditEventClickListener;

    public interface OnEditEventClickListener {
        void onEditEventClickListener(String id, String title, String descriptions, Long date, String time, RealmList<ImageEvent> imagePath);
    }

    public void setEditEventClickListener(OnEditEventClickListener listener) {
        this.mEditEventClickListener = listener;
    }

    protected OnSelectEditEventFragmentListener mOnSelectEditEventFragmentListener;

    public interface OnSelectEditEventFragmentListener {
        void OnSelectEditEvent(String id);
    }


    protected OnAddEventsFragmentListener mAddEventsFragmentListener;

    public interface OnAddEventsFragmentListener {
        void onAddEvent();
    }

    protected OnEditEventsFragmentListener mEditEventsFragmentListener;

    public interface OnEditEventsFragmentListener {
        void onEditEvent(String id);
    }

    protected OnSelectEventsFragmentListener mSelectEventsFragmentListener;

    public interface OnSelectEventsFragmentListener {
        void onSelectEvent(String id);
    }

    protected OnSelectDayEventsFragmentListener mSelectDayEventsFragmentListener;

    public interface OnSelectDayEventsFragmentListener {
        void onSelectDayEvent(String id);
    }

    protected AddDayEventFragmentListener mAddDayEventFragmentListener;

    public interface AddDayEventFragmentListener {
        void onAddDayEvent();
    }

    protected EditDayEventFragmentListener mEditDayEventFragmentListener;

    public interface EditDayEventFragmentListener {
        void onEditDayEvent(String id);
    }

    protected OnAddDayEventClickListener mAddDayEventListener;

    public void setAddDayEventListener(OnAddDayEventClickListener listener) {
        this.mAddDayEventListener = listener;
    }

    public interface OnAddDayEventClickListener {
        void onAddDayEventClickListener(String title, String numberDay);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mOnSelectEditEventFragmentListener = (OnSelectEditEventFragmentListener) context;
        mAddEventsFragmentListener = (OnAddEventsFragmentListener) context;
        mEditEventsFragmentListener = (OnEditEventsFragmentListener) context;
        mSelectEventsFragmentListener = (OnSelectEventsFragmentListener) context;
        mAddTravelsFragmentListener = (AddTravelsFragmentListener) context;
        mEditTravelsFragmentListener = (EditTravelsFragmentListener) context;
        mSelectTravelsFragmentListener = (SelectTravelsFragmentListener) context;
        mSelectDayEventsFragmentListener = (OnSelectDayEventsFragmentListener)context;
        mAddDayEventFragmentListener = (AddDayEventFragmentListener) context;

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOnSelectEditEventFragmentListener = null;
        mAddEventsFragmentListener = null;
        mEditEventsFragmentListener = null;
        mSelectEventsFragmentListener = null;
        mAddTravelsFragmentListener = null;
        mEditTravelsFragmentListener = null;
        mSelectTravelsFragmentListener = null;
        mSelectDayEventsFragmentListener = null;
        mEditDayEventFragmentListener = null;
        mAddDayEventFragmentListener = null;
        mAddDayEventListener = null;
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (enter) {
            return MoveAnimation.create(MoveAnimation.RIGHT, enter, 250);
        } else {
            return MoveAnimation.create(MoveAnimation.LEFT, enter, 250);
        }
    }
}
