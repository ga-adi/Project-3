package com.charlesdrews.hud;

import android.content.Context;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.charlesdrews.hud.CardsData.CardData;
import com.charlesdrews.hud.CardsData.CardType;
import com.charlesdrews.hud.CardsData.FacebookCardData;
import com.charlesdrews.hud.CardsData.NewsCardData;
import com.charlesdrews.hud.CardsData.NewsRecyclerAdapter;
import com.charlesdrews.hud.CardsData.WeatherCardData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Add cards to the recycler view
 * Created by charlie on 3/7/16.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.CardViewHolder> {
    private final ArrayList<CardType> mCardTypes = new ArrayList<>(Arrays.asList(CardType.values()));
    private List<CardData> mCardsData;
    private Context mContext;

    //TODO - pass parent.getContext() to ViewHolder constructor, rather than taking context here
    public RecyclerAdapter(Context context, List<CardData> cardsData) {
        mContext = context;
        mCardsData = cardsData;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardType type = mCardTypes.get(viewType);
        View view;

        //TODO - inflate the correct layout for each possible CardType value
        switch (type) {
            case Weather: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_card, parent, false);
                return new WeatherCard(view, type);
            }
            case Facebook: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.facebook_card, parent, false);
                return new FacebookCard(view, type);
            }
            case News: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_card, parent, false);
                return new NewsCard(view, type);
            }
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
            case Weather: {
                WeatherCard weatherCard = (WeatherCard) holder;
                WeatherCardData weatherData = (WeatherCardData) data;

                weatherCard.mHighTemp.setText("High temp: " + weatherData.getHighTemp());
                weatherCard.mLowTemp.setText("Low temp: " + weatherData.getLowTemp());
                break;
            }
            case Facebook: {
                FacebookCard facebookCard = (FacebookCard) holder;
                FacebookCardData facebookData = (FacebookCardData) data;

                facebookCard.mAuthor.setText(facebookData.getAuthor());
                facebookCard.mStatusUpdate.setText(facebookData.getStatusUpdate());
                break;
            }
            case News: {
                NewsCard newsCard = (NewsCard) holder;
                NewsCardData newsCardData = (NewsCardData) data;

                LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
                newsCard.mNewsRecyclerView.setLayoutManager(layoutManager);

                NewsRecyclerAdapter adapter = new NewsRecyclerAdapter(newsCardData.getNewsItems());
                newsCard.mNewsRecyclerView.setAdapter(adapter);
                break;
            }
            default:
                break;
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

    public class FacebookCard extends CardViewHolder {
        TextView mAuthor, mStatusUpdate;

        public FacebookCard(View itemView, CardType cardType) {
            super(itemView, cardType);
            mAuthor = (TextView) itemView.findViewById(R.id.author);
            mStatusUpdate = (TextView) itemView.findViewById(R.id.status_update);
        }
    }

    public class NewsCard extends CardViewHolder {
        RecyclerView mNewsRecyclerView;
        //TODO - add a text view saying when it was last updated???

        public NewsCard(View itemView, CardType cardType) {
            super(itemView, cardType);
            mNewsRecyclerView = (RecyclerView) itemView.findViewById(R.id.newsRecyclerView);
        }
    }
}
