package com.elgroup.lenovo.bottlepop.Adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.elgroup.lenovo.bottlepop.R;
import com.elgroup.lenovo.bottlepop.Utils.CommonFunctions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Lenovo on 13-01-2016.
 */
public class CustomPagerAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    ArrayList<String> imageNamesArray;
    View itemView = null;
    ImageView imageView = null;

    public CustomPagerAdapter(Context context, ArrayList<String> imageNamesArray) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.imageNamesArray = imageNamesArray;

    }

    @Override
    public int getCount() {
        return imageNamesArray.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);
        imageView = (ImageView) itemView.findViewById(R.id.pagerImageView);
        Picasso.with(mContext).load(imageNamesArray.get(position)).placeholder(R.drawable.loading_image).into(imageView);
        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

}
