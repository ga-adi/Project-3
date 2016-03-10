package com.charlesdrews.hud.CardsData;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.charlesdrews.hud.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by charlie on 3/10/16.
 */
public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.ViewHolder> {
    private ArrayList<NewsCardData.NewsItemData> mData;

    public NewsRecyclerAdapter(ArrayList<NewsCardData.NewsItemData> data) {
        mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        return new ViewHolder(parent.getContext(), v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NewsCardData.NewsItemData item = mData.get(position);

        holder.mHeadline.setText(item.getHeadline());
        //TODO - set thumbnail using url
        Picasso.with(holder.mContext).load(item.getThumbnailUrl()).into(holder.mThumbnail);
        //TODO - make item clickable w/ link url
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Context mContext;
        ImageView mThumbnail;
        TextView mHeadline;

        public ViewHolder(Context context, View itemView) {
            super(itemView);
            mContext = context;
            mThumbnail = (ImageView) itemView.findViewById(R.id.newsItemThumbnail);
            mHeadline = (TextView) itemView.findViewById(R.id.newsItemHeadline);
        }
    }
}
