package com.example.android.lately.Cards;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.lately.R;

import java.util.List;

/**
 * Created by perrycooperman on 3/8/16.
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ParentCardHolder> {

    List<ParentCard> mParentCardList;
    int mCurrentCardType;
    ParentCardHolder mHolder;
    public static final int TYPE_WEATHER = 1;
    public static final int TYPE_EVENT = 2;
    public static final int TYPE_SMALL_ARTICLE = 3;
    public static final int TYPE_LARGE_ARTICLE = 4;

    public CardAdapter(List<ParentCard> parentCardList) {
        this.mParentCardList = parentCardList;
//        mCurrentCardType = mParentCardList.get(0).getmCardType();
    }


    @Override
    public ParentCardHolder onCreateViewHolder(ViewGroup parent, int viewType) {

//get card type and set to member variable
//        if an event card then

        View mView;
        switch (viewType) {
            case TYPE_EVENT:
                mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_card, parent, false);
                return new EventHolder(mView);
            //if a article card then
            case TYPE_SMALL_ARTICLE:
                mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.small_picture_article, parent, false);
                return new SmallArticleHolder(mView);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(ParentCardHolder holder, int position) {

        //        check card type member variable

//        switch case again

        mHolder = holder;

        switch (mCurrentCardType) {


            case TYPE_EVENT:

                EventCard eventCard = (EventCard) mParentCardList.get(position);
                EventHolder eventHolder = (EventHolder) mHolder;


                eventHolder.vGroupName.setText(eventCard.getGroup());
                eventHolder.vTime.setText(eventCard.getTime());
                eventHolder.vEventName.setText(eventCard.getEvent());
                eventHolder.vNumberOfPeople.setText(eventCard.getNumberOfPeople());
                break;

            case TYPE_SMALL_ARTICLE:

                SmallArticleCard smallArticleCard = (SmallArticleCard) mParentCardList.get(position);
                SmallArticleHolder smallArticleHolder = (SmallArticleHolder) mHolder;

                smallArticleHolder.vTitle.setText(smallArticleCard.getmTitle());
                smallArticleHolder.vSource.setText(smallArticleCard.getmSource());
                smallArticleHolder.vTime.setText(smallArticleCard.getmTime());
                //TODO add image here
//                smallArticleHolder.vArticleImage.setImageResource(Color.LTGRAY);
//                smallArticleHolder.vCompanyLogo.setText(smallArticleCard.getmTitle());
                break;

            case TYPE_LARGE_ARTICLE:

                LargeArticleCard largeArticleCard = (LargeArticleCard) mParentCardList.get(position);
                LargeArticleHolder largeArticleHolder = (LargeArticleHolder) mHolder;

                largeArticleHolder.vTitle.setText(largeArticleCard.getmTitle());
                largeArticleHolder.vSource.setText(largeArticleCard.getmSource());
                largeArticleHolder.vTime.setText(largeArticleCard.getmTime());
                //TODO add image here
//                smallArticleHolder.vArticleImage.setImageResource(Color.LTGRAY);
//                smallArticleHolder.vCompanyLogo.setText(smallArticleCard.getmTitle());
                break;


        }
            mHolder = null;
    }


    @Override
    public int getItemCount() {
        return mParentCardList.size();
    }

    @Override
    public int getItemViewType(int position) {
        mCurrentCardType = mParentCardList.get(position).getmCardType();
        return mCurrentCardType;
    }

    //Parent Holder can be any card type holder.  Create a card type holder with parent extended.  If adding a new card type...
    //declare member variables, and create constructor which casts the layout id's to the member variable
    public static class ParentCardHolder extends RecyclerView.ViewHolder {

        public ParentCardHolder(View itemView) {
            super(itemView);
        }
    }

    public static class EventHolder extends ParentCardHolder {

        TextView vGroupName;
        TextView vTime;
        TextView vEventName;
        TextView vNumberOfPeople;


        public EventHolder(View itemView) {
            super(itemView);

            vGroupName = (TextView) itemView.findViewById(R.id.eventcard_groupname);
            vTime = (TextView) itemView.findViewById(R.id.eventcard_time);
            vEventName = (TextView) itemView.findViewById(R.id.eventcard_eventname);
            vNumberOfPeople = (TextView) itemView.findViewById(R.id.eventcard_attendants);

        }
    }

    public static class SmallArticleHolder extends ParentCardHolder {

        TextView vSource;
        TextView vTitle;
        TextView vTime;
//        ImageView vCompanyLogo;
//        ImageView vArticleImage;

        public SmallArticleHolder(View itemView) {
            super(itemView);

            vSource = (TextView) itemView.findViewById(R.id.small_article_source);
            vTitle = (TextView) itemView.findViewById(R.id.small_article_title);
            vTime = (TextView) itemView.findViewById(R.id.small_article_time);
//            vCompanyLogo = (ImageView) itemView.findViewById(R.id.small_article_icon);
//            vArticleImage = (ImageView) itemView.findViewById(R.id.small_article_image);

        }
    }

    public static class LargeArticleHolder extends ParentCardHolder {

        TextView vSource;
        TextView vTitle;
        TextView vTime;
        ImageView vCompanyLogo;
        ImageView vArticleImage;

        public LargeArticleHolder(View itemView) {
            super(itemView);

            vSource = (TextView) itemView.findViewById(R.id.large_article_source);
            vTitle = (TextView) itemView.findViewById(R.id.large_article_title);
            vTime = (TextView) itemView.findViewById(R.id.large_article_time);
            vCompanyLogo = (ImageView) itemView.findViewById(R.id.large_article_icon);
            vArticleImage = (ImageView) itemView.findViewById(R.id.large_article_image);

        }
    }

}

