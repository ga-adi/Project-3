package com.example.android.lately.Cards;

import org.w3c.dom.Comment;

import java.util.ArrayList;

/**
 * Created by ShowMe on 3/8/16.
 */
public class LargeArticleCard extends ParentCard {
    String mSource;
    String mTitle;
    String mTime;


    public LargeArticleCard(String mSource, String mTitle, String mTime, int mCardType) {
        super(mCardType);
        this.mSource = mSource;
        this.mTitle = mTitle;
        this.mTime = mTime;
    }

    public String getmSource() {
        return mSource;
    }

    public void setmSource(String mSource) {
        this.mSource = mSource;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmTime() {
        return mTime;
    }

    public void setmTime(String mTime) {
        this.mTime = mTime;
    }
}
