package by.insight.travelersjournal.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import by.insight.travelersjournal.R;
import by.insight.travelersjournal.model.Event;
import by.insight.travelersjournal.tools.DateFormatter;
import io.realm.RealmList;


public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventViewHolder> {

    private RealmList<Event> mEvents;

    public EventsAdapter(RealmList<Event> events) {
        this.mEvents = events;
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_event, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {
        holder.tvTitle.setText(mEvents.get(position).getTitle());
        holder.tvDescriptions.setText(mEvents.get(position).getDescriptions());

    }

    @Override
    public int getItemCount() {
        return mEvents.size();
    }

    public class EventViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.timeEvent)
        TextView tvTitle;
        @BindView(R.id.descriptionEvent)
        TextView tvDescriptions;


        public EventViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
