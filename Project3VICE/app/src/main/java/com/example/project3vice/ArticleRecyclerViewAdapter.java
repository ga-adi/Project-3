package com.example.project3vice;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project3vice.vice_classes.ViceArticle;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by David on 3/7/16.
 */
public class ArticleRecyclerViewAdapter extends RecyclerView.Adapter<ArticleRecyclerViewAdapter.ViewHolder> {

    ArrayList<ViceArticle> mListOfArticles;
    private Context mContext;

    public ArticleRecyclerViewAdapter(ArrayList<ViceArticle> arrayOfViceObjects) {
        mListOfArticles = arrayOfViceObjects;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView vCardThumbnail;
        public TextView vCardTitle;
        public TextView vCardPreview;
        public TextView vCardDate;

        public ViewHolder(View itemView) {
            super(itemView);
            vCardThumbnail = (ImageView)itemView.findViewById(R.id.cardThumbnail);
            vCardTitle = (TextView)itemView.findViewById(R.id.cardTitle);
            vCardPreview = (TextView)itemView.findViewById(R.id.cardPreview);
            vCardDate = (TextView)itemView.findViewById(R.id.cardDate);
            itemView.setOnClickListener(this);
        }

        //card click launches new activity showing article details
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(),ArticleActivity.class);
            intent.putExtra("ID",mListOfArticles.get(getLayoutPosition()).getId());
            v.getContext().startActivity(intent);
        }
    }

    @Override
    public ArticleRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.cardview_item,parent,false);
        mContext = parent.getContext();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ArticleRecyclerViewAdapter.ViewHolder holder, int position) {
        ViceArticle currentArticle = mListOfArticles.get(position);
        holder.vCardTitle.setText(currentArticle.getTitle());
        //holder.vCardPreview.setText(currentArticle.getPreview());
        //holder.vCardDate.setText(currentArticle.getPubDate());
        //holder.vCardThumbnail.setImageDrawable(LoadImage(currentArticle.getImage()));
        Picasso.with(mContext)
                .load(currentArticle.getImage())
                .fit().centerCrop()
                .into(holder.vCardThumbnail);
    }

    @Override
    public int getItemCount() {
        return mListOfArticles.size();
    }

    public static Drawable LoadImage(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }

}
