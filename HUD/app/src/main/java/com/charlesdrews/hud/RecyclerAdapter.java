package com.charlesdrews.hud;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.charlesdrews.hud.CardsData.CardData;
import com.charlesdrews.hud.CardsData.CardType;
import com.charlesdrews.hud.CardsData.FacebookCardData;
import com.charlesdrews.hud.CardsData.NewsCardData;
import com.charlesdrews.hud.CardsData.NewsRecyclerAdapter;
import com.charlesdrews.hud.CardsData.RemindersCardData;
import com.charlesdrews.hud.CardsData.RemindersRecyclerAdapter;
import com.charlesdrews.hud.CardsData.WeatherCardData;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

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
    public LoginButton mFacebookLoginButton;
    public static CallbackManager mCallbackManager;
    TextView mLoginText;
    public static boolean mIsLoggedInToFacebook;

    //TODO - pass parent.getContext() to ViewHolder constructor, rather than taking context here
    public RecyclerAdapter(List<CardData> cardsData) {
        mCardsData = cardsData;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        if (viewType == -1) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_card, parent, false);
            return new CardViewHolder(view, parent.getContext(), null);
        }

        CardType type = mCardTypes.get(viewType);

        //TODO - inflate the correct layout for each possible CardType value
        switch (type) {
            case Weather: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_card, parent, false);
                return new WeatherCard(view, parent.getContext(), type);
            }
            case Facebook: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.facebook_card, parent, false);
                mLoginText = (TextView)view.findViewById(R.id.status_update);
                facebookLogin(view);
                return new FacebookCard(view, parent.getContext(), type);
            }
            case News: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_card, parent, false);
                return new NewsCard(view, parent.getContext(), type);
            }
            case Reminders: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reminders_card, parent, false);
                return new RemindersCard(view, parent.getContext(), type);
            }
            default:
                return new CardViewHolder(view, parent.getContext(), null);
        }
    }

    @Override
    public int getItemViewType(int position) {
        CardData cardData = mCardsData.get(position);
        if (cardData == null) {
            return -1;
        } else {
            CardType type = mCardsData.get(position).getType();
            return mCardTypes.indexOf(type);
        }
    }

    @Override
    public void onBindViewHolder(final CardViewHolder holder, int position) {
        CardData data = mCardsData.get(position);

        if (holder == null || data == null) { return; }

        //TODO - bind data to views for each possible CardType value
        switch (holder.getCardType()) {
            case Weather: {
                if ( !(data instanceof WeatherCardData) ) { return; }
                WeatherCard weatherCard = (WeatherCard) holder;
                WeatherCardData weatherData = (WeatherCardData) data;

                weatherCard.mHighTemp.setText("High temp: " + weatherData.getHighTemp());
                weatherCard.mLowTemp.setText("Low temp: " + weatherData.getLowTemp());
                break;
            }
            case Facebook: {
                if ( !(data instanceof FacebookCardData) ) { return; }
                FacebookCard facebookCard = (FacebookCard) holder;
                FacebookCardData facebookData = (FacebookCardData) data;

                facebookCard.mAuthor.setText(facebookData.getAuthor());
                facebookCard.mStatusUpdate.setText(facebookData.getStatusUpdate());
                break;
            }
            case News: {
                if ( !(data instanceof NewsCardData) ) { return; }
                NewsCard newsCard = (NewsCard) holder;
                NewsCardData newsCardData = (NewsCardData) data;

                LinearLayoutManager layoutManager = new LinearLayoutManager(holder.mContext);
                newsCard.mNewsRecyclerView.setLayoutManager(layoutManager);

                NewsRecyclerAdapter adapter = new NewsRecyclerAdapter(newsCardData.getNewsItems());
                newsCard.mNewsRecyclerView.setAdapter(adapter);
                break;
            }
            case Reminders: {
                if ( !(data instanceof RemindersCardData) ) { return; }
                RemindersCard remindersCard = (RemindersCard) holder;
                RemindersCardData remindersCardData = (RemindersCardData) data;

                LinearLayoutManager layoutManager = new LinearLayoutManager(holder.mContext);
                remindersCard.mRemindersRecyclerView.setLayoutManager(layoutManager);

                RemindersRecyclerAdapter adapter = new RemindersRecyclerAdapter(remindersCardData.getReminders());
                remindersCard.mRemindersRecyclerView.setAdapter(adapter);

                remindersCard.mAddReminderButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        launchAddReminderDialog(holder.mContext);
                    }
                });
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
        Context mContext;
        CardType mCardType;

        public CardViewHolder(View itemView, Context context, CardType cardType) {
            super(itemView);
            mContext = context;
            mCardType = cardType;
        }

        public CardType getCardType() {
            return mCardType;
        }
    }

    //TODO - extend CardViewHolder for each possible CardType value
    public class WeatherCard extends CardViewHolder {
        TextView mHighTemp, mLowTemp;

        public WeatherCard(View itemView, Context context, CardType cardType) {
            super(itemView, context, cardType);
            mHighTemp = (TextView) itemView.findViewById(R.id.high_temp);
            mLowTemp = (TextView) itemView.findViewById(R.id.low_temp);
        }
    }

    public class FacebookCard extends CardViewHolder {
        TextView mAuthor, mStatusUpdate;

        public FacebookCard(View itemView, Context context, CardType cardType) {
            super(itemView, context, cardType);
            mAuthor = (TextView) itemView.findViewById(R.id.author);
            mStatusUpdate = (TextView) itemView.findViewById(R.id.status_update);
        }
    }

    public class NewsCard extends CardViewHolder {
        RecyclerView mNewsRecyclerView;
        //TODO - add a text view saying when it was last updated???

        public NewsCard(View itemView, Context context, CardType cardType) {
            super(itemView, context, cardType);
            mNewsRecyclerView = (RecyclerView) itemView.findViewById(R.id.newsRecyclerView);
        }
    }

    public class RemindersCard extends CardViewHolder {
        RecyclerView mRemindersRecyclerView;
        FloatingActionButton mAddReminderButton;
        //TODO - add a text view saying when it was last updated???

        public RemindersCard(View itemView, Context context, CardType cardType) {
            super(itemView, context, cardType);
            mRemindersRecyclerView = (RecyclerView) itemView.findViewById(R.id.remindersRecyclerView);
            mAddReminderButton = (FloatingActionButton) itemView.findViewById(R.id.addReminderButton);
        }
    }

    public void launchAddReminderDialog(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Add Reminder");
        EditText input = new EditText(context);
        input.setHint("Enter reminder content");
        builder.setView(input);
        builder.setNegativeButton("Cancel", null);
        builder.setNeutralButton("Add Alarm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "Add alarm", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "yup", Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }

    //TODO set flag to check for logged in status
    //TODO return true once they login, and set syncadapter API
    //TODO to run if ( facebookLogin() )
    public void facebookLogin(View view){
        mFacebookLoginButton = (LoginButton)view.findViewById(R.id.login_button);
        mFacebookLoginButton.setReadPermissions("user_posts");
        mCallbackManager = CallbackManager.Factory.create();
        mFacebookLoginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
//                        Toast.makeText(MainActivity.class, "Logged in successfully", Toast.LENGTH_SHORT).show();
                //TODO Set text to results
                       mLoginText.setText("User ID: " + loginResult.getAccessToken().getUserId() + "Auth token: " + loginResult.getAccessToken().getToken());
                mIsLoggedInToFacebook = true;

            }

            @Override
            public void onCancel() {
//                        Toast.makeText(MainActivity.this, "Login canceled", Toast.LENGTH_SHORT).show();
                mIsLoggedInToFacebook = false;
            }

            @Override
            public void onError(FacebookException error) {
//                        Toast.makeText(MainActivity.this, "Login error", Toast.LENGTH_SHORT).show();
                mIsLoggedInToFacebook = false;
            }
        });
    }

}
