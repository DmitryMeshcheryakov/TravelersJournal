package by.insight.travelersjournal.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import by.insight.travelersjournal.R;
import by.insight.travelersjournal.model.Event;
import io.realm.RealmList;
import static by.insight.travelersjournal.tools.DateFormatter.convertDateToStringForAdapter;


public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventViewHolder> {

    private RealmList<Event> mEvents;
    private Context _context;

    private OnItemEventClickListener mOnItemEventClickListener;

    public interface OnItemEventClickListener {
        void onItemClick(String id);
    }

    public EventsAdapter(RealmList<Event> events, Context context) {
        this.mEvents = events;
        this._context = context;
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_event, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {

        holder.mTitleEvent.setText(mEvents
                .get(position)
                .getTitle());
        holder.mDescriptionsEvent.setText(mEvents
                .get(position)
                .getDescriptions());
        holder.mDateEvent.setText(convertDateToStringForAdapter(mEvents
                .get(position)
                .getDate()));
        holder.mTimeEvent.setText(mEvents
                .get(position)
                .getTime());

    }

    @Override
    public int getItemCount() {
        return mEvents.size();
    }

    public class EventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.title_event)
        TextView mTitleEvent;
        @BindView(R.id.description_event)
        TextView mDescriptionsEvent;
        @BindView(R.id.date_event)
        TextView mDateEvent;
        @BindView(R.id.time_event)
        TextView mTimeEvent;
        @BindView(R.id.image_event)
        ImageView mImageEvent;

        public EventViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View view) {
            Event event = mEvents.get(getAdapterPosition());
            mOnItemEventClickListener.onItemClick(event.getId());
        }
    }


    public void setOnItemEventClickListener(OnItemEventClickListener onItemClickListener) {
        this.mOnItemEventClickListener = onItemClickListener;
    }
}


