package com.charlesdrews.hud.News;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.charlesdrews.hud.DatabaseHelper;

/**
 * Manage reads/writes to/from database
 * Created by charlie on 3/7/16.
 */
public class NewsContentProvider extends ContentProvider {
    public static final String AUTHORITY = "com.charlesdrews.hud.News.NewsContentProvider";
    public static final String PATH = DatabaseHelper.NEWS_TABLE;
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + PATH);
    public static final String ERR_MSG_UNKNOWN_URI = "Unknown URI: ";

    public static final int NEWS = 1;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sUriMatcher.addURI(AUTHORITY, PATH, NEWS);
    }
    private DatabaseHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = DatabaseHelper.getInstance(getContext());
        return (mDbHelper != null);
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        int uriType = sUriMatcher.match(uri);
        Cursor cursor;

        switch (uriType) {
            case NEWS:
                cursor = mDbHelper.getNews();
                break;
            default:
                throw new IllegalArgumentException(ERR_MSG_UNKNOWN_URI + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }
    /*
     * insert() always returns null (no URI)
     */
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.d(NewsContentProvider.class.getCanonicalName(), "Starting insert...");

        int uriType = sUriMatcher.match(uri);
        long id;

        switch (uriType) {
            case NEWS:
                id = mDbHelper.addNews(values);
                break;
            default:
                throw new IllegalArgumentException(ERR_MSG_UNKNOWN_URI + uri);
        }

        //TODO - check if id == -1 which indicates db error

        Log.d(NewsContentProvider.class.getCanonicalName(), "Notifying content resolver...");

        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.withAppendedPath(CONTENT_URI, String.valueOf(id));
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        //TODO
        return 0;
    }

    public int update(
            Uri uri,
            ContentValues values,
            String selection,
            String[] selectionArgs) {
        //TODO
        return 0;
    }
}
