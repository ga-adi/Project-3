package com.example.android.lately.Cards;

import android.graphics.Color;
import android.support.v7.widget.CardView;
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
    public static final int TYPE_FOURSQUARE = 5;
    public static final int TYPE_REDDIT = 6;
    public static final int TYPE_FACEBOOK = 7;
    public static final int TAB_MAINPAGE = 0;
    public static final int TAB_POLITICS = 1;
    public static final int TAB_TECH = 2;
    public static final int TAB_CAREER = 3;
    public static final int TAB_FOOD = 4;
    public static final int TAB_DANCING = 5;
    public static final int TAB_ANIMALS = 6;
    public static final int TAB_LANGUAGE = 7;
    public static final int TAB_SPORTS = 8;
    public static final int TAB_NATURE = 9;
    public static final int TAB_DATING = 10;


    public CardAdapter(List<ParentCard> parentCardList) {
        this.mParentCardList = parentCardList;
    }


    @Override
    public ParentCardHolder onCreateViewHolder(ViewGroup parent, int viewType) {

//get card type and set to member variable
//        if an event card then

        //Depending on the CardType, We will inflate that specific Layout
        View mView;
        switch (viewType) {
            case TYPE_EVENT:
                mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_card, parent, false);
                return new EventHolder(mView);
            //if a article card then
            case TYPE_SMALL_ARTICLE:
                mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.small_picture_article, parent, false);
                return new SmallArticleHolder(mView);
            case TYPE_WEATHER:
                mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_card,parent, false);
                return new WeatherHolder(mView);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(ParentCardHolder holder, int position) {

        //        check card type member variable

//        switch case again

        mHolder = holder;
//Depending on the card type, we will extract card-specific information and place them into card specific placeholders
        switch (mCurrentCardType) {


            case TYPE_EVENT:

                EventCard eventCard = (EventCard) mParentCardList.get(position);
                final EventHolder eventHolder = (EventHolder) mHolder;

                eventHolder.vGroupName.setText(eventCard.getGroup());
                eventHolder.vTime.setText(eventCard.getTime());
                eventHolder.vEventName.setText(eventCard.getEvent());
                eventHolder.vNumberOfPeople.setText(eventCard.getNumberOfPeople());
                eventHolder.vCardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        eventHolder.vGroupName.setText("This works");
                        eventHolder.vCardView.setCardBackgroundColor(Color.GRAY);
                    }
                });
                break;

            case TYPE_SMALL_ARTICLE:

                SmallArticleCard smallArticleCard = (SmallArticleCard) mParentCardList.get(position);
                final SmallArticleHolder smallArticleHolder = (SmallArticleHolder) mHolder;

                smallArticleHolder.vTitle.setText(smallArticleCard.getmTitle());
                smallArticleHolder.vSource.setText(smallArticleCard.getmSource());
                smallArticleHolder.vTime.setText(smallArticleCard.getmTime());
                //TODO add image here
//                smallArticleHolder.vArticleImage.setImageResource(Color.LTGRAY);
//                smallArticleHolder.vCompanyLogo.setText(smallArticleCard.getmTitle());
                smallArticleHolder.vCardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        smallArticleHolder.vTitle.setText("AwwwesssssoooooomE!!!");
                    }
                });

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

            case TYPE_WEATHER:

                WeatherCard weatherCard = (WeatherCard)mParentCardList.get(position);
                WeatherHolder weatherHolder = (WeatherHolder) mHolder;

                weatherHolder.vCurrentTemp.setText(weatherCard.getmCurrentTemp());
                weatherHolder.vCurrentLocation.setText(weatherCard.getmCurrentLocation());
                weatherHolder.vCurrentDate.setText(weatherCard.getmFormattedCurrentDate());
                //TODO Create Set Weather Image Method using a case/switch returning a drawable to place here
//                weatherHolder.vCurrentImage.setImageDrawable(setWeatherImage(weatherCard.getmCurrentSummary());
                weatherHolder.vDay1High.setText(weatherCard.getmNextFiveDaysHighTemp()[0]);
                weatherHolder.vDay1Low.setText(weatherCard.getmNextFiveDaysLowTemp()[0]);
                weatherHolder.vDay1Date.setText(weatherCard.getmNextFiveDaysDates()[0]);
//                weatherHolder.vDay1Image.setImageDrawable(setWeatherImage(weatherCard.getmNextFiveDaysSummary()[0]));
                weatherHolder.vDay2High.setText(weatherCard.getmNextFiveDaysHighTemp()[1]);
                weatherHolder.vDay2Low.setText(weatherCard.getmNextFiveDaysLowTemp()[1]);
                weatherHolder.vDay2Date.setText(weatherCard.getmNextFiveDaysDates()[1]);
//                weatherHolder.vDay2Image.setImageDrawable(setWeatherImage(weatherCard.getmNextFiveDaysSummary()[1]));
                weatherHolder.vDay3High.setText(weatherCard.getmNextFiveDaysHighTemp()[2]);
                weatherHolder.vDay3Low.setText(weatherCard.getmNextFiveDaysLowTemp()[2]);
                weatherHolder.vDay3Date.setText(weatherCard.getmNextFiveDaysDates()[2]);
//                weatherHolder.vDay3Image.setImageDrawable(setWeatherImage(weatherCard.getmNextFiveDaysSummary()[2]));
                weatherHolder.vDay4High.setText(weatherCard.getmNextFiveDaysHighTemp()[3]);
                weatherHolder.vDay4Low.setText(weatherCard.getmNextFiveDaysLowTemp()[3]);
                weatherHolder.vDay4Date.setText(weatherCard.getmNextFiveDaysDates()[3]);
//                weatherHolder.vDay4Image.setImageDrawable(setWeatherImage(weatherCard.getmNextFiveDaysSummary()[3]));
                weatherHolder.vDay5High.setText(weatherCard.getmNextFiveDaysHighTemp()[4]);
                weatherHolder.vDay5Low.setText(weatherCard.getmNextFiveDaysLowTemp()[4]);
                weatherHolder.vDay5Date.setText(weatherCard.getmNextFiveDaysDates()[4]);
//                weatherHolder.vDay5Image.setImageDrawable(setWeatherImage(weatherCard.getmNextFiveDaysSummary()[4]));

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
        //Identify the type of card at this index/position of our array as we go through the array
        mCurrentCardType = mParentCardList.get(position).getmCardType();
        return mCurrentCardType;
    }

    //Parent Holder can be any card type holder.  Create a card type holder with parent extended.  If adding a new card type...
    //declare member variables, and create constructor which casts the layout id's to the member variable
    public static class ParentCardHolder extends RecyclerView.ViewHolder {
//All holders are rectangles but each specificHolders is it's own square/parrallelogram/rhombus/etc.
        public ParentCardHolder(View itemView) {
            super(itemView);
        }
    }
//A holder targets each vessel(view) inside the layout and prepares values to toss into the vessels(views)
    public static class EventHolder extends ParentCardHolder {

        TextView vGroupName;
        TextView vTime;
        TextView vEventName;
        TextView vNumberOfPeople;
        CardView vCardView;


        public EventHolder(View itemView) {
            super(itemView);

            vGroupName = (TextView) itemView.findViewById(R.id.eventcard_groupname);
            vTime = (TextView) itemView.findViewById(R.id.eventcard_time);
            vEventName = (TextView) itemView.findViewById(R.id.eventcard_eventname);
            vNumberOfPeople = (TextView) itemView.findViewById(R.id.eventcard_attendants);
            vCardView = (CardView) itemView.findViewById(R.id.eventcard_cardview);
        }
    }

    public static class SmallArticleHolder extends ParentCardHolder {

        TextView vSource;
        TextView vTitle;
        TextView vTime;
//        ImageView vCompanyLogo;
//        ImageView vArticleImage;
        CardView vCardView;

        public SmallArticleHolder(View itemView) {
            super(itemView);

            vSource = (TextView) itemView.findViewById(R.id.small_article_source);
            vTitle = (TextView) itemView.findViewById(R.id.small_article_title);
            vTime = (TextView) itemView.findViewById(R.id.small_article_time);
//            vCompanyLogo = (ImageView) itemView.findViewById(R.id.small_article_icon);
//            vArticleImage = (ImageView) itemView.findViewById(R.id.small_article_image);
            vCardView = (CardView) itemView.findViewById(R.id.small_article_cardview);

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

    public static class WeatherHolder extends ParentCardHolder {

        TextView vCurrentDate, vCurrentTemp, vCurrentLocation;
        ImageView vCurrentImage;

        TextView vDay1Date, vDay1High, vDay1Low;
        ImageView vDay1Image;

        TextView vDay2Date, vDay2High, vDay2Low;
        ImageView vDay2Image;

        TextView vDay3Date, vDay3High, vDay3Low;
        ImageView vDay3Image;

        TextView vDay4Date, vDay4High, vDay4Low;
        ImageView vDay4Image;

        TextView vDay5Date, vDay5High, vDay5Low;
        ImageView vDay5Image;

        public WeatherHolder(View itemView) {
            super(itemView);

            vCurrentDate = (TextView) itemView.findViewById(R.id.weathercard_current_date);
            vCurrentTemp = (TextView) itemView.findViewById(R.id.weathercard_current_temp);
            vCurrentLocation = (TextView) itemView.findViewById(R.id.weathercard_current_location);
            vCurrentImage = (ImageView) itemView.findViewById(R.id.weathercard_current_image);

            vDay1Date = (TextView) itemView.findViewById(R.id.weathercard_day1_date);
            vDay1High = (TextView) itemView.findViewById(R.id.weathercard_day1_high);
            vDay1Image = (ImageView) itemView.findViewById(R.id.weathercard_day1_image);
            vDay1Low = (TextView) itemView.findViewById(R.id.weathercard_day1_low);

            vDay2Date = (TextView) itemView.findViewById(R.id.weathercard_day2_date);
            vDay2High = (TextView) itemView.findViewById(R.id.weathercard_day2_high);
            vDay2Image = (ImageView) itemView.findViewById(R.id.weathercard_day2_image);
            vDay2Low = (TextView) itemView.findViewById(R.id.weathercard_day2_low);

            vDay3Date = (TextView) itemView.findViewById(R.id.weathercard_day3_date);
            vDay3High = (TextView) itemView.findViewById(R.id.weathercard_day3_high);
            vDay3Image = (ImageView) itemView.findViewById(R.id.weathercard_day3_image);
            vDay3Low = (TextView) itemView.findViewById(R.id.weathercard_day3_low);

            vDay4Date = (TextView) itemView.findViewById(R.id.weathercard_day4_date);
            vDay4High = (TextView) itemView.findViewById(R.id.weathercard_day4_high);
            vDay4Image = (ImageView) itemView.findViewById(R.id.weathercard_day4_image);
            vDay4Low = (TextView) itemView.findViewById(R.id.weathercard_day4_low);

            vDay5Date = (TextView) itemView.findViewById(R.id.weathercard_day5_date);
            vDay5High = (TextView) itemView.findViewById(R.id.weathercard_day5_high);
            vDay5Image = (ImageView) itemView.findViewById(R.id.weathercard_day5_image);
            vDay5Low = (TextView) itemView.findViewById(R.id.weathercard_day5_low);
        }
    }

}

