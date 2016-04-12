package com.elgroup.lenovo.bottlepop.Fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.elgroup.lenovo.bottlepop.MainActivity;
import com.elgroup.lenovo.bottlepop.R;
import com.neopixl.pixlui.components.textview.TextView;

/**
 * Created by Lenovo on 08-03-2016.
 */
public class HowIWorksFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.how_it_works, container,
                false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //CommonFunctions.getInstance().setActionBarWithBackButton(getActivity());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getActivity().getResources().getColor(R.color.style_color_primary_dark));
        }
        MainActivity.headerText.setTextColor(getActivity().getResources().getColor(android.R.color.white));
        ((MainActivity) getActivity()).toolbar.setBackgroundColor(getActivity().getResources().getColor(R.color.style_color_primary_dark));
        MainActivity.headerText.setText("HOW IT WORKS");

        InitViews();
    }

    private void InitViews() {

        //((TextView)getView().findViewById(R.id.text)).setText(Html.fromHtml(getString(R.string.how_it_works_policies)));
    }
}

