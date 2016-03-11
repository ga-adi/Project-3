package com.charlesdrews.hud.CardsData;

import android.database.Cursor;

import com.charlesdrews.hud.DatabaseHelper;

import java.util.ArrayList;

/**
 * Created by charlie on 3/7/16.
 */
public class FacebookCardData extends CardData {
    private ArrayList<FacebookItem> mFacebookItems;

    public FacebookCardData(CardType type, Cursor cursor) {
        super(type);
        mFacebookItems = new ArrayList<>(cursor.getCount());
        while (cursor.moveToNext()) {
            mFacebookItems.add(new FacebookItem(
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.FACEBOOK_COL_AUTHOR)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.FACEBOOK_COL_STATUS_UPDATE))
            ));
        }
    }

    public ArrayList<FacebookItem> getFacebookItems() { return mFacebookItems; }

    public class FacebookItem {
        private String mAuthor, mStatusUpdate;

        public FacebookItem(String author, String statusUpdate) {
            mAuthor = author;
            mStatusUpdate = statusUpdate;
        }

        public String getAuthor() { return mAuthor; }

        public String getStatusUpdate() { return mStatusUpdate; }
    }
}
