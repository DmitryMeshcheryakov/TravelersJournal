package by.insight.travelersjournal.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import by.insight.travelersjournal.R;
import by.insight.travelersjournal.model.Event;
import by.insight.travelersjournal.view.fragments.RecyclerViewEventFragment;
import io.realm.RealmList;

import static by.insight.travelersjournal.tools.DateFormatter.convertDateToStringForAdapter;


public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventViewHolder> {

    private RealmList<Event> mEvents;
    private Context _context;
    private RequestOptions mRequestOptions;

    private OnItemEventClickListener mOnItemEventClickListener;

    public interface OnItemEventClickListener {
        void onItemClick(String id);
    }

    private EditEventFragmentListener mEditEventFragmentListener;

    public interface EditEventFragmentListener {
        void onEditEvent(String id);
    }

    public void setEditEventFragmentListener(EditEventFragmentListener editTravelsFragmentListener) {
        this.mEditEventFragmentListener = editTravelsFragmentListener;
    }

    public EventsAdapter(RealmList<Event> events, Context context) {
        this.mEvents = events;
        this._context = context;
        notifyDataSetChanged();
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_event, parent, false);
        mRequestOptions = new RequestOptions().optionalCenterInside().optionalCenterInside();
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, final int position) {

        holder.mTitleEvent.setText(mEvents
                .get(position)
                .getTitle());
        holder.mTimeEvent.setText(mEvents
                .get(position)
                .getTime());
        holder.mEditEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEditEventFragmentListener.onEditEvent(mEvents.get(position).getId());
            }
        });

        if (mEvents.get(position).getImageEvent().size() > 0) {
            Glide.with(_context)
                    .load(mEvents.get(position).getImageEvent().get(1).getImagePath())
                    .apply(mRequestOptions)
                    .into(holder.mImageEvent);
        }


    }

    @Override
    public int getItemCount() {
        return mEvents.size();
    }

    public class EventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.title_event)
        TextView mTitleEvent;
        @BindView(R.id.time_event)
        TextView mTimeEvent;
        @BindView(R.id.image_event)
        ImageView mImageEvent;
        @BindView(R.id.edit_event)
        ImageView mEditEvent;

        public EventViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View view) {
            mOnItemEventClickListener.onItemClick(mEvents.get(getAdapterPosition()).getId());
        }
    }


    public void setOnItemEventClickListener(OnItemEventClickListener onItemClickListener) {
        this.mOnItemEventClickListener = onItemClickListener;
    }
}


