package com.example.android.lately.Cards;

import java.util.ArrayList;

/**
 * Created by Wasabi on 3/9/2016.
 */
public class RedditCard extends ParentCard{

    String mTitle;
    String mTime;
    String mContent;
    String mAuthor;
    ArrayList<RedditComment> mComments;
    int mScore;
    int mNumOfComment;
    String mSubreddit;
    String mUrl;

    public RedditCard(String mAuthor, ArrayList<RedditComment> mComments, String mContent, int mNumOfComment, int mScore, String mSubreddit, String mTime, String mTitle, String mUrl, int cardType) {
        super(cardType);
        this.mAuthor = mAuthor;
        this.mComments = mComments;
        this.mContent = mContent;
        this.mNumOfComment = mNumOfComment;
        this.mScore = mScore;
        this.mSubreddit = mSubreddit;
        this.mTime = mTime;
        this.mTitle = mTitle;
        this.mUrl = mUrl;
    }

    public String getmUrl() {
        return mUrl;
    }

    public void setmUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public int getmNumOfComment() {
        return mNumOfComment;
    }

    public void setmNumOfComment(int mNumOfComment) {
        this.mNumOfComment = mNumOfComment;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public void setmAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }

    public ArrayList<RedditComment> getmComments() {
        return mComments;
    }

    public void setmComments(ArrayList<RedditComment> mComments) {
        this.mComments = mComments;
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

    public String getmSubreddit() {
        return mSubreddit;
    }

    public void setmSubreddit(String mSubreddit) {
        this.mSubreddit = mSubreddit;
    }

    public String getmTime() {
        return mTime;
    }

    public void setmTime(String mTime) {
        this.mTime = mTime;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }
}
