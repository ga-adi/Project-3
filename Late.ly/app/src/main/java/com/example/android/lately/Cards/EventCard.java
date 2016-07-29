package com.example.android.lately.Cards;

/**
 * Created by perrycooperman on 3/8/16.
 */
public class EventCard extends ParentCard {
    String time;
    String group;
    String event;
    String numberOfPeople;
    String eventDescription;
    String venueAddress;
    String venueName;
    String eventUrl;

    public EventCard(String event, String eventDescription, String eventUrl, String group, String numberOfPeople, String time, String venueAddress, String venueName, int mCardType, int mTabType, int mId) {
        super(mCardType, mTabType, mId);
        this.event = event;
        this.eventDescription = eventDescription;
        this.eventUrl = eventUrl;
        this.group = group;
        this.numberOfPeople = numberOfPeople;
        this.time = time;
        this.venueAddress = venueAddress;
        this.venueName = venueName;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getEventUrl() {
        return eventUrl;
    }

    public void setEventUrl(String eventUrl) {
        this.eventUrl = eventUrl;
    }

    public String getVenueAddress() {
        return venueAddress;
    }

    public void setVenueAddress(String venueAddress) {
        this.venueAddress = venueAddress;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
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
