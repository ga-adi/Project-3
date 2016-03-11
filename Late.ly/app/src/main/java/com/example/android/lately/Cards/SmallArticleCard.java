package com.example.android.lately.Cards;

/**
 * Created by ShowMe on 3/8/16.
 */
public class SmallArticleCard extends ParentCard {

    String mSource;
    String mTitle;
    String mTime;


    public SmallArticleCard(String mSource, String mTitle, String mTime, int cardType, int tabType, int id) {
        super(cardType, tabType, id);
        this.mSource = mSource;
        this.mTitle = mTitle;
        this.mTime = mTime;
    }

    //to do: add images


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
