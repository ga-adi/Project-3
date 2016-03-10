package com.example.android.lately.Foursquare.FoursquarePhotos;

import com.example.android.lately.Foursquare.Meta;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Wasabi on 3/10/2016.
 */
public class FoursquarePhotos {
    @SerializedName("response")
    @Expose
    private Response response;


    /**
     *
     * @return
     * The response
     */
    public Response getResponse() {
        return response;
    }

    /**
     *
     * @param response
     * The response
     */
    public void setResponse(Response response) {
        this.response = response;
    }
}
