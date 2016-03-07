package com.charlesdrews.hud;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.charlesdrews.hud.HudCardData.CardData;

import java.util.List;

/**
 * Add cards to the recycler view
 * Created by charlie on 3/7/16.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<CardData> mCardsData;

    public RecyclerAdapter(List<CardData> cardsData) {
        mCardsData = cardsData;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        //TODO - inflate the correct layout for each possible CardType value
        switch (viewType) {
            case 0: // Weather
                //view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_card, parent, false);
                return new WeatherCard(view);
            case 1: // Twitter
                //view = LayoutInflater.from(parent.getContext()).inflate(R.layout.twitter_card, parent, false);
                return new TwitterCard(view);
            default:
                return null;
        }
    }

    @Override
    public int getItemViewType(int position) {
        //TODO - pick an int for each possible CardType value
        switch (mCardsData.get(position).getType()) {
            case Weather:
                return 0;
            case Twitter:
                return 1;
            case News:
                return 2;
            case Concerts:
                return 3;
            default:
                return -1;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CardData data = mCardsData.get(position);

        //TODO - bind data to views for each possible CardType value
        switch (data.getType()) {
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

    //TODO - extend ViewHolder for each possible CardType value
    public class WeatherCard extends RecyclerView.ViewHolder {
        public WeatherCard(View itemView) {
            super(itemView);
        }
    }

    public class TwitterCard extends RecyclerView.ViewHolder {
        public TwitterCard(View itemView) {
            super(itemView);
        }
    }
}
