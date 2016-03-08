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

    List<ParentCard> parentCardList;
    int mCurrentCardType;
    public static final int TYPE_EVENT = 1;
    public static final int TYPE_SMALL_ARTICLE = 2;

    public CardAdapter(List<ParentCard> parentCardList) {
        this.parentCardList = parentCardList;

    }


    @Override
    public ParentCardHolder onCreateViewHolder(ViewGroup parent, int viewType) {

//get card type and set to member variable
//        if an event card then

        View mView;
        switch (mCurrentCardType) {
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
        mCurrentCardType = parentCardList.get(position).getmCardType();
//        switch case again

        switch (mCurrentCardType) {
            case TYPE_EVENT:

                EventCard eventCard = (EventCard) parentCardList.get(position);
                EventHolder currentHolder = (EventHolder) holder;


                currentHolder.vGroupName.setText(eventCard.getGroup());
                currentHolder.vTime.setText(eventCard.getTime());
                currentHolder.vEventName.setText(eventCard.getEvent());
                currentHolder.vNumberOfPeople.setText(eventCard.getNumberOfPeople());
                break;

            case TYPE_SMALL_ARTICLE:

                SmallArticleCard smallArticleCard = (SmallArticleCard) parentCardList.get(position);
                SmallArticleHolder smallArticleHolder = (SmallArticleHolder) holder;

                smallArticleHolder.vTitle.setText(smallArticleCard.getmTitle());
                smallArticleHolder.vSource.setText(smallArticleCard.getmSource());
                smallArticleHolder.vTime.setText(smallArticleCard.getmTime());
                //TODO add image here
//                smallArticleHolder.vArticleImage.setImageResource(Color.LTGRAY);
//                smallArticleHolder.vCompanyLogo.setText(smallArticleCard.getmTitle());


        }
    }


    @Override
    public int getItemCount() {
        return parentCardList.size();
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
        ImageView vCompanyLogo;
        ImageView vArticleImage;

        public SmallArticleHolder(View itemView) {
            super(itemView);

            vSource = (TextView) itemView.findViewById(R.id.small_article_source);
            vTitle = (TextView) itemView.findViewById(R.id.small_article_title);
            vTime = (TextView) itemView.findViewById(R.id.small_article_time);
            vCompanyLogo = (ImageView) itemView.findViewById(R.id.small_article_icon);
            vArticleImage = (ImageView) itemView.findViewById(R.id.small_article_image);

        }
    }

    public int getItemViewType(int position) {


        return super.getItemViewType(position);
    }
}

