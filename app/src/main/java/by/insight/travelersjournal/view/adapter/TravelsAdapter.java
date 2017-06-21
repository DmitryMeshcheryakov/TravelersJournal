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
import by.insight.travelersjournal.model.Travel;
import io.realm.RealmResults;


public class TravelsAdapter extends RecyclerView.Adapter<TravelsAdapter.TravelsViewHolder> {

    private OnItemTravelClickListener onItemClickListener;
    private RealmResults<Travel> mTravels;
    private Context _context;
    private RequestOptions mRequestOptions;

    public interface OnItemTravelClickListener {
        void onItemClick(String id);
    }

    public TravelsAdapter(RealmResults<Travel> travels, Context context) {

        this._context = context;
        this.mTravels = travels;

    }

    @Override
    public TravelsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_travels, parent, false);
        mRequestOptions = new RequestOptions().optionalCenterInside().fitCenter();
        return new TravelsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TravelsViewHolder holder, int position) {


        holder.mTitleTravelTextView.setText(mTravels
                .get(position)
                .getTitle());
        holder.mTravelDescriptionsTextView.setText(mTravels
                .get(position)
                .getDescriptions());
        Glide.with(_context)
                .load(mTravels.get(position).getImagePath())
                .apply(mRequestOptions)
                .into(holder.mImageTravel);

    }

    @Override
    public int getItemCount() {
        return mTravels.size();
    }

    public class TravelsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.title_travel_text_view)
        TextView mTitleTravelTextView;
        @BindView(R.id.description_travel_text_view)
        TextView mTravelDescriptionsTextView;
        @BindView(R.id.image_travel)
        ImageView mImageTravel;

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


    public void setOnItemTravelClickListener(OnItemTravelClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
