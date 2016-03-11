package com.example.project3vice.SyncClasses;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;

import com.example.project3vice.vice_classes.ViceArticleDBHelper;

/**
 * Created by Todo on 3/7/2016.
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter {
    ContentResolver mResolver;
    Context mContext;

    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        mResolver = context.getContentResolver();
        mContext = context;
    }

    public SyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        mResolver = context.getContentResolver();
        mContext = context;
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        //code to add a dummy value to trigger the ContentObserver via sync
                ContentValues values = new ContentValues();
                values.put(ViceArticleDBHelper.COL_TITLE, "trigger");
                mResolver.insert(ViceArticleContentProvider.CONTENT_URI, values);
    }
}
