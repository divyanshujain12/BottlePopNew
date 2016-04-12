package com.elgroup.lenovo.bottlepop.Adapters;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.elgroup.lenovo.bottlepop.R;

import java.util.ArrayList;

/**
 * Created by Lenovo on 28-03-2016.
 */
public class VipPagerAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    //ArrayList<String> images;

    FragmentManager fragmentManager;
    int[] imageResources = {R.drawable.vip_background,R.drawable.vip_background,R.drawable.vip_background,R.drawable.vip_background};

    public VipPagerAdapter(Context context) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
   //     this.images = eventsModels;

    }

    @Override
    public int getCount() {
        return imageResources.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.vip_pager_adapter, container, false);

        ImageView itemImg = (ImageView) itemView.findViewById(R.id.itemImg);
        itemImg.setImageResource(imageResources[position]);
        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }

}

