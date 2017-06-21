package by.insight.travelersjournal.view.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import butterknife.Unbinder;
import by.insight.travelersjournal.R;


public class AddEditTravelFragment extends Fragment {


    @BindView(R.id.titleTravelTextInputLayout)
    TextInputLayout titleTextInputLayout;

    @BindView(R.id.descriptionTravelTextInputLayout)
    TextInputLayout descriptionsTextInputLayout;

    @BindView(R.id.saveTravelFAB)
    FloatingActionButton saveTravelFAB;

    private OnAddTravelClickListener mListener;
    private Unbinder unbinder;


    public interface OnAddTravelClickListener {
        void onAddTravelClickListener(String travelTitle, String travelDescriptions);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_add_edit_travel, container, false);
        unbinder = ButterKnife.bind(this, view);
        titleTextInputLayout.getEditText().addTextChangedListener(nameChangedListener);
        updateSaveTravelButtonFAB();
        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void updateSaveTravelButtonFAB() {
        String input = titleTextInputLayout.getEditText().getText().toString();
        if (input.trim().length() != 0) {
            saveTravelFAB.show();
        } else {
            saveTravelFAB.hide();
        }
    }

    private final TextWatcher nameChangedListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            updateSaveTravelButtonFAB();
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    @OnClick(R.id.saveTravelFAB)
    public void OnClickSave() {
        if (isTravelInfoValid()) {
            ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(getView().getWindowToken(), 0);
            save();
            getFragmentManager().popBackStack();

        }
    }

    public void setListener(OnAddTravelClickListener listener) {
        this.mListener = listener;
    }

    private boolean isTravelInfoValid() {
        return !titleTextInputLayout.getEditText().getText().toString().isEmpty() &&
                !descriptionsTextInputLayout.getEditText().getText().toString().isEmpty();
    }

    private void save() {
        String title = titleTextInputLayout.getEditText().getText().toString();
        String descriptions = descriptionsTextInputLayout.getEditText().getText().toString();
        mListener.onAddTravelClickListener(title, descriptions);

    }




}
