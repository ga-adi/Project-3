package com.example.android.lately.Cards;

/**
 * Created by perrycooperman on 3/8/16.
 */
public class EventCard extends ParentCard {
    String time;
    String group;
    String event;
    String numberOfPeople;

    public EventCard(String time, String group, String event, String numberOfPeople, int cardType, int tabType, int id) {
        super(cardType, tabType, id);
        this.time = time;
        this.group = group;
        this.event = event;
        this.numberOfPeople = numberOfPeople;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(String numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }



}
