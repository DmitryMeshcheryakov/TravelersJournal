package by.insight.travelersjournal.view.fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
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
import by.insight.travelersjournal.view.fragments.base.BaseFragment;

import static by.insight.travelersjournal.tools.KeyboardUtils.hideOrShowFAB;
import static by.insight.travelersjournal.tools.KeyboardUtils.hideTheKeyboard;
import static by.insight.travelersjournal.tools.SDUtils.getRealPathFromUri;
import static by.insight.travelersjournal.tools.TextUtils.textInputConvertString;
import static by.insight.travelersjournal.tools.UtilsValidate.isInfoValidate;


public class AddEditTravelFragment extends BaseFragment {

    @BindView(R.id.title_travel_TextInputLayout)
    TextInputLayout mTitleTravelTextInputLayout;

    @BindView(R.id.description_travel_TextInputLayout)
    TextInputLayout mDescriptionsTravelTextInputLayout;

    @BindView(R.id.save_travel_FAB)
    FloatingActionButton mSaveTravelFAB;

    @BindView(R.id.image_item_travel)
    ImageView mImageItemTravel;

    private RequestOptions mRequestOptions;

    private OnAddTravelClickListener mListener;
    private Unbinder mUnbinder;
    private String mPathImage;

    public void setListener(OnAddTravelClickListener listener) {
        this.mListener = listener;
    }

    public interface OnAddTravelClickListener {
        void onAddTravelClickListener(String travelTitle, String travelDescriptions, String imagePath);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_edit_travel, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        textWatcher();
        hideOrShowFAB(textInputConvertString(mTitleTravelTextInputLayout), mSaveTravelFAB);
        mRequestOptions = new RequestOptions().optionalCenterInside().fitCenter();
        return view;

    }


    @OnTextChanged(value = R.id.title_travel_EditText,
            callback = OnTextChanged.Callback.TEXT_CHANGED)
    public void textWatcher() {
        hideOrShowFAB(textInputConvertString(mTitleTravelTextInputLayout), mSaveTravelFAB);
    }

    @OnClick(R.id.save_travel_FAB)
    public void OnClickSave() {
        if (isInfoValidate(mTitleTravelTextInputLayout, mDescriptionsTravelTextInputLayout)) {

            hideTheKeyboard(getActivity(), getView());

            mListener.onAddTravelClickListener(
                    textInputConvertString(mTitleTravelTextInputLayout),
                    textInputConvertString(mDescriptionsTravelTextInputLayout),
                    mPathImage
            );

            getFragmentManager().popBackStack();

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @OnClick(R.id.add_image_travel)
    public void addImageTravel() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, AppConstant.REQEST_PHOTO_CODE);
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
}
