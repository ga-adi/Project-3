package com.example.project3vice.SyncClasses;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;
import com.example.project3vice.vice_classes.ViceArticleDBHelper;

/**
 * Created by Todo on 3/7/2016.
 */
public class ViceArticleContentProvider extends ContentProvider {
    private static final String AUTHORITY = "com.example.project3vice.SyncClasses.ViceArticleContentProvider";
    private static final String VICE_ARTICLE_TABLE = ViceArticleDBHelper.TABLE_NAME;
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + VICE_ARTICLE_TABLE);
    public static final int ARTICLES = 1;
    public static final int ARTICLE_ID = 2;
    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private ViceArticleDBHelper dbHelper;

    static {
        sURIMatcher.addURI(AUTHORITY, VICE_ARTICLE_TABLE, ARTICLES);
        sURIMatcher.addURI(AUTHORITY, VICE_ARTICLE_TABLE + "/#", ARTICLE_ID);
    }

    @Override
    public boolean onCreate() {
        dbHelper = ViceArticleDBHelper.getInstance(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        int uriType = sURIMatcher.match(uri);

        Cursor cursor;

        switch (uriType) {
            case ARTICLES:
                cursor = dbHelper.getListOfArticlesStored();
                break;
            default:
                throw new IllegalArgumentException("Unknown URI");
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;

    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = sURIMatcher.match(uri);

        long id = 0;
        switch (uriType) {
            case ARTICLES:
                id = dbHelper.addArticleToDatabase(values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(CONTENT_URI + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);

        switch (uriType) {
            case ARTICLES:
                dbHelper.clearDatabase();
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
