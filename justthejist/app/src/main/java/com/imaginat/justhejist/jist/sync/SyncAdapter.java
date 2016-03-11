package com.imaginat.justhejist.jist.sync;

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

import com.imaginat.justhejist.jist.DBHelper.TopStoryDBHelper;

/**
 * Created by nat on 3/10/16.
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter {

    // Global variables
    // Define a variable to contain a content resolver instance
    ContentResolver mContentResolver;

    /**
     * Set up the sync adapter
     */
    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        /*
         * If your app uses a content resolver, get an instance of it
         * from the incoming Context
         */
        mContentResolver = context.getContentResolver();
    }



    /**
     * Set up the sync adapter. This form of the
     * constructor maintains compatibility with Android 3.0
     * and later platform versions
     */
    public SyncAdapter(
            Context context,
            boolean autoInitialize,
            boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        /*
         * If your app uses a content resolver, get an instance of it
         * from the incoming Context
         */
        mContentResolver = context.getContentResolver();

    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        Log.d("SyncAdapter", "onPerformSync called");
        ContentValues values = new ContentValues();
        values.put(TopStoryDBHelper.COL_SECTION, "section");
        values.put(TopStoryDBHelper.COL_SUBSECTION, "subsection");
        values.put(TopStoryDBHelper.COL_ABSTRACT, "abstract");
        values.put(TopStoryDBHelper.COL_BYLINE, "byline");
        values.put(TopStoryDBHelper.COL_UPDATE, "update");
        values.put(TopStoryDBHelper.COL_CREATED, "created");
        values.put(TopStoryDBHelper.COL_TITLE, "title");
        values.put(TopStoryDBHelper.COL_PUBLISHED, "published");
        values.put(TopStoryDBHelper.COL_KEYWORDS, "keywords");
        values.put(TopStoryDBHelper.COL_MULTIMEDIA, "multimedia");
        values.put(TopStoryDBHelper.COL_URL, "url");
        //Uri.parse("com.imaginat.justhejist.jist.sync.StubProvider")
        //StubProvider.CONTENT_URI
        mContentResolver.insert(Uri.parse("content://com.imaginat.justhejist.jist.sync.StubProvider"), values);
        Log.d("SyncAdapter", "just called insert");
    }
}