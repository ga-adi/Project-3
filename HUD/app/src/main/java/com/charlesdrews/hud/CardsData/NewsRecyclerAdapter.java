package com.charlesdrews.hud.CardsData;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final NewsCardData.NewsItemData item = mData.get(position);

        holder.mHeadline.setText(item.getHeadline());
        Picasso.with(holder.mContext).load(item.getThumbnailUrl()).into(holder.mThumbnail);
        //TODO - make item clickable w/ link url
        holder.mContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri linkUrl = Uri.parse(item.getLinkUrl());
                holder.mContext.startActivity(new Intent(Intent.ACTION_VIEW, linkUrl));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Context mContext;
        RelativeLayout mContainer;
        ImageView mThumbnail;
        TextView mHeadline;

        public ViewHolder(Context context, View itemView) {
            super(itemView);
            mContext = context;
            mContainer = (RelativeLayout) itemView.findViewById(R.id.newsItemContainer);
            mThumbnail = (ImageView) itemView.findViewById(R.id.newsItemThumbnail);
            mHeadline = (TextView) itemView.findViewById(R.id.newsItemHeadline);
        }
    }
}
