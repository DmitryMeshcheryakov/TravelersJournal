package by.insight.travelersjournal.view.fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import butterknife.Unbinder;
import by.insight.travelersjournal.AppConstant;
import by.insight.travelersjournal.R;
import by.insight.travelersjournal.database.UtilRealm;
import by.insight.travelersjournal.model.Travel;
import by.insight.travelersjournal.view.fragments.base.BaseFragment;

import static by.insight.travelersjournal.tools.KeyboardUtils.hideOrShowFAB;
import static by.insight.travelersjournal.tools.KeyboardUtils.hideTheKeyboard;
import static by.insight.travelersjournal.tools.SDUtils.getRealPathFromUri;
import static by.insight.travelersjournal.tools.TextUtils.textInputConvertString;
import static by.insight.travelersjournal.tools.UtilsValidate.isInfoValidate;


public class AddEditTravelFragment extends BaseFragment {

    @BindView(R.id.title_travel_TextInputLayout)
    TextInputLayout mTitleTravelTextInputLayout;

    @BindView(R.id.save_travel_FAB)
    FloatingActionButton mSaveTravelFAB;

    @BindView(R.id.add_edit_image_item_travel)
    ImageView mImageItemTravel;

    @BindView(R.id.add_edit_travel_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.add_edit_travel_collapsing)
    CollapsingToolbarLayout mCollapsingToolbarLayout;

    private RequestOptions mRequestOptions;

    private String travelId;
    private Unbinder mUnbinder;
    private String mPathImage;
    private UtilRealm mUtilRealm;


    private OnAddTravelClickListener mAddTravelListener;

    public void setAddTravelListener(OnAddTravelClickListener listener) {
        this.mAddTravelListener = listener;
    }

    public interface OnAddTravelClickListener {
        void onAddTravelClickListener(String travelTitle, String imagePath);
    }

    private OnEditTravelClickListener mOnEditTravelClickListener;

    public void setEditTravelListener(OnEditTravelClickListener listener) {
        this.mOnEditTravelClickListener = listener;
    }

    public interface OnEditTravelClickListener {
        void onEditTravelClickListener(String id, String travelTitle, String imagePath);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_edit_travel_fragment, container, false);
        setHasOptionsMenu(true);
        mUnbinder = ButterKnife.bind(this, view);
        initToolbar();
        textWatcher();
        hideOrShowFAB(textInputConvertString(mTitleTravelTextInputLayout), mSaveTravelFAB);
        mRequestOptions = new RequestOptions().optionalCenterInside();
        Bundle bundle = this.getArguments();
        mUtilRealm = new UtilRealm();
        if (bundle != null) {
            travelId = bundle.getString(AppConstant.KEY_TRAVEL_ID);
            showTravel(travelId);
        }
        return view;

    }

    private void initToolbar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }


    @OnTextChanged(value = R.id.title_travel_EditText,
            callback = OnTextChanged.Callback.TEXT_CHANGED)
    public void textWatcher() {
        hideOrShowFAB(textInputConvertString(mTitleTravelTextInputLayout), mSaveTravelFAB);
        if (textInputConvertString(mTitleTravelTextInputLayout) != null) {
            mCollapsingToolbarLayout.setTitle(textInputConvertString(mTitleTravelTextInputLayout));
        }
    }

    @OnClick(R.id.save_travel_FAB)
    public void OnClickSave() {
        if (isInfoValidate(mTitleTravelTextInputLayout)) {

            hideTheKeyboard(getActivity(), getView());
            if (mAddTravelListener != null) {
                mAddTravelListener.onAddTravelClickListener(
                        textInputConvertString(mTitleTravelTextInputLayout),
                        mPathImage);
            } else {
                mOnEditTravelClickListener.onEditTravelClickListener(travelId,
                        textInputConvertString(mTitleTravelTextInputLayout),
                        mPathImage);
            }

            getFragmentManager().popBackStack();

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null || resultCode != Activity.RESULT_OK) return;
        switch (requestCode) {
            case AppConstant.REQEST_PHOTO_CODE: {
                mPathImage = getRealPathFromUri(getContext(), data.getData());

                Glide.with(getContext())
                        .load(mPathImage)
                        .into(mImageItemTravel);
            }
        }
    }

    public void showTravel(String travelId) {
        Travel travel = mUtilRealm.getTravelById(travelId);
        mTitleTravelTextInputLayout.getEditText().setText(travel.getTitle());
        Glide.with(getContext())
                .load(travel.getImagePath())
                .into(mImageItemTravel);
        mPathImage = travel.getImagePath();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.add_edit_travel_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.add_image_travel: {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, AppConstant.REQEST_PHOTO_CODE);
                return true;
            }
            case android.R.id.home: {
                getFragmentManager().popBackStack();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
