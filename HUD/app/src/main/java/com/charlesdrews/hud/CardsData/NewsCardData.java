package com.charlesdrews.hud.CardsData;

import android.database.Cursor;

import com.charlesdrews.hud.DatabaseHelper;

import java.util.ArrayList;

/**
 * Contains a list of news items (headling, link, thumbnail) to show in the news card
 * Created by charlie on 3/7/16.
 */
public class NewsCardData extends CardData {
    private ArrayList<NewsItemData> mNewsItems;

    public NewsCardData(CardType type, Cursor cursor) {
        super(type);

        mNewsItems = new ArrayList<>(cursor.getCount());
        while (cursor.moveToNext()) {
            mNewsItems.add(new NewsItemData(
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.NEWS_COL_HEADLINE)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.NEWS_COL_LINK_URL)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.NEWS_COL_THUMBNAIL_URL))
            ));
        }
    }

    public ArrayList<NewsItemData> getNewsItems() { return mNewsItems; }

    public class NewsItemData {
        private String mHeadline, mLinkUrl, mThumbnailUrl;

        public NewsItemData(String headline, String linkUrl, String thumbnailUrl) {
            mHeadline = headline;
            mLinkUrl = linkUrl;
            mThumbnailUrl = thumbnailUrl;
        }

        public String getHeadline() { return mHeadline; }

        public String getLinkUrl() { return mLinkUrl; }

        public String getThumbnailUrl() { return mThumbnailUrl; }
    }
}
