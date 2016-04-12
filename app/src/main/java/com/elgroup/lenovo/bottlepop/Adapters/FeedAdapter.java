package com.elgroup.lenovo.bottlepop.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.elgroup.lenovo.bottlepop.Models.HomeFragmentModel;
import com.elgroup.lenovo.bottlepop.R;
import com.neopixl.pixlui.components.textview.TextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class FeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private int itemsCount = 0;
    private ArrayList<HomeFragmentModel> resourceIds;
    private String[] categoryArray = {"maddox", "project", "circque_le_soir", "club_41", "bonboniere", "libertine", "dstkrt", "boujis", "toy_room", "tonteria", "drama"};

    public FeedAdapter(Context context, ArrayList<HomeFragmentModel> resourceIds) {
        this.context = context;
        this.resourceIds = resourceIds;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.item_feed, parent, false);

        return new CellFeedViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final CellFeedViewHolder holder = (CellFeedViewHolder) viewHolder;
        bindDefaultFeedItem(position, holder);
    }

    private void bindDefaultFeedItem(int position, CellFeedViewHolder holder) {
   //     holder.feedImage.setTag(categoryArray[position]);
        Picasso.with(context).load(resourceIds.get(position).getMain_image()).placeholder(R.drawable.loading_image).into(holder.feedImage);
        holder.txtCatName.setText(resourceIds.get(position).getTitle());
        holder.txtAddress.setText(resourceIds.get(position).getAddress());
        holder.txtPrice.setText("£ "+ Html.fromHtml(resourceIds.get(position).getPrice()));
        float rating = Float.valueOf(resourceIds.get(position).getRating());
        holder.ratingBar.setRating(rating);


    }

    public void updateItems() {
        itemsCount = 10;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @Override
    public int getItemCount() {
        return resourceIds.size();
    }

    public static class CellFeedViewHolder extends RecyclerView.ViewHolder {
        private ImageView feedImage;
        private TextView txtCatName, txtAddress, txtPrice;
        private RatingBar ratingBar;

        public CellFeedViewHolder(View view) {
            super(view);

            feedImage = (ImageView) view.findViewById(R.id.feedImage);
            txtCatName = (TextView) view.findViewById(R.id.txtCatName);
            txtAddress = (TextView) view.findViewById(R.id.txtAddress);
            txtPrice = (TextView) view.findViewById(R.id.txtPrice);

            ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);

        }
    }


}
