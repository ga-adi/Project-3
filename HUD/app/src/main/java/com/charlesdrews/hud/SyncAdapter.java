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

import com.charlesdrews.hud.NYTimesTop.NYTimesAPIResult;
import com.charlesdrews.hud.NYTimesTop.Result;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Make API calls, parse responses, and store data via the content provider
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
        getFacebookData();

        Log.d(TAG, "onPerformSync: insert news");
        getNewsData();


        Log.d(TAG, "onPerformSync: insert weather");
        mContentResolver.insert(CardContentProvider.WEATHER_URI, getWeatherData());
        mContentResolver.notifyChange(CardContentProvider.WEATHER_URI, null);
    }

    public void getFacebookData() {
        // TODO - make the Facebook API call, parse the response, and create
        // TODO   a new ContentValues object with values for each column in the database

        //TODO {1370430319_10206094192418409} is my latest post

        if (RecyclerAdapter.mIsLoggedInToFacebook) {
            new GraphRequest(
                    AccessToken.getCurrentAccessToken(),
                    //TODO "/me?fields=id,name,posts",
                    "/me?fields=posts",
                    null,
                    HttpMethod.GET,
                    new GraphRequest.Callback() {
                        public void onCompleted(GraphResponse response) {
                            JSONObject facebookObject = response.getJSONObject();

                        }
                    }
            ).executeAsync();
        }

        // manual test values
        mContentResolver.delete(CardContentProvider.FACEBOOK_URI, null, null); // clear table
        for (int i = 1; i < 6; i++) {
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.FACEBOOK_COL_AUTHOR, "Facebook friend #" + i);
            values.put(DatabaseHelper.FACEBOOK_COL_STATUS_UPDATE, "This is a status update...");
            mContentResolver.insert(CardContentProvider.FACEBOOK_URI, values);
        }
        mContentResolver.notifyChange(CardContentProvider.FACEBOOK_URI, null);
    }

    public void getNewsData() {
        NYTimesAPI.Factory.getInstance().getTopNYTimes().enqueue(new Callback<NYTimesAPIResult>() {

            @Override
            public void onResponse(Call<NYTimesAPIResult> call, Response<NYTimesAPIResult> response) {
                List<Result> results = response.body().getResults();

                if (results != null && results.size() > 0) {
                    // first, clear out the saved news data
                    mContentResolver.delete(CardContentProvider.NEWS_URI, null, null);

                    // then add new values from results
                    for (Result result : results) {
                        ContentValues values = new ContentValues();
                        values.put(DatabaseHelper.NEWS_COL_HEADLINE, result.getTitle());
                        values.put(DatabaseHelper.NEWS_COL_LINK_URL, result.getUrl());
                        if (result.getThumbnailStandard().length() > 1) {
                            values.put(DatabaseHelper.NEWS_COL_THUMBNAIL_URL, result.getThumbnailStandard());
                        }

                        mContentResolver.insert(CardContentProvider.NEWS_URI, values);
                    }

                    // notify the ContentObserver in MainActivity
                    mContentResolver.notifyChange(CardContentProvider.NEWS_URI, null);
                } else {
                    Log.d(TAG, "onFailure: error using the NYT Top Stories API");
                }
            }

            @Override
            public void onFailure(Call<NYTimesAPIResult> call, Throwable t) {
                Log.d(TAG, "onFailure: error using the NYT Top Stories API");
            }
        });
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
