package com.charlesdrews.hud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.charlesdrews.hud.Weather.WeatherCardData;

/**
 * Store results from API calls in a local database for offline availability
 * Created by charlie on 3/8/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "hud_db.db";

    //TODO - replace with actual column names - these are just placehoders

    public static final String WEATHER_TABLE = "weather";
    public static final String WEATHER_COL_ID = "_id";
    public static final String WEATHER_COL_HIGH = "high_temp";
    public static final String WEATHER_COL_LOW = "low_temp";
    public static final String[] WEATHER_COLUMNS = new String[]{WEATHER_COL_ID, WEATHER_COL_HIGH, WEATHER_COL_LOW};
    public static final String WEATHER_CREATE = "CREATE TABLE " + WEATHER_TABLE + " ("
            + WEATHER_COL_ID + " INTEGER PRIMARY KEY, "
            + WEATHER_COL_HIGH + " INTEGER,"
            + WEATHER_COL_LOW + " INTEGER)";

    public static final String NEWS_TABLE = "news";
    public static final String NEWS_COL_ID = "_id";
    public static final String NEWS_COL_HEADLINE = "headline";
    public static final String[] NEWS_COLUMNS = new String[]{NEWS_COL_ID, NEWS_COL_HEADLINE};
    public static final String NEWS_CREATE = "CREATE TABLE " + NEWS_TABLE + " ("
            + NEWS_COL_ID + " INTEGER PRIMARY KEY, "
            + NEWS_COL_HEADLINE + " TEXT)";

    public static final String FACEBOOK_TABLE = "facebook";
    public static final String FACEBOOK_COL_ID = "_id";
    public static final String FACEBOOK_COL_AUTHOR = "author";
    public static final String FACEBOOK_COL_STATUS_UPDATE = "status_update";
    public static final String[] FACEBOOK_COLUMNS = new String[]{FACEBOOK_COL_ID, FACEBOOK_COL_AUTHOR, FACEBOOK_COL_STATUS_UPDATE};
    public static final String FACEBOOK_CREATE = "CREATE TABLE " + FACEBOOK_TABLE + " ("
            + FACEBOOK_COL_ID + " INTEGER PRIMARY KEY, "
            + FACEBOOK_COL_AUTHOR + " TEXT,"
            + FACEBOOK_COL_STATUS_UPDATE + " TEXT)";

    private static DatabaseHelper mInstance;

    public static DatabaseHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DatabaseHelper(context.getApplicationContext(), null);
        }
        return mInstance;
    }

    private DatabaseHelper(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(WEATHER_CREATE);
        db.execSQL(NEWS_CREATE);
        db.execSQL(FACEBOOK_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + WEATHER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + NEWS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + FACEBOOK_TABLE);
    }

    public long addWeather(ContentValues values) {
        Log.d(DatabaseHelper.class.getCanonicalName(), "Starting addWeather...");

        SQLiteDatabase db = getWritableDatabase();
        //TODO - check if values contains the correct keys
        long rowId = db.insert(WEATHER_TABLE, null, values);
        db.close();
        return rowId;
    }

    public Cursor getWeather() {
        Log.d(DatabaseHelper.class.getCanonicalName(), "Getting weather...");

        SQLiteDatabase db = getReadableDatabase();
        //TODO - consider adding an "insert time" field and order desc by that
        Cursor cursor = db.query(
                WEATHER_TABLE,              // table
                WEATHER_COLUMNS,            // columns
                null, null, null, null,     // selection, selectionArgs, groupBy, having
                WEATHER_COL_ID + " DESC"    // orderBy
        );
        return cursor;
    }

    public long addNews(ContentValues values) {
        Log.d(DatabaseHelper.class.getCanonicalName(), "Starting addNews...");

        SQLiteDatabase db = getWritableDatabase();
        //TODO - check if values contains the correct keys
        long rowId = db.insert(NEWS_TABLE, null, values);
        db.close();
        return rowId;
    }

    public Cursor getNews() {
        Log.d(DatabaseHelper.class.getCanonicalName(), "Getting news...");

        SQLiteDatabase db = getReadableDatabase();
        //TODO - consider adding an "insert time" field and order desc by that
        Cursor cursor = db.query(
                NEWS_TABLE,             // table
                NEWS_COLUMNS,           // columns
                null, null, null, null, // selection, selectionArgs, groupBy, having
                NEWS_COL_ID + " DESC"   // orderBy
        );
        return cursor;
    }
}
