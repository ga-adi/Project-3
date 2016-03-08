package com.charlesdrews.hud.Weather;

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
public class WeatherContentProvider extends ContentProvider {
    public static final String AUTHORITY = "com.charlesdrews.hud.Weather.WeatherContentProvider";
    public static final String PATH = DatabaseHelper.WEATHER_TABLE;
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + PATH);
    public static final String ERR_MSG_UNKNOWN_URI = "Unknown URI: ";

    public static final int WEATHER = 1;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sUriMatcher.addURI(AUTHORITY, PATH, WEATHER);
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
            case WEATHER:
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

        Log.d(WeatherContentProvider.class.getCanonicalName(), "Starting insert...");

        int uriType = sUriMatcher.match(uri);
        long id;

        switch (uriType) {
            case WEATHER:
                id = mDbHelper.addWeather(values);
                break;
            default:
                throw new IllegalArgumentException(ERR_MSG_UNKNOWN_URI + uri);
        }

        //TODO - check if id == -1 which indicates db error

        Log.d(WeatherContentProvider.class.getCanonicalName(), "Notifying content resolver...");

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
