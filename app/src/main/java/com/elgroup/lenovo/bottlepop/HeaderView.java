package com.elgroup.lenovo.bottlepop;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.neopixl.pixlui.components.textview.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Lenovo on 11-04-2016.
 */
public class HeaderView extends LinearLayout {
    Context context;
    @Bind(R.id.backIV)
    ImageView backIV;
    @Bind(R.id.headerTV)
    TextView headerTV;
    final String xmlns = "http://schemas.android.com/apk/res-auto";
    String headerText = "";

    public HeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        headerText = attrs.getAttributeValue(xmlns, "headerText");
    }


    public void InitViews(Context context) {
        this.context = context;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.header_view, this);
        ButterKnife.bind(this, view);
        headerTV.setText(headerText);
    }

    public void setHeaderText(String headerText) {
        headerTV.setText(headerText);
    }

    @OnClick(R.id.backIV)
    public void back() {
        ((Activity) context).onBackPressed();
    }
}
