package com.charlesdrews.hud.NYTimesSearch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by williamtygret on 3/8/16.
 */
public class NYTimesAPIResponse {

    @SerializedName("response")
    @Expose
    private NYTimesSearchResponse response;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("copyright")
    @Expose
    private String copyright;

    /**
     *
     * @return
     * The response
     */
    public NYTimesSearchResponse getResponse() {
        return response;
    }

    /**
     *
     * @param response
     * The response
     */
    public void setResponse(NYTimesSearchResponse response) {
        this.response = response;
    }

    /**
     *
     * @return
     * The status
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     *
     * @return
     * The copyright
     */
    public String getCopyright() {
        return copyright;
    }

    /**
     *
     * @param copyright
     * The copyright
     */
    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }


}
