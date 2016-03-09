package com.charlesdrews.hud.CardsData;

/**
 * Created by charlie on 3/7/16.
 */
public class NewsCardData extends CardData {
    //TODO - feel free to change this around - this was just for testing
    private String mHeadline;

    public NewsCardData(CardType type, String headline) {
        super(type);
        mHeadline = headline;
    }

    public String getHeadline() {
        return mHeadline;
    }
}
