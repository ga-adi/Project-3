package com.charlesdrews.hud;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.charlesdrews.hud.Facebook.FacebookCardData;
import com.charlesdrews.hud.News.NewsCardData;
import com.charlesdrews.hud.News.NewsContentProvider;
import com.charlesdrews.hud.Weather.WeatherCardData;
import com.charlesdrews.hud.Weather.WeatherContentProvider;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<CardData> mCardsData;
    private RecyclerView.Adapter mAdapter;
    private Account mAccount;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAccount = createSyncAccount(this);

        // register content observers
        getContentResolver().registerContentObserver(
                WeatherContentProvider.CONTENT_URI, true, new WeatherContentObserver(new Handler()));
        getContentResolver().registerContentObserver(
                NewsContentProvider.CONTENT_URI, true, new NewsContentObserver(new Handler()));
        //TODO - create and register remaining observers

        // set up recycler view & adapter
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        mCardsData = new ArrayList<>();

        //TODO - remove this - it's for testing only
        mCardsData.add(new WeatherCardData(CardType.Weather, 65, 37));
        mCardsData.add(new FacebookCardData(CardType.Facebook, "Kyle", "Facebook is better than Twitter!"));
        mCardsData.add(new NewsCardData(CardType.News, "This is an important headline"));

        //TODO - consider using a hashmap to keep track of which card is in which position in the adapter

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

    public class WeatherContentObserver extends ContentObserver {

        public WeatherContentObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            Log.d(MainActivity.class.getCanonicalName(), "Starting Weather onChange...");

            Cursor cursor = getContentResolver().query(WeatherContentProvider.CONTENT_URI, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                WeatherCardData weatherCardData = new WeatherCardData(
                        CardType.Weather,
                        cursor.getInt(cursor.getColumnIndex(DatabaseHelper.WEATHER_COL_HIGH)),
                        cursor.getInt(cursor.getColumnIndex(DatabaseHelper.WEATHER_COL_LOW))
                );

                boolean weatherCardUpdated = false;

                for (int i = 0; i < mCardsData.size(); i++) {
                    if (mCardsData.get(i) instanceof WeatherCardData) {
                        mCardsData.remove(i);
                        mCardsData.add(i, weatherCardData);
                        weatherCardUpdated = true;
                        break;
                    }
                }

                if (!weatherCardUpdated) {
                    mCardsData.add(weatherCardData);
                }
                mAdapter.notifyDataSetChanged();
            }
            cursor.close();
        }
    }

    public class NewsContentObserver extends ContentObserver {

        public NewsContentObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            Log.d(MainActivity.class.getCanonicalName(), "Starting News onChange...");

            Cursor cursor = getContentResolver().query(NewsContentProvider.CONTENT_URI, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                NewsCardData newsCardData = new NewsCardData(
                        CardType.News,
                        cursor.getString(cursor.getColumnIndex(DatabaseHelper.NEWS_COL_HEADLINE))
                );

                boolean newsCardUpdated = false;

                for (int i = 0; i < mCardsData.size(); i++) {
                    if (mCardsData.get(i) instanceof NewsCardData) {
                        mCardsData.remove(i);
                        mCardsData.add(i, newsCardData);
                        newsCardUpdated = true;
                        break;
                    }
                }

                if (!newsCardUpdated) {
                    mCardsData.add(newsCardData);
                }
                mAdapter.notifyDataSetChanged();
            }
            cursor.close();
        }
    }

    public static Account createSyncAccount(Context context) {
        // Create the account type and default account
        Account newAccount = new Account(
                context.getString(R.string.account),
                context.getString(R.string.account_type)
        );

        // Get an instance of the Android account manager
        AccountManager accountManager =
                (AccountManager) context.getSystemService(ACCOUNT_SERVICE);

        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
        if (accountManager.addAccountExplicitly(newAccount, null, null)) {
          /*
           * If you don't set android:syncable="true" in
           * in your <provider> element in the manifest,
           * then call context.setIsSyncable(account, AUTHORITY, 1)
           * here.
           */
        } else {
            /*
             * The account exists or some other error occurred. Log this, report it,
             * or handle it internally.
             */
        }
        return newAccount;
    }
}
