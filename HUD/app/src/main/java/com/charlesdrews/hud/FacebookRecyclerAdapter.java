package com.charlesdrews.hud;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.charlesdrews.hud.CardsData.FacebookCardData;

import java.util.ArrayList;

/**
 * Bind facebook posts to the facebook recycler view in the facebook card
 * Created by charlie on 3/11/16.
 */
public class FacebookRecyclerAdapter extends RecyclerView.Adapter<FacebookRecyclerAdapter.ViewHolder> {
    private ArrayList<FacebookCardData.FacebookItem> mData;

    public FacebookRecyclerAdapter(ArrayList<FacebookCardData.FacebookItem> data) {
        mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.facebook_item, parent, false);
        return new ViewHolder(parent.getContext(), v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FacebookCardData.FacebookItem data = mData.get(position);

        holder.mAuthor.setText(data.getAuthor());
        holder.mStatusUpdate.setText(data.getStatusUpdate());

        holder.mContainer.setOnClickListener(null); //TODO
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Context mContext;
        RelativeLayout mContainer;
        ImageView mThumbnail;
        TextView mAuthor, mStatusUpdate;

        public ViewHolder(Context context, View itemView) {
            super(itemView);
            mContext = context;
            mContainer = (RelativeLayout) itemView.findViewById(R.id.facebookItemContainer);
            mThumbnail = (ImageView) itemView.findViewById(R.id.facebookItemThumbnail);
            mAuthor = (TextView) itemView.findViewById(R.id.facebookItemName);
            mStatusUpdate = (TextView) itemView.findViewById(R.id.facebookItemStatus);
        }
    }
}
