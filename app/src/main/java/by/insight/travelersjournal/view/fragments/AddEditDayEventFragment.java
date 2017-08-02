package by.insight.travelersjournal.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import CustomFonts.CustomCollapsingToolbarLayout;
import CustomFonts.CustomTextEditInputLayout;
import CustomFonts.CustomTextInputLayout;
import biz.borealis.numberpicker.NumberPicker;
import biz.borealis.numberpicker.OnValueChangeListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import by.insight.travelersjournal.R;
import by.insight.travelersjournal.view.fragments.base.BaseFragment;

import static by.insight.travelersjournal.tools.InitUtil.initToolbar;
import static by.insight.travelersjournal.tools.KeyboardUtils.hideTheKeyboard;
import static by.insight.travelersjournal.tools.TextUtils.textInputConvertString;
import static by.insight.travelersjournal.tools.UtilsValidate.isInfoValidate;


public class AddEditDayEventFragment extends BaseFragment {


    @BindView(R.id.add_edit_day_event_ViewPager)
    ViewPager mAddEditDayEventViewPager;
    @BindView(R.id.add_edit_day_event_toolbar)
    Toolbar mAddEditDayEventToolbar;
    @BindView(R.id.add_edit_day_event_collapsing)
    CustomCollapsingToolbarLayout mAddEditDayEventCollapsing;
    @BindView(R.id.add_edit_day_event_appbar)
    AppBarLayout mAddEditDayEventAppbar;
    @BindView(R.id.add_edit_day_event_FAB)
    FloatingActionButton mAddEditDayEventFAB;
    @BindView(R.id.add_edit_description_day_event_EditText)
    CustomTextEditInputLayout mAddEditDescriptionDayEventEditText;
    @BindView(R.id.add_edit_description_day_event_TextInputLayout)
    CustomTextInputLayout mAddEditDescriptionDayEventTextInputLayout;
//    @BindView(R.id.add_edit_numaber_day_event_EditText)
//    CustomTextEditInputLayout mAddEditNumaberDayEventEditText;
//    @BindView(R.id.add_edit_number_day_event_TextInputLayout)
//    CustomTextInputLayout mAddEditNumberDayEventTextInputLayout;
    @BindView(R.id.np_day_event)
    NumberPicker mNumberPicker;
    private int numberDay;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_edit_day_event_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        initToolbar(mAddEditDayEventToolbar, getActivity());
        mAddEditDayEventAppbar.setExpanded(false);
        mNumberPicker.setOnValueChangeListener(newValue -> numberDay = newValue);
        return view;
    }

    @OnClick(R.id.add_edit_day_event_FAB)
    public void OnClickSave() {
        if (numberDay > 1) {

            hideTheKeyboard(getActivity(), getView());
            if (mAddDayEventListener != null) {

                mAddDayEventListener.onAddDayEventClickListener(
                        String.valueOf(numberDay),
                        textInputConvertString(mAddEditDescriptionDayEventTextInputLayout));
            } else {
//                mOnEditTravelClickListener.onEditTravelClickListener(travelId,
//                        textInputConvertString(mTitleTravelTextInputLayout),
//                        mPathImage);
            }

            getFragmentManager().popBackStack();

        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
