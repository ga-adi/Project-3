package com.charlesdrews.hud;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.charlesdrews.hud.HudCardData.CardData;
import com.charlesdrews.hud.HudCardData.CardType;

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
        View view = null;

        //TODO - inflate the correct layout for each possible CardType value
        switch (type) {
            case Weather:
                //view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_card, parent, false);
                return new WeatherCard(view, type);
            case Twitter:
                //view = LayoutInflater.from(parent.getContext()).inflate(R.layout.twitter_card, parent, false);
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
                return;
            case Twitter:
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
        public WeatherCard(View itemView, CardType cardType) {
            super(itemView, cardType);
        }
    }

    public class TwitterCard extends CardViewHolder {
        public TwitterCard(View itemView, CardType cardType) {
            super(itemView, cardType);
        }
    }
}
