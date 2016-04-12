package com.elgroup.lenovo.bottlepop.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.elgroup.lenovo.bottlepop.MainActivity;
import com.elgroup.lenovo.bottlepop.R;
import com.elgroup.lenovo.bottlepop.Utils.CommonFunctions;
import com.neopixl.pixlui.components.textview.TextView;

/**
 * Created by Lenovo on 18-01-2016.
 */
public class ConfirmationFragment extends Fragment {


    public static ConfirmationFragment newInstance() {

        ConfirmationFragment fragment = new ConfirmationFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setHasOptionsMenu(true);
        // setRetainInstance(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.confirmation_fragment, container,
                false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        CommonFunctions.getInstance().setActionBarWithBackButton(getActivity());
        MainActivity.headerText.setText("Reservation In Process!");

        InitViews();
    }

    private void InitViews() {

        ((TextView) getView().findViewById(R.id.txtMakeBooking)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });
    }
}
