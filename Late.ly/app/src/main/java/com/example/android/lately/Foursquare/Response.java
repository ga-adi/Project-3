package com.example.android.lately.Foursquare;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wasabi on 3/10/2016.
 */
public class Response {

    @SerializedName("venues")
    @Expose
    private List<Venue> venues = new ArrayList<Venue>();

    /**
     *
     * @return
     * The venues
     */
    public List<Venue> getVenues() {
        return venues;
    }

    /**
     *
     * @param venues
     * The venues
     */
    public void setVenues(List<Venue> venues) {
        this.venues = venues;
    }
}
