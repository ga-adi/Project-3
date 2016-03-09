package com.charlesdrews.hud.CardsData;

/**
 * Base class for card data - extend this and add fields specific to each type of card
 * Created by charlie on 3/7/16.
 */
public class CardData {
    private CardType mType;

    public CardData(CardType type) {
        mType = type;
    }

    public CardType getType() {
        return mType;
    }
}
