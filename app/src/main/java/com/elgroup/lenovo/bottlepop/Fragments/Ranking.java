package com.elgroup.lenovo.bottlepop.Fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;


import com.elgroup.lenovo.bottlepop.MainActivity;
import com.elgroup.lenovo.bottlepop.Models.LeaderBoardMessageModel;
import com.elgroup.lenovo.bottlepop.Models.RankingModel;
import com.elgroup.lenovo.bottlepop.R;
import com.elgroup.lenovo.bottlepop.Utils.CallBackInterface;
import com.elgroup.lenovo.bottlepop.Utils.GlobalAsyncTask;
import com.elgroup.lenovo.bottlepop.Utils.ImageLoading;
import com.elgroup.lenovo.bottlepop.Utils.ParsingResponse;
import com.elgroup.lenovo.bottlepop.Utils.UrlList;
import com.neopixl.pixlui.components.textview.TextView;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Ranking extends Fragment implements CallBackInterface {
    private ListView listView;
    private int i = 0;
    private View listViewHeader = null;
    private RankingAdapter adapter;
    // private DisplayImageOptions options;
    private ImageLoading loading;

    private View view;
    private boolean isCalled = false;
    private View footerView;
    private ProgressBar footerProgressBar;
    private Context context;
    private ParsingResponse parsingResponse;
    private LeaderBoardMessageModel model;
    ArrayList<RankingModel> rankingModelList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ranking, container,
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
        MainActivity.headerText.setText("LEADERBOARD");

        InitViews();
    }

    private void InitViews() {

        context = getActivity();
        isCalled = false;

        parsingResponse = new ParsingResponse();
        listView = (ListView) getView().findViewById(R.id.listView);
        loading = new ImageLoading(context);
            /*
                Calling web service for getting description
            */
        GlobalAsyncTask globalAsyncTask = new GlobalAsyncTask(context, UrlList.BOARD_DESCRIPTION, new CallBackInterface() {
            @Override
            public void onSuccess(String string) {
                try {
                    JSONObject jsonObject = new JSONObject(string);
                    model = new LeaderBoardMessageModel();
                    model.setDescription(jsonObject.getJSONArray("description").getString(0));

                } catch (Exception e) {
                    e.printStackTrace();
                    Snackbar.make(getView(), "Some internet issues!", Snackbar.LENGTH_LONG).show();
                }

            /*
                   Calling web service for getting ranking of people
            */
                GlobalAsyncTask task = new GlobalAsyncTask(context, UrlList.RANKING_URl, Ranking.this);
                task.execute(new ArrayList<NameValuePair>());
            }

            @Override
            public void onFailure(String string) {
                GlobalAsyncTask task = new GlobalAsyncTask(context, UrlList.RANKING_URl, Ranking.this);
                task.execute(new ArrayList<NameValuePair>());
            }
        });

        globalAsyncTask.execute(new ArrayList<NameValuePair>());

       /* footerView = LayoutInflater.from(context).inflate(R.layout.footer_view, null);
        footerProgressBar = (ProgressBar) footerView.findViewById(R.id.progressBar);

        listView.addFooterView(footerView);*/
        adapter = new RankingAdapter(context, R.layout.ranking_list_item);
        listView.setAdapter(adapter);

    }

    @Override
    public void onSuccess(String response) {

        try {
            JSONArray array = new JSONArray(response);
            if (array.length() > 0) {
            /*
               parsing response got by backend.
             */

                rankingModelList = (parsingResponse.parseJsonArrayWithJsonObject(array, RankingModel.class));
                if (!isCalled) {

                    /*
                        creating header for listview
                     */
                    listView.addHeaderView(createListViewHeader());
                    adapter.addAll((rankingModelList));
                    adapter.notifyDataSetChanged();
                    isCalled = true;
                }
                // setValueinPBottomView();
            } else {
                adapter.notifyNoMoreItems();
            }
        } catch (Exception e) {
            e.printStackTrace();
            adapter.notifyNoMoreItems();
        }
    }

    @Override
    public void onFailure(String string) {

        adapter.notifyNoMoreItems();
    }


    private View createListViewHeader() {

        LayoutInflater inflater = LayoutInflater.from(context);
        listViewHeader = inflater.inflate(R.layout.ranking_header, null);

        TextView txtLeaderBoardDescription = (TextView) listViewHeader.findViewById(R.id.txtLeaderBoardDescription);

        CircleImageView imgFirstCircle = (CircleImageView) listViewHeader.findViewById(R.id.imgFirstCircle);
        CircleImageView imgSecondCircle = (CircleImageView) listViewHeader.findViewById(R.id.imgSecondCircle);
        CircleImageView imgThirdCircle = (CircleImageView) listViewHeader.findViewById(R.id.imgThirdCircle);

        TextView txtNameFirst = (TextView) listViewHeader.findViewById(R.id.txtNameFirst);
        TextView txtNameSecond = (TextView) listViewHeader.findViewById(R.id.txtNameSecond);
        TextView txtNameThird = (TextView) listViewHeader.findViewById(R.id.txtNameThird);

        TextView txtAmountFirst = (TextView) listViewHeader.findViewById(R.id.txtAmountFirst);
        TextView txtAmountSecond = (TextView) listViewHeader.findViewById(R.id.txtAmountSecond);
        TextView txtAmountThird = (TextView) listViewHeader.findViewById(R.id.txtAmountThird);

        loading.LoadImage(UrlList.IMAGE_URL + rankingModelList.get(1).getimage(), imgFirstCircle, null);
        loading.LoadImage(UrlList.IMAGE_URL + rankingModelList.get(0).getimage(), imgSecondCircle, null);
        loading.LoadImage(UrlList.IMAGE_URL + rankingModelList.get(2).getimage(), imgThirdCircle, null);

        txtLeaderBoardDescription.setText(model.getDescription());

        txtNameFirst.setText(rankingModelList.get(1).getName());
        txtNameSecond.setText(rankingModelList.get(0).getName());
        txtNameThird.setText(rankingModelList.get(2).getName());

        txtAmountFirst.setText("£ " + rankingModelList.get(1).getUsertotal());
        txtAmountSecond.setText("£ " + rankingModelList.get(0).getUsertotal());
        txtAmountThird.setText("£ " + rankingModelList.get(2).getUsertotal());

        for (int i = 0; i < 3; i++)
            rankingModelList.remove(0);
        //adapter.remove(adapter.getItem(0));
        // adapter.remove(adapter.getItem(1));
        // adapter.remove(adapter.getItem(2));

        return listViewHeader;
    }

    /*
            Ranking Adapter for showing listview Data
     */
    public class RankingAdapter extends ArrayAdapter<RankingModel> {
        boolean mHasMoreItems = true;

        private Context mContext = null;
        private LayoutInflater inflater;


        public RankingAdapter(Context context, int resource) {
            super(context, resource);
            mContext = context;
            inflater = LayoutInflater.from(mContext);

        }

        public void notifyNoMoreItems() {
          /*  mHasMoreItems = false;
            footerProgressBar.setVisibility(View.GONE);*/
        }


        @Override
        public int getCount() {
            return super.getCount();
        }

        @Override
        public RankingModel getItem(int position) {
            return super.getItem(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            /*if (position == getCount() - 1 && mHasMoreItems) {

                CallWebService.getInstance(mContext).callGetWevService(createUrl(), Ranking.this);

            }*/
            View view = convertView;
            ViewHolder holder = null;

            if (convertView == null) {
                view = inflater.inflate(R.layout.ranking_list_item, null);
                holder = new ViewHolder();
                holder.txtRankCircle = (TextView) view.findViewById(R.id.txtRankCircle);
                holder.imgProfile = (CircleImageView) view.findViewById(R.id.imgProfile);
                holder.txtUserName = (TextView) view.findViewById(R.id.txtUserName);
                holder.txtAmount = (TextView) view.findViewById(R.id.txtAmount);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            RankingModel model = getItem(position);
            if (model != null) {

                holder.txtRankCircle.setText(String.valueOf(position + 4));
                holder.txtUserName.setText(model.getName());
                holder.txtAmount.setText("£ " + model.getUsertotal());
                loading.LoadImage(UrlList.IMAGE_URL + model.getimage(), holder.imgProfile, null);
            }


            return view;
        }
    }

    class ViewHolder {
        TextView txtRankCircle, txtUserName, txtAddress, txtAmount;
        CircleImageView imgProfile;
    }
}
