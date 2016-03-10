package com.example.android.lately.Cards;

/**
 * Created by ShowMe on 3/8/16.
 */
public class ParentCard {
    int mCardType;
    int mId;
    int mTabType;

    public ParentCard(int mCardType, int mTabType, int mId) {
        this.mCardType = mCardType;
        this.mId = mId;
        this.mTabType = mTabType;

    }

    public int getmTabType() {
        return mTabType;
    }

    public void setmTabType(int mTabType) {
        this.mTabType = mTabType;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public int getmCardType() {
        return mCardType;
    }

    public void setmCardType(int mCardType) {
        this.mCardType = mCardType;
    }
}
