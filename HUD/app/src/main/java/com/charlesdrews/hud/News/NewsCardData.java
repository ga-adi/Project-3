package com.charlesdrews.hud.News;

import com.charlesdrews.hud.CardData;
import com.charlesdrews.hud.CardType;

/**
 * Created by charlie on 3/7/16.
 */
public class NewsCardData extends CardData {
    private String mHeadline;

    public NewsCardData(CardType type, String headline) {
        super(type);
        mHeadline = headline;
    }

    public String getHeadline() {
        return mHeadline;
    }
}
