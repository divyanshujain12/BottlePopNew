package com.elgroup.lenovo.bottlepop.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.elgroup.lenovo.bottlepop.MainActivity;
import com.elgroup.lenovo.bottlepop.R;

/**
 * Created by Lenovo on 13-01-2016.
 */
public class ConciergeFragment extends Fragment implements View.OnClickListener {

    private ImageView imgCall, imgTxt, imgMail;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.concierge_fragment, container,
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
        MainActivity.headerText.setText("CONCIERGE");

        InitViews();
    }

    private void InitViews() {

        imgCall = (ImageView) getView().findViewById(R.id.imgCall);
        imgCall.setOnClickListener(this);

        imgTxt = (ImageView) getView().findViewById(R.id.imgTxt);
        imgTxt.setOnClickListener(this);

        imgMail = (ImageView) getView().findViewById(R.id.imgMail);
        imgMail.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == imgCall) {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "+447438227874"));
            startActivity(intent);
        } else if (v == imgTxt) {
            String number = "+447438227874";  // The number on which you want to send SMS
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", number, null)));
        } else if (v == imgMail) {
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"bottlepopreservation@gmail.com"});
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Contact");
            emailIntent.setType("message/rfc822");

            startActivity(Intent.createChooser(emailIntent, "Choose an Email client :"));

        }
    }
}
