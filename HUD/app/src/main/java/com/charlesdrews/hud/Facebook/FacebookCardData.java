package com.charlesdrews.hud.Facebook;

import com.charlesdrews.hud.CardData;
import com.charlesdrews.hud.CardType;

/**
 * Created by charlie on 3/7/16.
 */
public class FacebookCardData extends CardData {
    //TODO - feel free to change this around - this was just for testing
    private String mAuthor, mStatusUpdateText;

    public FacebookCardData(CardType type, String author, String statusUpdateText) {
        super(type);
        mAuthor = author;
        mStatusUpdateText = statusUpdateText;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getStatusUpdate() {
        return mStatusUpdateText;
    }
}
