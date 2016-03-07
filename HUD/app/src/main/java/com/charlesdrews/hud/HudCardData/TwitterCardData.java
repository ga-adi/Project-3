package com.charlesdrews.hud.HudCardData;

/**
 * Created by charlie on 3/7/16.
 */
public class TwitterCardData extends CardData {
    private String mAuthor, mTweetText;

    public TwitterCardData(CardType type, String author, String tweetText) {
        super(type);
        mAuthor = author;
        mTweetText = tweetText;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getTweetText() {
        return mTweetText;
    }
}
