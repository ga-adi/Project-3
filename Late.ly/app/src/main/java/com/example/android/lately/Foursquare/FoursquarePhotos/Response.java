package com.example.android.lately.Foursquare.FoursquarePhotos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Wasabi on 3/10/2016.
 */
public class Response {

    @SerializedName("photos")
    @Expose
    private Photos photos;

    /**
     *
     * @return
     * The photos
     */
    public Photos getPhotos() {
        return photos;
    }

    /**
     *
     * @param photos
     * The photos
     */
    public void setPhotos(Photos photos) {
        this.photos = photos;
    }

}
