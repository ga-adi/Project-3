package com.example.android.lately.Cards;

/**
 * Created by Wasabi on 3/9/2016.
 */
public class RedditComment {
    String mAuthor;
    String mContent;
    String mTime;
    int mScore;

    public RedditComment(String mAuthor, String mContent, int mScore, String mTime) {
        this.mAuthor = mAuthor;
        this.mContent = mContent;
        this.mScore = mScore;
        this.mTime = mTime;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public void setmAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    public int getmScore() {
        return mScore;
    }

    public void setmScore(int mScore) {
        this.mScore = mScore;
    }

    public String getmTime() {
        return mTime;
    }

    public void setmTime(String mTime) {
        this.mTime = mTime;
    }
}
