package com.charlesdrews.hud.Weather;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncResult;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.charlesdrews.hud.DatabaseHelper;

/**
 * Created by charlie on 3/7/16.
 */
public class WeatherSyncAdapter extends AbstractThreadedSyncAdapter {
    ContentResolver mContentResolver;

    public WeatherSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        mContentResolver = context.getContentResolver();
    }

    public WeatherSyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        mContentResolver = context.getContentResolver();
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        //TODO - use Retrofit to make API calls & parse responses
        //TODO - and use content provider to save data in database

        Log.d(WeatherSyncAdapter.class.getCanonicalName(), "Starting sync...");

        //testing
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.WEATHER_COL_HIGH, 57);
        values.put(DatabaseHelper.WEATHER_COL_LOW, 42);

        Uri uri = WeatherContentProvider.CONTENT_URI;
        mContentResolver.insert(uri, values);
    }
}
