package com.charlesdrews.hud.HudCardData;

/**
 * Created by charlie on 3/7/16.
 */
public class WeatherCardData extends CardData {
    private int mHighTemp, mLowTemp;

    public WeatherCardData(CardType type, int highTemp, int lowTemp) {
        super(type);
        mHighTemp = highTemp;
        mLowTemp = lowTemp;
    }

    public int getHighTemp() {
        return mHighTemp;
    }

    public int getLowTemp() {
        return mLowTemp;
    }
}
