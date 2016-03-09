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

import com.charlesdrews.hud.Facebook.FacebookCardData;
import com.charlesdrews.hud.News.NewsCardData;
import com.charlesdrews.hud.News.NewsContentProvider;
import com.charlesdrews.hud.Weather.WeatherCardData;
import com.charlesdrews.hud.Weather.WeatherContentProvider;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getCanonicalName();

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
                WeatherContentProvider.WEATHER_URI, true, new WeatherContentObserver(new Handler()));
        getContentResolver().registerContentObserver(
                NewsContentProvider.WEATHER_URI, true, new NewsContentObserver(new Handler()));
        getContentResolver().registerContentObserver(FacebookContentProvider.CONTENT_URI, true,
                new CardContentObserver(new Handler(), CardType.Facebook));
        getContentResolver().registerContentObserver(NewsContentProvider.CONTENT_URI, true,
                new CardContentObserver(new Handler(), CardType.News));
        getContentResolver().registerContentObserver(WeatherContentProvider.CONTENT_URI, true,
                new CardContentObserver(new Handler(), CardType.Weather));
                */
        getContentResolver().registerContentObserver(
                Uri.parse(CardContentProvider.BASE_URI_STRING),
                true,
                new CardContentObserver(new Handler())
        );
        //TODO - create and register remaining observers

        // set up recycler view & adapter
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        mCardsData = new ArrayList<>();
        mAdapter = new RecyclerAdapter(mCardsData);
        recyclerView.setAdapter(mAdapter);

        // set up syncing
        Bundle settingsBundle = new Bundle();
        settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        ContentResolver.requestSync(mAccount, CardContentProvider.AUTHORITY, settingsBundle);
        ContentResolver.setSyncAutomatically(mAccount, CardContentProvider.AUTHORITY, true);
        ContentResolver.addPeriodicSync(mAccount, CardContentProvider.AUTHORITY, Bundle.EMPTY, 60 * 15);

        /*
        // weather - every 10 min
        ContentResolver.requestSync(mAccount, WeatherContentProvider.AUTHORITY, settingsBundle);
        ContentResolver.setSyncAutomatically(mAccount, WeatherContentProvider.AUTHORITY, true);
        ContentResolver.addPeriodicSync(mAccount, WeatherContentProvider.AUTHORITY, Bundle.EMPTY, 60 * 10);

        // news - every 20 min
        ContentResolver.requestSync(mAccount, NewsContentProvider.AUTHORITY, settingsBundle);
        ContentResolver.setSyncAutomatically(mAccount, NewsContentProvider.AUTHORITY, true);
        ContentResolver.addPeriodicSync(mAccount, NewsContentProvider.AUTHORITY, Bundle.EMPTY, 60 * 20);
        */
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

        public CardContentObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            int uriType = CardContentProvider.sUriMatcher.match(uri);
            
            switch (uriType) {
                case CardContentProvider.FACEBOOK:
                    Log.d(TAG, "onChange: facebook");
                    new UpdateDataAsyncTask().execute(CardType.Facebook);
                    break;

                case CardContentProvider.NEWS:
                    Log.d(TAG, "onChange: news");
                    new UpdateDataAsyncTask().execute(CardType.News);
                    break;

                case CardContentProvider.WEATHER:
                    Log.d(TAG, "onChange: weather");
                    new UpdateDataAsyncTask().execute(CardType.Weather);
                    break;

                default:
                    break;
            }
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
        private final String TAG = UpdateDataAsyncTask.class.getCanonicalName();

        private CardType mCardType;

        @Override
        protected Cursor doInBackground(CardType... params) {
            Log.d(UpdateDataAsyncTask.class.getCanonicalName(), "Starting async load of " + params[0].toString());

            if ((mCardType = params[0]) == null) {
                cancel(true);
            }

            switch (mCardType) {
                case Facebook:
                    Log.d(TAG, "doInBackground: query facebook");
                    return getContentResolver().query(CardContentProvider.FACEBOOK_URI, null, null, null, null);
                case News:
                    Log.d(TAG, "doInBackground: query news");
                    return getContentResolver().query(CardContentProvider.NEWS_URI, null, null, null, null);
                case Weather:
                    Log.d(TAG, "doInBackground: query weather");
                    return getContentResolver().query(CardContentProvider.WEATHER_URI, null, null, null, null);
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
