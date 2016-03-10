package com.charlesdrews.hud;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;


/**
 * Created by charlie on 3/9/16.
 */
public class CardContentProvider extends ContentProvider {
    private static final String TAG = CardContentProvider.class.getCanonicalName();

    public static final String AUTHORITY = "com.charlesdrews.hud.CardContentProvider";

    public static final String FACEBOOK_PATH = DatabaseHelper.FACEBOOK_TABLE;
    public static final String NEWS_PATH = DatabaseHelper.NEWS_TABLE;
    public static final String WEATHER_PATH = DatabaseHelper.WEATHER_TABLE;

    public static final String BASE_URI_STRING = "content://" + AUTHORITY;
    public static final Uri FACEBOOK_URI = Uri.parse(BASE_URI_STRING + "/" + FACEBOOK_PATH);
    public static final Uri NEWS_URI = Uri.parse(BASE_URI_STRING + "/" + NEWS_PATH);
    public static final Uri WEATHER_URI = Uri.parse(BASE_URI_STRING + "/" + WEATHER_PATH);

    public static final String ERR_MSG_UNKNOWN_URI = "Unknown URI: ";

    public static final int FACEBOOK = 1;
    public static final int NEWS = 2;
    public static final int WEATHER = 3;

    public static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sUriMatcher.addURI(AUTHORITY, FACEBOOK_PATH, FACEBOOK);
        sUriMatcher.addURI(AUTHORITY, NEWS_PATH, NEWS);
        sUriMatcher.addURI(AUTHORITY, WEATHER_PATH, WEATHER);
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
            case FACEBOOK:
                Log.d(TAG, "query: facebook");
                cursor = mDbHelper.getFacebook();
                break;
            
            case NEWS:
                Log.d(TAG, "query: news");
                cursor = mDbHelper.getNews();
                break;
            
            case WEATHER:
                Log.d(TAG, "query: weather");
                cursor = mDbHelper.getWeather();
                break;
            
            default:
                throw new IllegalArgumentException(ERR_MSG_UNKNOWN_URI + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = sUriMatcher.match(uri);
        long id;

        switch (uriType) {
            case FACEBOOK:
                Log.d(TAG, "insert: facebook");
                id = mDbHelper.addFacebook(values);
                break;

            case NEWS:
                Log.d(TAG, "insert: news");
                id = mDbHelper.addNews(values);
                break;

            case WEATHER:
                Log.d(TAG, "insert: weather");
                id = mDbHelper.addWeather(values);
                break;

            default:
                throw new IllegalArgumentException(ERR_MSG_UNKNOWN_URI + uri);
        }

        //TODO - check if id == -1 which indicates db error

        Log.d(TAG, "insert: notify content resolver");

        //getContext().getContentResolver().notifyChange(uri, null); // notify from sync adapter instead
        return Uri.withAppendedPath(uri, String.valueOf(id));
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = sUriMatcher.match(uri);
        int rowsAffected = 0;

        switch (uriType) {
            case FACEBOOK:
                Log.d(TAG, "insert: facebook");
                //TODO - need a db helper method for deleting facebook data
                break;

            case NEWS:
                Log.d(TAG, "insert: news");
                rowsAffected = mDbHelper.deleteAllNews();
                break;

            case WEATHER:
                Log.d(TAG, "insert: weather");
                //TODO - need a db helper method for deleting weather data
                break;

            default:
                throw new IllegalArgumentException(ERR_MSG_UNKNOWN_URI + uri);
        }

        //TODO - check if rowsAffected == -1 which indicates db error

        Log.d(TAG, "delete: notify content resolver");

        getContext().getContentResolver().notifyChange(uri, null);
        return rowsAffected;
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
