package by.insight.travelersjournal.view.adapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import by.insight.travelersjournal.R;
import by.insight.travelersjournal.model.Travel;
import io.realm.RealmResults;



public class TravelsAdapter extends RecyclerView.Adapter<TravelsAdapter.TravelsViewHolder>  {
    private OnItemClickListener onItemClickListener;
    private RealmResults<Travel> mTravels;


    public TravelsAdapter(RealmResults<Travel> travels) {
        this.mTravels = travels;

    }

    @Override
    public TravelsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_travels, parent, false);
        return new TravelsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TravelsViewHolder holder, int position) {




        holder.tvTravelTitle.setText(mTravels.get(position).getTitle());
        holder.tvTravelDescriptions.setText(mTravels.get(position).getDescriptions());
    }

    @Override
    public int getItemCount() {
        return mTravels.size();
    }

    public class TravelsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.titleTravelsTextView)
        TextView tvTravelTitle;
        @BindView(R.id.descriptionTravelsTextView)
         TextView tvTravelDescriptions;

        public TravelsViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);

        }

        @Override
        public void onClick(View v) {
            Travel travel = mTravels.get(getAdapterPosition());
            onItemClickListener.onItemClick(travel.getId());
        }
    }

    public interface OnItemClickListener{
        void onItemClick(String id);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
