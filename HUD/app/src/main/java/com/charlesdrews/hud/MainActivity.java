package com.charlesdrews.hud;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.SearchManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.Toast;

import com.charlesdrews.hud.CardsData.MtaStatusCardData;
import com.charlesdrews.hud.CardsData.Reminder;
import com.charlesdrews.hud.CardsData.RemindersCardData;
import com.facebook.FacebookSdk;

import com.charlesdrews.hud.CardsData.CardData;
import com.charlesdrews.hud.CardsData.CardType;
import com.charlesdrews.hud.CardsData.FacebookCardData;
import com.charlesdrews.hud.CardsData.NewsCardData;
import com.charlesdrews.hud.CardsData.WeatherCardData;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements ReminderCreator.OnReminderSubmittedListener, SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = MainActivity.class.getCanonicalName();

    public static final int ITEM_COUNT = 3;
    public static final int WEATHER_POSITION = 0;
    public static final int NEWS_POSITION = 1;
    public static final int FACEBOOK_POSITION = 2;
    public static final int REMINDERS_POSITION = 3;
    public static final int MTA_STATUS_POSITION = 4;

    public static final long SYNC_INTERVAL = 60; // seconds

    private ArrayList<CardData> mCardsData;
    private RecyclerView.Adapter mAdapter;
    private Account mAccount;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAccount = createSyncAccount(this);
        handleIntent(getIntent());

        ImageView backgroundImage = (ImageView)findViewById(R.id.imageframe);
        backgroundImage.setImageResource(R.drawable.rothkoyello);

        new FacebookInitAsync().execute();

        // register content observers
        getContentResolver().registerContentObserver(
                Uri.parse(CardContentProvider.BASE_URI_STRING), // base uri w/o API-specific path
                true,                                           // notify for API-specific paths
                new CardContentObserver(new Handler())
        );

        // set up array of placeholder card data for use in adapter
        mCardsData = new ArrayList<>(ITEM_COUNT);
        mCardsData.add(new CardData(CardType.Weather));                     // index 0
        mCardsData.add(new CardData(CardType.News));                        // index 1
        mCardsData.add(new CardData(CardType.Facebook));                    // index 2
        mCardsData.add(new RemindersCardData(CardType.Reminders, null));    // index 3
        mCardsData.add(new MtaStatusCardData(CardType.MtaStatus));          // index 4

        // set up recycler view & adapter
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(MainActivity.this);
        mRecyclerView.setLayoutManager(manager);

        mAdapter = new RecyclerAdapter(mCardsData);
        mRecyclerView.setAdapter(mAdapter);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        // initialize data for recycler view
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        new InitRecyclerDataAsyncTask().execute();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // set up syncing
        ContentResolver.setIsSyncable(mAccount, CardContentProvider.AUTHORITY, 1);
        ContentResolver.setMasterSyncAutomatically(true);
        ContentResolver.setSyncAutomatically(mAccount, CardContentProvider.AUTHORITY, true);
        ContentResolver.addPeriodicSync(mAccount, CardContentProvider.AUTHORITY, Bundle.EMPTY, SYNC_INTERVAL);

        // if activity started from reminder notification, scroll to reminders
        if (getIntent().getBooleanExtra(ReminderService.SCROLL_TO_REMINDERS, false)) {
            mRecyclerView.getLayoutManager().scrollToPosition(REMINDERS_POSITION);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        ContentResolver.cancelSync(mAccount, CardContentProvider.AUTHORITY);
        ContentResolver.setIsSyncable(mAccount, CardContentProvider.AUTHORITY, 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        RecyclerAdapter.mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Toast.makeText(MainActivity.this, "Query: " + query, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        WebView webView = (WebView) findViewById(R.id.mtaWebView);
        if (webView != null && webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    public void refreshWebView() {
        WebView webView = (WebView) findViewById(R.id.mtaWebView);
        if (webView != null) {
            webView.loadUrl(((MtaStatusCardData) mCardsData.get(MTA_STATUS_POSITION)).getWidgetUrl());
        }
    }

    @Override
    public void onRefresh() {
        Log.d(TAG, "onRefresh: handle swipe refresh gesture");

        // request manual sync
        Bundle settingsBundle = new Bundle();
        settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        ContentResolver.requestSync(mAccount, CardContentProvider.AUTHORITY, settingsBundle);

        //TODO - this causes the refresh animation to start - it's stopped in PullFromDbAsync onPostExecute
        // but if device is offline, that never runs, so animation never stops
    }

    @Override
    public void onReminderSubmitted(Reminder reminder) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.REMINDERS_COL_TEXT, reminder.getReminderText());

        Long alarmTime = reminder.getDateTimeInMillis();
        if (alarmTime > 0) {
            values.put(DatabaseHelper.REMINDERS_COL_WHEN, alarmTime);
            setNotificationAlarm(alarmTime, reminder.getReminderText());
        }
        getContentResolver().insert(CardContentProvider.REMINDERS_URI, values);
    }

    public void setNotificationAlarm(Long alarmTimeInMillis, String message) {
        Intent intent = new Intent(this, ReminderService.class);
        intent.putExtra(DatabaseHelper.REMINDERS_COL_TEXT, message);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC, alarmTimeInMillis, pendingIntent);
        Toast.makeText(MainActivity.this, "Alarm set", Toast.LENGTH_SHORT).show();
    }

    public static Account createSyncAccount(Context context) {
        Account newAccount = new Account(
                context.getString(R.string.account),
                context.getString(R.string.account_type)
        );

        AccountManager accountManager =
                (AccountManager) context.getSystemService(ACCOUNT_SERVICE);

        if (accountManager.addAccountExplicitly(newAccount, null, null)) {
            Log.d(TAG, "createSyncAccount: successful");
        } else {
            Log.d(TAG, "createSyncAccount: failed");
        }
        return newAccount;
    }

    public class CardContentObserver extends ContentObserver {
        private final String TAG = CardContentObserver.class.getCanonicalName();

        public CardContentObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            int uriType = CardContentProvider.sUriMatcher.match(uri);

            switch (uriType) {
                case CardContentProvider.FACEBOOK: {
                    Log.d(TAG, "onChange: facebook");
                    new PullFromDbAsyncTask().execute(CardType.Facebook);
                    break;
                }
                case CardContentProvider.NEWS: {
                    Log.d(TAG, "onChange: news");
                    new PullFromDbAsyncTask().execute(CardType.News);
                    break;
                }
                case CardContentProvider.WEATHER: {
                    Log.d(TAG, "onChange: weather");
                    new PullFromDbAsyncTask().execute(CardType.Weather);
                    break;
                }
                case CardContentProvider.REMINDERS: {
                    Log.d(TAG, "onChange: reminders");
                    new PullFromDbAsyncTask().execute(CardType.Reminders);
                    break;
                }
                case CardContentProvider.REMINDERS_ID: {
                    Log.d(TAG, "onChange: reminders/id");
                    new PullFromDbAsyncTask().execute(CardType.Reminders);
                    break;
                }
                default:
                    break;
            }
            refreshWebView();
        }
    }

    private class PullFromDbAsyncTask extends AsyncTask<CardType, Void, CardType> {
        private final String TAG = PullFromDbAsyncTask.class.getCanonicalName();

        private CardType mCardType;

        @Override
        protected CardType doInBackground(CardType... params) {
            if ((mCardType = params[0]) == null) {
                cancel(true);
            }

            Cursor cursor;

            switch (mCardType) {
                case Facebook: {
                    Log.d(TAG, "doInBackground: query facebook");
                    cursor = getContentResolver().query(CardContentProvider.FACEBOOK_URI, null, null, null, null);
                    break;
                }
                case News: {
                    Log.d(TAG, "doInBackground: query news");
                    cursor = getContentResolver().query(CardContentProvider.NEWS_URI, null, null, null, null);
                    break;
                }
                case Weather: {
                    Log.d(TAG, "doInBackground: query weather");
                    cursor = getContentResolver().query(CardContentProvider.WEATHER_URI, null, null, null, null);
                    break;
                }
                case Reminders: {
                    Log.d(TAG, "doInBackground: query reminders");
                    cursor = getContentResolver().query(CardContentProvider.REMINDERS_URI, null, null, null, null);
                    break;
                }
                default:
                    cursor = null;
                    break;
            }
            updateCardDataArrayFromCursor(mCardType, cursor);
            return mCardType;
        }

        @Override
        protected void onPostExecute(CardType cardType) {
            super.onPostExecute(cardType);

            switch (cardType) {
                case Facebook:
                    mAdapter.notifyItemChanged(FACEBOOK_POSITION);
                    break;
                case News:
                    mAdapter.notifyItemChanged(NEWS_POSITION);
                    break;
                case Weather:
                    mAdapter.notifyItemChanged(WEATHER_POSITION);
                    break;
                case Reminders:
                    mAdapter.notifyItemChanged(REMINDERS_POSITION);
                    break;
                default:
                    break;
            }
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    private void updateCardDataArrayFromCursor(CardType cardType, Cursor cursor) {
        if (cursor != null && cursor.moveToFirst()) {
            switch (cardType) {
                case Facebook: {
                    FacebookCardData facebookCardData = new FacebookCardData(CardType.Facebook, cursor);
                    mCardsData.set(FACEBOOK_POSITION, facebookCardData);
                    break;
                }
                case News: {
                    NewsCardData newsCardData = new NewsCardData(CardType.News, cursor);
                    mCardsData.set(NEWS_POSITION, newsCardData);
                    break;
                }
                case Weather: {
                    WeatherCardData weatherCardData = new WeatherCardData(
                            CardType.Weather,
                            cursor.getInt(cursor.getColumnIndex(DatabaseHelper.WEATHER_COL_HIGH)),
                            cursor.getInt(cursor.getColumnIndex(DatabaseHelper.WEATHER_COL_LOW))
                    );
                    mCardsData.set(WEATHER_POSITION, weatherCardData);
                    break;
                }
                case Reminders: {
                    RemindersCardData remCardData = new RemindersCardData(CardType.Reminders, cursor);
                    mCardsData.set(REMINDERS_POSITION, remCardData);
                    break;
                }
                default:
                    return;
            }
            cursor.close();
        }
    }

    public class InitRecyclerDataAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            // pull most recent data from database
            updateCardDataArrayFromCursor(
                    CardType.Facebook,
                    getContentResolver().query(CardContentProvider.FACEBOOK_URI, null, null, null, null)
            );

            updateCardDataArrayFromCursor(
                    CardType.News,
                    getContentResolver().query(CardContentProvider.NEWS_URI, null, null, null, null)
            );

            updateCardDataArrayFromCursor(
                    CardType.Weather,
                    getContentResolver().query(CardContentProvider.WEATHER_URI, null, null, null, null)
            );

            updateCardDataArrayFromCursor(
                    CardType.Reminders,
                    getContentResolver().query(CardContentProvider.REMINDERS_URI, null, null, null, null)
            );

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mRecyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    public class FacebookInitAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            FacebookSdk.sdkInitialize(getApplicationContext());
            return null;
        }
    }
}
