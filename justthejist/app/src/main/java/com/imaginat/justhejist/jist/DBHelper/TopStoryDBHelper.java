package com.imaginat.justhejist.jist.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by generalassembly on 3/8/16.
 */
public class TopStoryDBHelper extends SQLiteOpenHelper {
  public static final String DATABASE_NAME = "top_story";
  public static final String TABLE_NAME = "top_story_table";
  public static final int DATABASE_VERSION = 2;

  public static final String COL_ID = "_id";
  public static final String COL_SECTION = "section";
  public static final String COL_SUBSECTION = "subsection";
  public static final String COL_ABSTRACT = "abstract";
  public static final String COL_BYLINE = "byline";
  public static final String COL_UPDATE = "updated";
  public static final String COL_CREATED = "created";
  public static final String COL_TITLE = "title";
  public static final String COL_PUBLISHED = "published";
  public static final String COL_KEYWORDS = "keywords";
  public static final String COL_MULTIMEDIA = "multimedia";
  public static final String COL_URL = "url";
  private static final String[] All_COLUMNS =
      new String[] {COL_ID,        COL_SECTION,  COL_SUBSECTION, COL_ABSTRACT,
                    COL_BYLINE,    COL_UPDATE,   COL_CREATED,    COL_TITLE,
                    COL_PUBLISHED, COL_KEYWORDS, COL_MULTIMEDIA, COL_URL};

  public static String[] getAllColumns() { return All_COLUMNS; }

  public static final String CREATE =
      "CREATE TABLE " + TABLE_NAME + " (" + COL_ID +
      " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_SECTION + " TEXT, " +
      COL_SUBSECTION + " TEXT, " + COL_ABSTRACT + " TEXT, " + COL_BYLINE +
      " TEXT, " + COL_UPDATE + " TEXT, " + COL_CREATED + " TEXT, " + COL_TITLE +
      " TEXT, " + COL_PUBLISHED + " TEXT, " + COL_KEYWORDS + " TEXT, " +
      COL_MULTIMEDIA + " TEXT, " + COL_URL + " TEXT)";

  private TopStoryDBHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }
  // constructor in singleton should be private

  private static TopStoryDBHelper Instance = null;

  public static TopStoryDBHelper getInstance(Context context) {
    if (Instance == null) {
      Instance = new TopStoryDBHelper(context);
      Log.d("dude", "getInstance: " + CREATE);
    }

    return Instance;
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    Log.d("dude", "before the creation");
    db.execSQL(CREATE);
    Log.d("TopStoryDBHelper", "onCreate " + CREATE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    onCreate(db);
  }

  public long addArticle(ContentValues values) {
    SQLiteDatabase db = this.getWritableDatabase();
    long rowAdded = db.insert(TABLE_NAME, null, values);
    db.close();
    return rowAdded;
  }

  public Cursor getAllArticles() {
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor =
        db.query(TABLE_NAME, All_COLUMNS, null, null, null, null, null, null);

    return cursor;
  }

  public Cursor getArticlesById(String id) {
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.query(TABLE_NAME, All_COLUMNS, COL_ID + " = ?",
                             new String[] {id}, null, null, null, null);

    return cursor;
  }

  public int updateArticlesById(String id, ContentValues values) {
    SQLiteDatabase db = this.getWritableDatabase();
    int rowsChanged =
        db.update(TABLE_NAME, values, COL_ID + " = ?", new String[] {id});
    db.close();

    return rowsChanged;
  }

  public void addArticletoDB(Context context) {

    ContentValues values = new ContentValues();
    values.put(COL_SECTION, "sexy");
    values.put(COL_SUBSECTION, "sexy");
    values.put(COL_ABSTRACT, "sexy");
    values.put(COL_BYLINE, "sexy");
    values.put(COL_UPDATE, "sexy");
    values.put(COL_CREATED, "sexy");
    values.put(COL_TITLE, "sexy");
    values.put(COL_PUBLISHED, "sexy");
    values.put(COL_KEYWORDS, "sexy");
    values.put(COL_MULTIMEDIA, "sexy");
    values.put(COL_URL, "sexy");

    addArticle(values);
    Toast.makeText(context, "DB ammended", Toast.LENGTH_LONG).show();
  }

  public Cursor searchArticlesListByTitle(String query) {
    SQLiteDatabase db = this.getReadableDatabase();

    Cursor cursorSearch =
        db.query(TABLE_NAME,             // a. table
                 All_COLUMNS,            // b. column names
                 COL_TITLE + " LIKE ? ", // c. selections
                 new String[] {"%" + query + "%", "%" + query + "%",
                               "%" + query + "%"}, // d. selections args
                 null,                             // e. group by
                 null,                             // f. having
                 null,                             // g. order by
                 null);                            // h. limit

    return cursorSearch;
  }

  public Cursor searchArticlesListByKeywords(String query) {
    SQLiteDatabase db = this.getReadableDatabase();

    Cursor cursorSearch =
        db.query(TABLE_NAME,                       // a. table
                 All_COLUMNS,                      // b. column names
                 COL_KEYWORDS + " LIKE ?",         // c. selections
                 new String[] {"%" + query + "%"}, // d. selections args
                 null,                             // e. group by
                 null,                             // f. having
                 null,                             // g. order by
                 null);                            // h. limit

    return cursorSearch;
  }

  public Cursor searchArticlesListBySection(String query) {
    SQLiteDatabase db = this.getReadableDatabase();

    Cursor cursorSearch =
        db.query(TABLE_NAME,              // a. table
                 All_COLUMNS,             // b. column names
                 COL_SECTION + " LIKE ?", // c. selections
                 new String[] {"%" + query + "%", "%" + query + "%",
                               "%" + query + "%"}, // d. selections args
                 null,                             // e. group by
                 null,                             // f. having
                 null,                             // g. order by
                 null);                            // h. limit

    return cursorSearch;
  }
}
