package com.example.android.lately.Foursquare;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Wasabi on 3/10/2016.
 */
public class Meta {
    @SerializedName("code")
    @Expose
    private Long code;

    /**
     *
     * @return
     * The code
     */
    public Long getCode() {
        return code;
    }

    /**
     *
     * @param code
     * The code
     */
    public void setCode(Long code) {
        this.code = code;
    }
}
