package com.example.android.lately.Foursquare.FoursquarePhotos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Wasabi on 3/10/2016.
 */
public class Photo {

    @SerializedName("prefix")
    @Expose
    private String prefix;
    @SerializedName("suffix")
    @Expose
    private String suffix;

    /**
     *
     * @return
     * The prefix
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     *
     * @param prefix
     * The prefix
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    /**
     *
     * @return
     * The suffix
     */
    public String getSuffix() {
        return suffix;
    }

    /**
     *
     * @param suffix
     * The suffix
     */
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

}
