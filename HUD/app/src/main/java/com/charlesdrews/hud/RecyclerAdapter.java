package com.charlesdrews.hud;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.charlesdrews.hud.HudCardData.CardData;
import com.charlesdrews.hud.HudCardData.CardType;
import com.charlesdrews.hud.HudCardData.TwitterCardData;
import com.charlesdrews.hud.HudCardData.WeatherCardData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Add cards to the recycler view
 * Created by charlie on 3/7/16.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.CardViewHolder> {
    private List<CardData> mCardsData;
    private ArrayList<CardType> mCardTypes;

    public RecyclerAdapter(List<CardData> cardsData) {
        mCardsData = cardsData;
        mCardTypes = new ArrayList<>(Arrays.asList(CardType.values()));
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardType type = mCardTypes.get(viewType);
        View view;

        //TODO - inflate the correct layout for each possible CardType value
        switch (type) {
            case Weather:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_card, parent, false);
                return new WeatherCard(view, type);

            case Twitter:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.twitter_card, parent, false);
                return new TwitterCard(view, type);

            default:
                return null;
        }
    }

    @Override
    public int getItemViewType(int position) {
        CardType type = mCardsData.get(position).getType();
        return mCardTypes.indexOf(type);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        CardData data = mCardsData.get(position);

        //TODO - bind data to views for each possible CardType value
        switch (holder.getCardType()) {
            case Weather:
                WeatherCard weatherCard = (WeatherCard) holder;
                WeatherCardData weatherData = (WeatherCardData) data;

                weatherCard.mHighTemp.setText("High temp: " + weatherData.getHighTemp());
                weatherCard.mLowTemp.setText("Low temp: " + weatherData.getLowTemp());
                return;

            case Twitter:
                TwitterCard twitterCard = (TwitterCard) holder;
                TwitterCardData twitterData = (TwitterCardData) data;

                twitterCard.mAuthor.setText(twitterData.getAuthor());
                twitterCard.mTweetText.setText(twitterData.getTweetText());
                return;

            case News:
                return;

            default:
                return;
        }
    }

    @Override
    public int getItemCount() {
        return mCardsData.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {
        private CardType mCardType;

        public CardViewHolder(View itemView, CardType cardType) {
            super(itemView);
            mCardType = cardType;
        }

        public CardType getCardType() {
            return mCardType;
        }
    }

    //TODO - extend ViewHolder for each possible CardType value
    public class WeatherCard extends CardViewHolder {
        TextView mHighTemp, mLowTemp;

        public WeatherCard(View itemView, CardType cardType) {
            super(itemView, cardType);
            mHighTemp = (TextView) itemView.findViewById(R.id.high_temp);
            mLowTemp = (TextView) itemView.findViewById(R.id.low_temp);
        }
    }

    public class TwitterCard extends CardViewHolder {
        TextView mAuthor, mTweetText;

        public TwitterCard(View itemView, CardType cardType) {
            super(itemView, cardType);
            mAuthor = (TextView) itemView.findViewById(R.id.author);
            mTweetText = (TextView) itemView.findViewById(R.id.tweet_text);
        }
    }
}
