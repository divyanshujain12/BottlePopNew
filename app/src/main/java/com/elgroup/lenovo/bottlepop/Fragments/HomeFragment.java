package com.elgroup.lenovo.bottlepop.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.elgroup.lenovo.bottlepop.Adapters.FeedAdapter;
import com.elgroup.lenovo.bottlepop.MainActivity;
import com.elgroup.lenovo.bottlepop.Models.HomeFragmentModel;
import com.elgroup.lenovo.bottlepop.R;
import com.elgroup.lenovo.bottlepop.Utils.CallBackInterface;
import com.elgroup.lenovo.bottlepop.Utils.GlobalAsyncTask;
import com.elgroup.lenovo.bottlepop.Utils.Keys;
import com.elgroup.lenovo.bottlepop.Utils.ParsingResponse;
import com.elgroup.lenovo.bottlepop.Utils.RecyclerItemClickListener;
import com.elgroup.lenovo.bottlepop.Utils.UrlList;

import org.apache.http.NameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Lenovo on 11-01-2016.
 */
public class HomeFragment extends Fragment implements CallBackInterface {
    private RecyclerView rvFeed;
    private Context instance;

    private FeedAdapter adapter;
    public static ArrayList<HomeFragmentModel> homeFragmentModels = new ArrayList<>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setHasOptionsMenu(true);
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container,
                false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((MainActivity) getActivity()).toolbar.setNavigationIcon(R.drawable.side_menu_icon);
        MainActivity.headerText.setText("BOTTLEPOP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getActivity().getResources().getColor(R.color.style_color_primary_dark));
        }
        MainActivity.headerText.setTextColor(getActivity().getResources().getColor(android.R.color.white));
        ((MainActivity) getActivity()).toolbar.setBackgroundColor(getActivity().getResources().getColor(R.color.style_color_primary_dark));
        ((MainActivity) getActivity()).toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).id_drawerlayout.openDrawer(Gravity.LEFT);
            }
        });
        InitViews();
    }

    private void InitViews() {
        instance = getActivity();


        rvFeed = (RecyclerView) getView().findViewById(R.id.rvFeed);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(instance) {
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return 300;
            }
        };
        rvFeed.setLayoutManager(linearLayoutManager);
        if (homeFragmentModels == null || homeFragmentModels.isEmpty()) {
            GlobalAsyncTask globalAsyncTask = new GlobalAsyncTask(getActivity(), UrlList.HOME_PAGE, this);
            globalAsyncTask.execute(new ArrayList<NameValuePair>());
        } else {
            adapter = new FeedAdapter(instance, homeFragmentModels);
            rvFeed.setAdapter(adapter);
        }
        rvFeed.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                MainActivity.ClickedCategoryTag = (String) view.findViewById(R.id.feedImage).getTag();
                MainActivity.updateFragment(BookingFragment.getInstance(position));
            }
        }));
    }

    @Override
    public void onSuccess(String string) {

        ParsingResponse parsingResponse = new ParsingResponse();
        try {
            homeFragmentModels = parsingResponse.parseJsonArrayWithJsonObject(new JSONObject(string).optJSONArray(Keys.DATA), HomeFragmentModel.class);

            //  addRequestNewClubArrayList();
            adapter = new FeedAdapter(instance, homeFragmentModels);
            rvFeed.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
            Snackbar.make(getView(), e.getMessage(), Snackbar.LENGTH_SHORT).show();
        }

    }

    private void addRequestNewClubArrayList() {
        HomeFragmentModel model = new HomeFragmentModel();
        ArrayList<String> imageArray = new ArrayList<>();
        Uri otherPath = Uri.parse("android.resource://com.segf4ult.test/drawable/app_icon");
        String image = otherPath.toString();
        imageArray.add(image);
        model.setAddress("");
        model.setDescription("BottlePop allows you to book a table at London's most famous nightclub of your choice. Our private concierge will call you personally to confirm the booking. BottlePop promises you a fun night out, so be ready for a wild night ahead. Keep the party rocking and the bottles popping!!");
        model.setFrom_date("");
        model.setId("");
        model.setLatitude("51.5072");
        model.setLongitude("0.1275");
        model.setPrice("500-1000");
        model.setRating("5");
        model.setSecondary_images(imageArray);
        model.setMain_image(image);
        model.setTitle("request a club");
        model.setTo_date("");
        model.setVenue("");
        homeFragmentModels.add(model);
    }

    @Override
    public void onFailure(String string) {

    }

}
