package com.example.project3vice.vice_classes;

/**
 * Created by gregorydaly on 3/9/16.
 */
public class Video{
    private String duration;
    private String video_duration;
    private String ooyalaId;
    private String thrid_party_video_id;
    private String video_type;

    public Video(){}

    public String getDuration() {
        return duration;
    }

    public String getVideo_duration() {
        return video_duration;
    }

    public String getOoyalaId() {
        return ooyalaId;
    }

    public String getThrid_party_video_id() {
        return thrid_party_video_id;
    }

    public String getVideo_type() {
        return video_type;
    }

    public void setOoyalaId(String id){
        this.ooyalaId = id;
    }
}
