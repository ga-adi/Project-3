package com.example.android.lately.Cards;

import java.util.ArrayList;

/**
 * Created by Wasabi on 3/11/2016.
 */
public class FoursquareCard extends ParentCard {

    String venueId;
    String venueName;
    String venueAddress;
    String venueUrl;
    String venuePhotoUrl;

    public FoursquareCard(String venueAddress, String venueId, String venueName, String venueUrl, String venuePhotoUrl,int mCardType, int mTabType, int mId) {
        super(mCardType, mTabType, mId);
        this.venueAddress = venueAddress;
        this.venueId = venueId;
        this.venueName = venueName;
        this.venueUrl = venueUrl;
        this.venuePhotoUrl = venuePhotoUrl;
    }

    public String getVenuePhotoUrl() {
        return venuePhotoUrl;
    }

    public void setVenuePhotoUrl(String venuePhotoUrl) {
        this.venuePhotoUrl = venuePhotoUrl;
    }

    public String getVenueAddress() {
        return venueAddress;
    }

    public void setVenueAddress(String venueAddress) {
        this.venueAddress = venueAddress;
    }

    public String getVenueId() {
        return venueId;
    }

    public void setVenueId(String venueId) {
        this.venueId = venueId;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public String getVenueUrl() {
        return venueUrl;
    }

    public void setVenueUrl(String venueUrl) {
        this.venueUrl = venueUrl;
    }
}
