package com.charlesdrews.hud.CardsData;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.charlesdrews.hud.R;

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
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NewsCardData.NewsItemData item = mData.get(position);
        //TODO - set thumbnail using url
        //TODO - make item clickable w/ link url
        holder.mHeadline.setText(item.getHeadline());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mThumbnail;
        TextView mHeadline;

        public ViewHolder(View itemView) {
            super(itemView);
            mThumbnail = (ImageView) itemView.findViewById(R.id.newsItemThumbnail);
            mHeadline = (TextView) itemView.findViewById(R.id.newsItemHeadline);
        }
    }
}
