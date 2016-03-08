package com.charlesdrews.hud.Twitter;

import com.charlesdrews.hud.CardData;
import com.charlesdrews.hud.CardType;

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
