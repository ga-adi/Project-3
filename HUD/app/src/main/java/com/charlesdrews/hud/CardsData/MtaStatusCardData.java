package com.charlesdrews.hud.CardsData;

/**
 * Created by charlie on 3/10/16.
 */
public class MtaStatusCardData extends CardData {
    private static final String WIDGET_URL = "http://service.mta.info/ServiceStatus/status.html?widget=yes";

    public MtaStatusCardData(CardType type) {
        super(type);
    }

    public String getWidgetUrl() { return WIDGET_URL; }
}
