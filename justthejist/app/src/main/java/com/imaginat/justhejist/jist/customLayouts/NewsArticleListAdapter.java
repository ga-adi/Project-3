package com.imaginat.justhejist.jist.customLayouts;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.imaginat.justhejist.jist.Activities.StoryActivity;
import com.imaginat.justhejist.jist.R;
import com.imaginat.justhejist.jist.api.nyt.MediaOptions;
import com.imaginat.justhejist.jist.models.Medium;
import com.imaginat.justhejist.jist.models.NewsStory;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nat on 3/6/16.
 */
public class NewsArticleListAdapter
    extends RecyclerView.Adapter<NewsArticleListAdapter.ViewHolder> {
  private ArrayList<NewsStory> mNewsStories;
  private static Context mContext;

    public void swap(ArrayList<NewsStory> datas){
        if(mNewsStories!=null && datas!=null) {
            mNewsStories.clear();
            mNewsStories.addAll(datas);
            notifyDataSetChanged();
        }
    }

  // Provide a reference to the views for each data item
  // Complex data items may need more than one view per item, and
  // you provide access to all the views for a data item in a view holder
  public static class ViewHolder extends RecyclerView.ViewHolder {
    // each data item is just a string in this case
    public TextView mMainTextView=null;
      public ImageView mImageView=null;

    public ViewHolder(View v) {
      super(v);
      CardView cardView = (CardView)v.findViewById(R.id.card_view);
      LinearLayout rl = (LinearLayout)cardView.findViewById(R.id.relativeInsideward);

      mImageView =
          (ImageView)rl.findViewById(R.id.theMainImage);

         mMainTextView =
                (TextView)rl.findViewById(R.id.theMainText);
        //mMainTextView.setTextColor(Color.rgb(255, 255, 255));
        mMainTextView.setText("HELLO HELLO");
    }
  }

  // Provide a suitable constructor (depends on the kind of dataset)
  public NewsArticleListAdapter(ArrayList<NewsStory> myDataset, Context context) {
    mNewsStories = myDataset;
    mContext = context;
  }

  // Create new views (invoked by the layout manager)
  @Override
  public NewsArticleListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
    Log.d("NewsArticleListAdapter",
          "inside news article list adapter viewholder");
    // create a new view
    View v = LayoutInflater.from(parent.getContext())
                 .inflate(R.layout.my_text_view, parent, false);
    // set the view's size, margins, paddings and layout parameters

    ViewHolder vh = new ViewHolder(v);
    return vh;
  }

  // Replace the contents of a view (invoked by the layout manager)
  @Override
  public void onBindViewHolder(ViewHolder holder, final int position) {
    // - get element from your dataset at this position
    // - replace the contents of the view with that element
    // holder.mTextView.setText(mDataset[position]);
      final NewsStory newsStory = mNewsStories.get(position);
      holder.mMainTextView.setText(newsStory.getSummary());
    List<Medium> images = newsStory.getMedia();
    String imgUrl = "http://example.com/";
    for (Medium image : images) {
      if (image.getMediaType().equals(MediaOptions.JUMBO)) {
        imgUrl = image.getUrl();
        break;
      }

      if (image.getMediaType().equals(MediaOptions.MEDIUM)) {
        imgUrl = image.getUrl();
      }
    }

    final String picUrl = imgUrl;
    Picasso
        .with(mContext)
        .load(picUrl)
        .error(R.drawable.ted_cruz2_nyt)
        .fit()
            //.centerInside()      // call .centerInside() or
        .centerCrop() //to avoid a stretched image
        .into(holder.mImageView);
    holder.mImageView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        String url = newsStory.getUrl();
        Intent i = new Intent(mContext, StoryActivity.class);
        // TODO(boloutaredoubeni): a temporary fix to build the object on the other side
        i.putExtra("URL", url);
        i.putExtra("PIC_URL", picUrl);
        i.putExtra("TITLE", newsStory.getTitle());
        i.putExtra("AUTHOR", newsStory.getAuthor());
        mContext.startActivity(i);
      }
    });
  }

  // Return the size of your dataset (invoked by the layout manager)
  @Override
  public int getItemCount() {
      if(mNewsStories ==null){
          return 0;
      }
    return mNewsStories.size();
  }


}
