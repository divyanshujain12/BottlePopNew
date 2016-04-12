package com.elgroup.lenovo.bottlepop.Fragments;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import com.elgroup.lenovo.bottlepop.Adapters.CustomPagerAdapter;
import com.elgroup.lenovo.bottlepop.MainActivity;
import com.elgroup.lenovo.bottlepop.R;
import com.elgroup.lenovo.bottlepop.Utils.CallBackInterface;
import com.elgroup.lenovo.bottlepop.Utils.CommonFunctions;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.neopixl.pixlui.components.textview.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Lenovo on 12-01-2016.
 */
public class BookingFragment extends Fragment implements CallBackInterface, View.OnClickListener {
    private Context context;
    private TextView txtCatName, txtAddress, txtPrice;
    private String nameString, dateString, descriptionString, addressString, secondAddressString, priceString, imagesString;
    private ArrayList<String> imageArray;
    private ViewPager viewPager;
    private TextView txtDateTime, btnBookNow, txtDetail, txtLocation;
    private GoogleMap mapFragment;
    private SupportMapFragment map;
    private RatingBar ratingBar;
    View view = null;
    public static int pos = -1;
    private static BookingFragment bookingFragment = null;

    public static BookingFragment getInstance(int position) {
        pos = position;
        return new BookingFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
            view = inflater.inflate(R.layout.booking_fragment, container, false);
        } catch (InflateException e) {

        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        CommonFunctions.getInstance().setActionBarWithBackButton(getActivity());
        MainActivity.headerText.setText("BOOKING");

        InitViews();
    }


    private void InitViews() {
        context = getActivity();
       // String[] catArray = CommonFunctions.getInstance().getArray(MainActivity.ClickedCategoryTag, context);

        //--first position return name--//
        nameString = HomeFragment.homeFragmentModels.get(pos).getTitle();
        //--second position return date--//
        dateString = HomeFragment.homeFragmentModels.get(pos).getVenue();
        //--third position return description--//
        descriptionString = HomeFragment.homeFragmentModels.get(pos).getDescription();
        //--fourth position return address--//
        addressString = HomeFragment.homeFragmentModels.get(pos).getAddress();
        //--sixth position return price--//
        priceString = HomeFragment.homeFragmentModels.get(pos).getPrice();
        //---spliting image string to image array using comma seperation--//
        imageArray = HomeFragment.homeFragmentModels.get(pos).getSecondary_images();

        viewPager = (ViewPager) getView().findViewById(R.id.viewPager);
        ratingBar = (RatingBar) getView().findViewById(R.id.ratingBar);
        txtCatName = (TextView) getView().findViewById(R.id.txtCatName);
        txtAddress = (TextView) getView().findViewById(R.id.txtAddress);
        txtPrice = (TextView) getView().findViewById(R.id.txtPrice);
        txtDateTime = (TextView) getView().findViewById(R.id.txtDateTime);
        btnBookNow = (TextView) getView().findViewById(R.id.btnBookNow);
        txtDetail = (TextView) getView().findViewById(R.id.txtDetail);
        txtLocation = (TextView) getView().findViewById(R.id.txtLocation);


        ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapFragment)).getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mapFragment = googleMap;
                double latitude = Double.parseDouble(HomeFragment.homeFragmentModels.get(pos).getLatitude());
                double longitude = Double.parseDouble(HomeFragment.homeFragmentModels.get(pos).getLongitude());
                LatLng latLng = new LatLng(latitude, longitude);
                showMarkerOnMap(latLng);

               /* GetLatLngAsyncTask task = new GetLatLngAsyncTask(BookingFragment.this);
                task.execute(addressString);*/
            }
        });


        txtCatName.setText(nameString);
        txtPrice.setText("£ " + Html.fromHtml(priceString));
        txtAddress.setText(addressString);
        txtDateTime.setText(dateString);
        txtDetail.setText(descriptionString);
        txtLocation.setText(addressString);
        float rating = Float.valueOf(HomeFragment.homeFragmentModels.get(pos).getRating());
        ratingBar.setRating(rating);

        CustomPagerAdapter adapter = new CustomPagerAdapter(context, imageArray);
        viewPager.setAdapter(adapter);
       // viewPager.setOffscreenPageLimit(imageArray.size());
        viewPager.setCurrentItem(0);
        btnBookNow.setOnClickListener(this);
    }


    @Override
    public void onSuccess(String string) {

        try {
            JSONObject jsonObj = new JSONObject(string);
            JSONArray resultJsonArray = jsonObj.getJSONArray("results");
            JSONObject before_geometry_jsonObj = resultJsonArray
                    .getJSONObject(0);

            JSONObject geometry_jsonObj = before_geometry_jsonObj
                    .getJSONObject("geometry");

            JSONObject location_jsonObj = geometry_jsonObj
                    .getJSONObject("location");

            String lat_helper = location_jsonObj.getString("lat");
            double lat = Double.valueOf(lat_helper);


            String lng_helper = location_jsonObj.getString("lng");
            double lng = Double.valueOf(lng_helper);


            LatLng point = new LatLng(lat, lng);

            showMarkerOnMap(point);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }

    }

    private void showMarkerOnMap(LatLng latLng) {
        if (latLng != null) {
            mapFragment.addMarker(new MarkerOptions().position(latLng).title(addressString));
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 12);
            mapFragment.animateCamera(cameraUpdate);

        } else {
            mapFragment.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                @Override
                public void onMyLocationChange(Location location) {
                    mapFragment.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())));
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 12);
                    mapFragment.animateCamera(cameraUpdate);
                }
            });
        }
    }

    @Override
    public void onFailure(String string) {

    }

    @Override
    public void onClick(View v) {
        if (v == btnBookNow) {
            MainActivity.updateFragment(ReservationFragment.newInstance(nameString, priceString));

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

       /* Fragment fragment = (getChildFragmentManager().findFragmentById(R.id.mapFragment));
        if (fragment != null) {
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.remove(fragment);
            //ft.remove(this);

            ft.commit();
        }*/
    }

}
