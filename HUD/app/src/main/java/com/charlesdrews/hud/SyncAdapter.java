package com.charlesdrews.hud;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import android.util.Log;


/**
 * Created by charlie on 3/9/16.
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter {
    private static final String TAG = SyncAdapter.class.getCanonicalName();

    private ContentResolver mContentResolver;

    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        mContentResolver = context.getContentResolver();
    }

    public SyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        mContentResolver = context.getContentResolver();
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        Log.d(TAG, "onPerformSync: starting");

        Log.d(TAG, "onPerformSync: insert facebook");
        mContentResolver.insert(CardContentProvider.FACEBOOK_URI, getFacebookData());

        Log.d(TAG, "onPerformSync: insert news");
        mContentResolver.insert(CardContentProvider.NEWS_URI, getNewsData());

        Log.d(TAG, "onPerformSync: insert weather");
        mContentResolver.insert(CardContentProvider.WEATHER_URI, getWeatherData());
    }

    public ContentValues getFacebookData() {
        // TODO - make the Facebook API call, parse the response, and create
        // TODO   a new ContentValues object with values for each column in the database

        // manual test values
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.FACEBOOK_COL_AUTHOR, "Kyle");
        values.put(DatabaseHelper.FACEBOOK_COL_STATUS_UPDATE, "Boo Twitter! Yay Facebook!");
        return values;
    }

    public ContentValues getNewsData() {
        // TODO - make the news API call, parse the response, and create
        // TODO   a new ContentValues object with values for each column in the database

        // manual test values
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.NEWS_COL_HEADLINE, "Aliens attacking; seek shelter");
        return values;
    }

    public ContentValues getWeatherData() {
        // TODO - make the weather API call, parse the response, and create
        // TODO   a new ContentValues object with values for each column in the database

        // manual test values
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.WEATHER_COL_HIGH, 57);
        values.put(DatabaseHelper.WEATHER_COL_LOW, 42);
        return values;
    }
}
