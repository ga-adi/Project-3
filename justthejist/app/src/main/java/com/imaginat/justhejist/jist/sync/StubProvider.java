package com.imaginat.justhejist.jist.sync;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.imaginat.justhejist.jist.DBHelper.TopStoryDBHelper;

/**
 * Created by drewmahrt on 3/2/16.
 */
public class StubProvider extends ContentProvider {

  private static final String AUTHORITY = "com.imaginat.justthejist.jist.sync.StubProvider";
  private static final String NEWS_STORY_TABLE = TopStoryDBHelper.TABLE_NAME;
  public static final Uri CONTENT_URI = Uri.parse("content://"
          + AUTHORITY + "/" + NEWS_STORY_TABLE);

  public static final int ARTICLES=0;
  public static final int KEYWORDS = 1;
  public static final int TITLE = 2;
  public static final int CONTENT=3;
  public static final int ARTICLE_ID=4;

  private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

  static {
    sURIMatcher.addURI(AUTHORITY,NEWS_STORY_TABLE,ARTICLES);
    sURIMatcher.addURI(AUTHORITY, NEWS_STORY_TABLE + "/#", ARTICLE_ID);
  }

  private TopStoryDBHelper dbHelper;
  /*
   * Always return true, indicating that the
   * provider loaded correctly.
   */
  @Override
  public boolean onCreate() {
    dbHelper = TopStoryDBHelper.getInstance(getContext());
    return false;

  }
  /*
   * Return no type for MIME type
   */
  @Override
  public String getType(Uri uri) {
    return null;
  }
  /*
   * query() always returns no results
   *
   */
  @Override
  public Cursor query(Uri uri, String[] projection, String selection,
                      String[] selectionArgs, String sortOrder) {
    return null;
  }
  /*
   * insert() always returns null (no URI)
   */
  @Override
  public Uri insert(Uri uri, ContentValues values) {
    Log.d("StubProvider","Insider insert");
    long id = dbHelper.addArticle(values);
    /*int uriType = sURIMatcher.match(uri);

    long id = 0;
    switch (uriType) {
      case ARTICLES:
        id = dbHelper.addArticle(values);
        break;
      default:
        throw new IllegalArgumentException("Unknown URI: " + uri);
    }*/
    getContext().getContentResolver().notifyChange(uri, null);
    return Uri.parse(ARTICLES + "/" + id);

  }
  /*
   * delete() always returns "no rows affected" (0)
   */
  @Override
  public int delete(Uri uri, String selection, String[] selectionArgs) {
    return 0;
  }
  /*
   * update() always returns "no rows affected" (0)
   */
  public int update(Uri uri, ContentValues values, String selection,
                    String[] selectionArgs) {
    return 0;
  }
}