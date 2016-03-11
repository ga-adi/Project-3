package com.example.android.lately.Meetup.MeetupPhotos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Member;

/**
 * Created by Wasabi on 3/10/2016.
 */
public class Result {


    @SerializedName("utc_offset")
    @Expose
    private Integer utcOffset;
    @SerializedName("site_link")
    @Expose
    private String siteLink;
    @SerializedName("highres_link")
    @Expose
    private String highresLink;
    @SerializedName("photo_id")
    @Expose
    private Integer photoId;
    @SerializedName("created")
    @Expose
    private Integer created;
    @SerializedName("caption")
    @Expose
    private String caption;
    @SerializedName("photo_link")
    @Expose
    private String photoLink;
    @SerializedName("updated")
    @Expose
    private Integer updated;
    @SerializedName("thumb_link")
    @Expose
    private String thumbLink;

    /**
     *
     * @return
     * The utcOffset
     */
    public Integer getUtcOffset() {
        return utcOffset;
    }

    /**
     *
     * @param utcOffset
     * The utc_offset
     */
    public void setUtcOffset(Integer utcOffset) {
        this.utcOffset = utcOffset;
    }

    /**
     *
     * @return
     * The siteLink
     */
    public String getSiteLink() {
        return siteLink;
    }

    /**
     *
     * @param siteLink
     * The site_link
     */
    public void setSiteLink(String siteLink) {
        this.siteLink = siteLink;
    }

    /**
     *
     * @return
     * The highresLink
     */
    public String getHighresLink() {
        return highresLink;
    }

    /**
     *
     * @param highresLink
     * The highres_link
     */
    public void setHighresLink(String highresLink) {
        this.highresLink = highresLink;
    }

    /**
     *
     * @return
     * The photoId
     */
    public Integer getPhotoId() {
        return photoId;
    }

    /**
     *
     * @param photoId
     * The photo_id
     */
    public void setPhotoId(Integer photoId) {
        this.photoId = photoId;
    }

    /**
     *
     * @return
     * The created
     */
    public Integer getCreated() {
        return created;
    }

    /**
     *
     * @param created
     * The created
     */
    public void setCreated(Integer created) {
        this.created = created;
    }

    /**
     *
     * @return
     * The member
     */
    /**
     *
     * @return
     * The caption
     */
    public String getCaption() {
        return caption;
    }

    /**
     *
     * @param caption
     * The caption
     */
    public void setCaption(String caption) {
        this.caption = caption;
    }

    /**
     *
     * @return
     * The photoLink
     */
    public String getPhotoLink() {
        return photoLink;
    }

    /**
     *
     * @param photoLink
     * The photo_link
     */
    public void setPhotoLink(String photoLink) {
        this.photoLink = photoLink;
    }

    /**
     *
     * @return
     * The updated
     */
    public Integer getUpdated() {
        return updated;
    }

    /**
     *
     * @param updated
     * The updated
     */
    public void setUpdated(Integer updated) {
        this.updated = updated;
    }

    /**
     *
     * @return
     * The thumbLink
     */
    public String getThumbLink() {
        return thumbLink;
    }

    /**
     *
     * @param thumbLink
     * The thumb_link
     */
    public void setThumbLink(String thumbLink) {
        this.thumbLink = thumbLink;
    }
}
