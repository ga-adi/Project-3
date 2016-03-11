package com.example.android.lately.Foursquare;

import com.google.android.gms.games.stats.Stats;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wasabi on 3/10/2016.
 */
public class Venue {

    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("verified")
    @Expose
    private Boolean verified;
    @SerializedName("stats")
    @Expose
    private Stats stats;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("hasMenu")
    @Expose
    private Boolean hasMenu;
    @SerializedName("menu")
    @Expose
    private Menu menu;
    @SerializedName("allowMenuUrlEdit")
    @Expose
    private Boolean allowMenuUrlEdit;
    @SerializedName("referralId")
    @Expose
    private String referralId;
    @SerializedName("venueChains")
    @Expose
    private List<Object> venueChains = new ArrayList<Object>();


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The contact
     */
    /**
     *
     * @return
     * The location
     */
    public Location getLocation() {
        return location;
    }

    /**
     *
     * @param location
     * The location
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     *
     * @return
     * The categories
     */

    /**
     *
     * @return
     * The verified
     */
    public Boolean getVerified() {
        return verified;
    }

    /**
     *
     * @param verified
     * The verified
     */
    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    /**
     *
     * @return
     * The stats
     */
    public Stats getStats() {
        return stats;
    }

    /**
     *
     * @param stats
     * The stats
     */
    public void setStats(Stats stats) {
        this.stats = stats;
    }

    /**
     *
     * @return
     * The url
     */
    public String getUrl() {
        return url;
    }

    /**
     *
     * @param url
     * The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     *
     * @return
     * The hasMenu
     */
    public Boolean getHasMenu() {
        return hasMenu;
    }

    /**
     *
     * @param hasMenu
     * The hasMenu
     */
    public void setHasMenu(Boolean hasMenu) {
        this.hasMenu = hasMenu;
    }

    /**
     *
     * @return
     * The delivery

    /**
     *
     * @return
     * The menu
     */
    public Menu getMenu() {
        return menu;
    }

    /**
     *
     * @param menu
     * The menu
     */
    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    /**
     *
     * @return
     * The allowMenuUrlEdit
     */
    public Boolean getAllowMenuUrlEdit() {
        return allowMenuUrlEdit;
    }

    /**
     *
     * @param allowMenuUrlEdit
     * The allowMenuUrlEdit
     */
    public void setAllowMenuUrlEdit(Boolean allowMenuUrlEdit) {
        this.allowMenuUrlEdit = allowMenuUrlEdit;
    }

    /**
     *
     * @return
     * The specials
     */
    public String getReferralId() {
        return referralId;
    }

    /**
     *
     * @param referralId
     * The referralId
     */
    public void setReferralId(String referralId) {
        this.referralId = referralId;
    }

    /**
     *
     * @return
     * The venueChains
     */
    public List<Object> getVenueChains() {
        return venueChains;
    }

    /**
     *
     * @param venueChains
     * The venueChains
     */
    public void setVenueChains(List<Object> venueChains) {
        this.venueChains = venueChains;
    }

}
