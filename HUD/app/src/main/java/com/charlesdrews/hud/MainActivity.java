package com.charlesdrews.hud;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.charlesdrews.hud.Facebook.FacebookContentProvider;
import com.facebook.FacebookSdk;

import com.charlesdrews.hud.Facebook.FacebookCardData;
import com.charlesdrews.hud.News.NewsCardData;
import com.charlesdrews.hud.News.NewsContentProvider;
import com.charlesdrews.hud.Weather.WeatherCardData;
import com.charlesdrews.hud.Weather.WeatherContentProvider;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private ArrayList<CardData> mCardsData;
    private RecyclerView.Adapter mAdapter;
    private Account mAccount;
    private HashMap<CardType, Integer> mCardMap;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAccount = createSyncAccount(this);
        mCardMap = new HashMap<>();

        // register content observers
        /*
        getContentResolver().registerContentObserver(
                WeatherContentProvider.CONTENT_URI, true, new WeatherContentObserver(new Handler()));
        getContentResolver().registerContentObserver(
                NewsContentProvider.CONTENT_URI, true, new NewsContentObserver(new Handler()));
                */
        getContentResolver().registerContentObserver(FacebookContentProvider.CONTENT_URI, true,
                new CardContentObserver(new Handler(), CardType.Facebook));
        getContentResolver().registerContentObserver(NewsContentProvider.CONTENT_URI, true,
                new CardContentObserver(new Handler(), CardType.News));
        getContentResolver().registerContentObserver(WeatherContentProvider.CONTENT_URI, true,
                new CardContentObserver(new Handler(), CardType.Weather));
        //TODO - create and register remaining observers

        // set up recycler view & adapter
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        mCardsData = new ArrayList<>();

        //TODO - remove this - it's for testing only
        WeatherCardData weatherCardData = new WeatherCardData(CardType.Weather, 65, 37);
        mCardsData.add(weatherCardData);
        mCardMap.put(CardType.Weather, mCardsData.indexOf(weatherCardData));

        FacebookCardData facebookCardData = new FacebookCardData(CardType.Facebook, "Kyle", "Facebook is better than Twitter!");
        mCardsData.add(facebookCardData);
        mCardMap.put(CardType.Facebook, mCardsData.indexOf(facebookCardData));


        mAdapter = new RecyclerAdapter(mCardsData);
        recyclerView.setAdapter(mAdapter);

        // set up syncing
        Bundle settingsBundle = new Bundle();
        settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);

        // weather - every 10 min
        ContentResolver.requestSync(mAccount, WeatherContentProvider.AUTHORITY, settingsBundle);
        ContentResolver.setSyncAutomatically(mAccount, WeatherContentProvider.AUTHORITY, true);
        ContentResolver.addPeriodicSync(mAccount, WeatherContentProvider.AUTHORITY, Bundle.EMPTY, 60 * 10);

        // news - every 20 min
        ContentResolver.requestSync(mAccount, NewsContentProvider.AUTHORITY, settingsBundle);
        ContentResolver.setSyncAutomatically(mAccount, NewsContentProvider.AUTHORITY, true);
        ContentResolver.addPeriodicSync(mAccount, NewsContentProvider.AUTHORITY, Bundle.EMPTY, 60 * 20);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                //TODO - launch settings activity
                return true;
            /*
            case R.id.search:
                //TODO - setup and launch search
                return true;
                */
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public class CardContentObserver extends ContentObserver {
        private CardType mObserverCardType;

        public CardContentObserver(Handler handler, CardType type) {
            super(handler);
            mObserverCardType = type;
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            Log.d(MainActivity.class.getCanonicalName(), "Starting Weather onChange...");
            UpdateDataAsyncTask task = new UpdateDataAsyncTask();
            task.execute(CardType.Weather);
        }

        public CardType getObserverCardType() {
            return mObserverCardType;
        }
    }

    /*
    public class WeatherContentObserver extends ContentObserver {
        public WeatherContentObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            Log.d(MainActivity.class.getCanonicalName(), "Starting Weather onChange...");
            UpdateDataAsyncTask task = new UpdateDataAsyncTask();
            task.execute(CardType.Weather);
        }
    }

    public class NewsContentObserver extends ContentObserver {
        public NewsContentObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            Log.d(MainActivity.class.getCanonicalName(), "Starting News onChange...");
            UpdateDataAsyncTask task = new UpdateDataAsyncTask();
            task.execute(CardType.News);
        }
    }

    public class FacebookContentObserver extends ContentObserver {
        public FacebookContentObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            Log.d(MainActivity.class.getCanonicalName(), "Starting Facebook onChange...");
            UpdateDataAsyncTask task = new UpdateDataAsyncTask();
            task.execute(CardType.Facebook)
        }
    }
    */

    public static Account createSyncAccount(Context context) {
        Account newAccount = new Account(
                context.getString(R.string.account),
                context.getString(R.string.account_type)
        );

        AccountManager accountManager =
                (AccountManager) context.getSystemService(ACCOUNT_SERVICE);

        if (accountManager.addAccountExplicitly(newAccount, null, null)) {
            Log.d(MainActivity.class.getCanonicalName(), "Account added successfully");
        } else {
            Log.d(MainActivity.class.getCanonicalName(), "Account NOT added successfully");
        }
        return newAccount;
    }

    private class UpdateDataAsyncTask extends AsyncTask<CardType, Void, Cursor> {
        private CardType mCardType;

        @Override
        protected Cursor doInBackground(CardType... params) {
            Log.d(UpdateDataAsyncTask.class.getCanonicalName(), "Starting async load of " + params[0].toString());

            if ((mCardType = params[0]) == null) {
                cancel(true);
            }

            switch (mCardType) {
                case Facebook:
                    return getContentResolver().query(FacebookContentProvider.CONTENT_URI, null, null, null, null);
                case News:
                    return getContentResolver().query(NewsContentProvider.CONTENT_URI, null, null, null, null);
                case Weather:
                    return getContentResolver().query(WeatherContentProvider.CONTENT_URI, null, null, null, null);
                default:
                    return null;
            }
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);

            if (cursor != null && cursor.moveToFirst()) {

                Integer index = mCardMap.get(mCardType);    // returns null if key not in map
                if (index == null) {
                    index = mCardsData.size();              // add card to end of list
                    mCardMap.put(mCardType, index);         // save index in map
                } else {
                    mCardsData.remove(index);               // replace card in same position
                }

                switch (mCardType) {
                    case Facebook:
                        FacebookCardData facebookCardData = new FacebookCardData(
                                CardType.Facebook,
                                cursor.getString(cursor.getColumnIndex(DatabaseHelper.FACEBOOK_COL_AUTHOR)),
                                cursor.getString(cursor.getColumnIndex(DatabaseHelper.FACEBOOK_COL_STATUS_UPDATE))
                        );
                        mCardsData.add(index, facebookCardData);
                        break;

                    case News:
                        NewsCardData newsCardData = new NewsCardData(
                                CardType.News,
                                cursor.getString(cursor.getColumnIndex(DatabaseHelper.NEWS_COL_HEADLINE))
                        );
                        mCardsData.add(index, newsCardData);
                        break;

                    case Weather:
                        WeatherCardData weatherCardData = new WeatherCardData(
                                CardType.Weather,
                                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.WEATHER_COL_HIGH)),
                                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.WEATHER_COL_LOW))
                        );
                        mCardsData.add(index, weatherCardData);
                        break;

                    default:
                        break;
                }
                mAdapter.notifyDataSetChanged();
            }
            cursor.close();
        }
    }
}
