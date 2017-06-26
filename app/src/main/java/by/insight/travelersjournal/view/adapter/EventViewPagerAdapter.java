package by.insight.travelersjournal.view.adapter;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import by.insight.travelersjournal.R;
import by.insight.travelersjournal.model.ImageEvent;
import io.realm.RealmList;


public class EventViewPagerAdapter extends PagerAdapter {

    private Context _context;
    private LayoutInflater mLayoutInflater;
    private RealmList<ImageEvent> mImagePath;
    private RequestOptions mRequestOptions;

    public EventViewPagerAdapter(Context context, RealmList<ImageEvent> imageEvents) {
        this._context = context;
        this.mLayoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mImagePath = imageEvents;
    }


    @Override
    public int getCount() {
        return this.mImagePath.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.image_view_pager, container, false);
        mRequestOptions = new RequestOptions().optionalCenterInside();
        ImageView imageView = (ImageView) itemView.findViewById(R.id.item_image_view_pager);

        Glide.with(_context)
                .load(mImagePath.get(position).getImagePath())
                .into(imageView);

        Log.i("IMAGE", mImagePath.get(position).getImagePath());

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }


}
