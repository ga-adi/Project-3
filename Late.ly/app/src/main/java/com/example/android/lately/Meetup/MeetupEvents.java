package com.example.android.lately.Meetup;

import com.example.android.lately.Foursquare.Meta;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wasabi on 3/10/2016.
 */
public class MeetupEvents {

    private List<Result> results = new ArrayList<Result>();

    /**
     *
     * @return
     * The results
     */
    public List<Result> getResults() {
        return results;
    }

    /**
     *
     * @param results
     * The results
     */
    public void setResults(List<Result> results) {
        this.results = results;
    }


}
