package com.example.android.lately.Meetup;

/**
 * Created by Wasabi on 3/10/2016.
 */
public class Group {

    private String joinMode;
    private Long created;
    private String name;
    private Double groupLon;
    private Long id;
    private String urlname;
    private Double groupLat;
    private String who;

    /**
     *
     * @return
     * The joinMode
     */
    public String getJoinMode() {
        return joinMode;
    }

    /**
     *
     * @param joinMode
     * The join_mode
     */
    public void setJoinMode(String joinMode) {
        this.joinMode = joinMode;
    }

    /**
     *
     * @return
     * The created
     */
    public Long getCreated() {
        return created;
    }

    /**
     *
     * @param created
     * The created
     */
    public void setCreated(Long created) {
        this.created = created;
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
     * The groupLon
     */
    public Double getGroupLon() {
        return groupLon;
    }

    /**
     *
     * @param groupLon
     * The group_lon
     */
    public void setGroupLon(Double groupLon) {
        this.groupLon = groupLon;
    }

    /**
     *
     * @return
     * The id
     */
    public Long getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The urlname
     */
    public String getUrlname() {
        return urlname;
    }

    /**
     *
     * @param urlname
     * The urlname
     */
    public void setUrlname(String urlname) {
        this.urlname = urlname;
    }

    /**
     *
     * @return
     * The groupLat
     */
    public Double getGroupLat() {
        return groupLat;
    }

    /**
     *
     * @param groupLat
     * The group_lat
     */
    public void setGroupLat(Double groupLat) {
        this.groupLat = groupLat;
    }

    /**
     *
     * @return
     * The who
     */
    public String getWho() {
        return who;
    }

    /**
     *
     * @param who
     * The who
     */
    public void setWho(String who) {
        this.who = who;
    }
}
