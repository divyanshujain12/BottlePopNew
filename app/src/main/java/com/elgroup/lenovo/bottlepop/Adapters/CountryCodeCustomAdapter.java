package com.elgroup.lenovo.bottlepop.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.elgroup.lenovo.bottlepop.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 30-12-2015.
 */
public class CountryCodeCustomAdapter extends ArrayAdapter<String> {

    private Context context1;
    private List<String> data;
    public Resources res;
    LayoutInflater inflater;

    public CountryCodeCustomAdapter(Context context, List<String> objects) {
        super(context, R.layout.childrow, objects);

        context1 = context;
        data = objects;

        inflater = (LayoutInflater) context1
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getChildCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    // This funtion called for each row ( Called data.size() times )
    public View getCustomView(int position, View convertView, ViewGroup parent) {

        View row = inflater.inflate(R.layout.childrow, parent, false);

        TextView tvCategory = (TextView) row.findViewById(R.id.textView1);

        tvCategory.setText(data.get(position));

        return row;
    }

    public View getChildCustomView(int position, View convertView, ViewGroup parent) {

        View row = inflater.inflate(R.layout.grouprow, parent, false);


        ((TextView) row).setText(data.get(position));

        return row;
    }
}
