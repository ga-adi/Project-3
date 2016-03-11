package com.example.project3vice.vice_classes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Todo on 3/6/2016.
 */
public class ViceArticleDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "VICE_ARTICLE_DB";
    public static final String TABLE_NAME = "ARTICLES";

    public static final String COL_DB_ID = "_id";
    public static final String COL_TITLE = "TITLE";
    public static final String COL_PREVIEW = "PREVIEW";
    private static final String COL_BODY = "BODY";
    private static final String COL_FEED_TEXT = "FEED_TEXT";
    private static final String COL_SERIES_TITLE = "SERIES_TITLE";
    private static final String COL_SERIES_DESC = "SERIES_DESCRIPTION";
    public static final String COL_ARTICLE_ID = "ID";
    private static final String COL_SERIES_ID = "SERIES_ID";
    private static final String COL_EPISODE_NUMBER = "EPISODE_NUMBER";
    private static final String COL_PART = "PART";
    private static final String COL_URL = "URL";
    private static final String COL_AUTHOR = "AUTHOR";
    public static final String COL_PUB_DATE = "PUB_DATE";
    private static final String COL_PUB_TIMESTAMP = "PUB_TIMESTAMP";
    public static final String COL_CATEGORY = "CATEGORY";
    public static final String COL_TAGS = "TAGS";
    private static final String COL_NSFW = "NSFW";
    private static final String COL_NSFB = "NSFB";
    private static final String COL_THUMB = "THUMB";
    public static final String COL_IMAGE = "IMAGE";

    public static final String[] TABLE_COLUMNS = {COL_DB_ID,COL_TITLE,COL_PREVIEW,COL_BODY,COL_FEED_TEXT,
            COL_SERIES_TITLE,COL_SERIES_DESC,COL_ARTICLE_ID,COL_SERIES_ID,COL_EPISODE_NUMBER,COL_PART,
            COL_URL,COL_AUTHOR,COL_PUB_DATE,COL_PUB_TIMESTAMP,COL_CATEGORY,COL_TAGS,COL_NSFW,COL_NSFB,
            COL_THUMB,COL_IMAGE};

    private static final String CREATE_VICE_ARTICLE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
                    COL_DB_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_TITLE + " TEXT, " + COL_PREVIEW + " TEXT, " + COL_BODY + " TEXT, " + COL_FEED_TEXT + " TEXT, " +
                    COL_SERIES_TITLE + " TEXT, " + COL_SERIES_DESC + " TEXT, " + COL_ARTICLE_ID + " INTEGER, " +
                    COL_SERIES_ID + " INTEGER, " + COL_EPISODE_NUMBER + " INTEGER, " + COL_PART + " INTEGER, " +
                    COL_URL + " TEXT, " + COL_AUTHOR + " TEXT, " + COL_PUB_DATE + " TEXT, " + COL_PUB_TIMESTAMP  + " INTEGER, " +
                    COL_CATEGORY + " TEXT, " + COL_TAGS + " TEXT, " + COL_NSFW + " INTEGER, " + COL_NSFB + " INTEGER, " + COL_THUMB + " TEXT, " + COL_IMAGE + " TEXT)";

    //Singleton database instance
    private static ViceArticleDBHelper mInstance;

    //Singleton database constructor set to private + code to populate database
    private ViceArticleDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Method to retrieve Singleton database uses application context in constructor (since
    // individual activity contexts are not applicable)
    public static ViceArticleDBHelper getInstance (Context context){
        if(mInstance == null){
            mInstance = new ViceArticleDBHelper(context.getApplicationContext());
        }
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_VICE_ARTICLE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    //Method to return a cursor populated with the full list of articles stored
    public Cursor getListOfArticlesStored(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,TABLE_COLUMNS,null,null,null,null,null,null);
        return cursor;
    }

    //Method to create & add a new article reference to the database
    public long addArticleToDatabase(String pTitle, String pImage, String pPubDate, long pArticleID, String pCategory){
        ContentValues values = new ContentValues();
        values.put(COL_TITLE, pTitle);
        values.put(COL_IMAGE, pImage);
        values.put(COL_PUB_DATE, pPubDate);
        values.put(COL_ARTICLE_ID, pArticleID);
        values.put(COL_CATEGORY, pCategory);
        SQLiteDatabase db = this.getWritableDatabase();
        long returnId = db.insert(TABLE_NAME, null, values);
        db.close();
        return returnId;
    }

    //Method to add a new article reference to the database
    public long addArticleToDatabase(ContentValues values){
        SQLiteDatabase db = this.getWritableDatabase();
        long returnId = db.insert(TABLE_NAME, null, values);
        db.close();
        return returnId;
    }


    //Delete an article from the database via DatabaseID
    public int deleteArticleFromDatabase(int pDatabaseID){
        SQLiteDatabase db = getWritableDatabase();
        int deleteNum = db.delete(TABLE_NAME,
                COL_DB_ID + " = ?",
                new String[]{String.valueOf(pDatabaseID)});
        db.close();
        return deleteNum;
    }

    //Delete an article from the database via Vice's ArticleID
    public int deleteArticleFromDatabase(long pViceArticleID){
        SQLiteDatabase db = getWritableDatabase();
        int deleteNum = db.delete(TABLE_NAME,
                COL_ARTICLE_ID + " = ?",
                new String[]{String.valueOf(pViceArticleID)});
        db.close();
        return deleteNum;
    }

    //Search for an article by database id
    public Cursor searchForArticleByID(int query){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, TABLE_COLUMNS, COL_DB_ID + " = " + query,
                null, null, null, null, null);
        return cursor;
    }

    //Search for an article by Vice's article id
    public Cursor searchForArticleByID(long query){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, TABLE_COLUMNS, COL_ARTICLE_ID + " = " + query,
                null, null, null, null, null);
        return cursor;
    }

    //Search for an article by title
    public Cursor searchForArticleByTitle(String query){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, TABLE_COLUMNS, COL_TITLE + " LIKE ?",
                new String[]{"%" + query + "%"}, null, null, null, null);
        return cursor;
    }

    //Search for an article by category
    public Cursor searchForArticleByCategory(String query){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, TABLE_COLUMNS, COL_CATEGORY + " LIKE ?",
                new String[]{"%" + query + "%"}, null, null, null, null);
        return cursor;
    }

    //Search for an article by pubDate
    public Cursor searchForArticleByDate(String query){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, TABLE_COLUMNS, COL_PUB_DATE + " LIKE ?",
                new String[]{"%" + query + "%"}, null, null, null, null);
        return cursor;
    }

    //Search for an article by Title, Category, OR Date
    public Cursor searchForArticleByTitleCategoryDate(String query){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,TABLE_COLUMNS,COL_TITLE + " LIKE "+ "'%" + query + "%'"
                        +" OR " + COL_CATEGORY + " LIKE "+ "'%" + query + "%'"
                        +" OR " + COL_PUB_DATE + " LIKE "+ "'%" + query + "%'",
                null,null,null,null,null);
        return cursor;
    }

    public void clearDatabase(){
        SQLiteDatabase db = this.getReadableDatabase();
        String clearDBQuery = "DELETE FROM "+TABLE_NAME;
        db.execSQL(clearDBQuery);
    }
}
