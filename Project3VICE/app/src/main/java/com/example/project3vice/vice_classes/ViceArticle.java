package com.example.project3vice.vice_classes;

/**
 * Created by Todo on 3/6/2016.
 */
public class ViceArticle {
    private String title;
    private String preview;
    private String[] tags;
    private String body;
    private String feedText;
    private String seriesTitle;
    private String seriesDescription;
    private long id;
    private long seriesId;
    private long episodeNumber;
    private long part;
    private String url;
    private String author;
    private String pubDate;
    private long pubTimestamp;
    private String category;
    private Contributor contributor;
    private boolean nsfw;
    private boolean nsfb;
    private String thumb;
    private String image;
    private String thumb_10_4;
    private String thumb_10_3;
    private String thumb_16_9;
    private String thumb_7_10;
    private Media media;
    private String otherParts;
    private String dnd;

    public class Contributor{
        private String id;
        private String slug;
        private String email;
        private String username;
        private String firstname;
        private String lastname;
        private String jobtitle;
        private String twitter;
        private String facebook;
        private String bio;
        private String avatar;
        private String google_plus_url;
        private String image_path;
        private String image_file_name;
        private String image_crop_path;
        private String image_width;
        private String image_height;
    }

    //constructor
    public ViceArticle() {}

    //toString
    @Override
    public String toString() {
        return title;
    }

    //Getters & Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getFeedText() {
        return feedText;
    }

    public void setFeedText(String feedText) {
        this.feedText = feedText;
    }

    public String getSeriesTitle() {
        return seriesTitle;
    }

    public void setSeriesTitle(String seriesTitle) {
        this.seriesTitle = seriesTitle;
    }

    public String getSeriesDescription() {
        return seriesDescription;
    }

    public void setSeriesDescription(String seriesDescription) {
        this.seriesDescription = seriesDescription;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(long seriesId) {
        this.seriesId = seriesId;
    }

    public long getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(long episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public long getPart() {
        return part;
    }

    public void setPart(long part) {
        this.part = part;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public long getPubTimestamp() {
        return pubTimestamp;
    }

    public void setPubTimestamp(long pubTimestamp) {
        this.pubTimestamp = pubTimestamp;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Contributor getContributor() {
        return contributor;
    }

    public void setContributor(Contributor contributor) {
        this.contributor = contributor;
    }

    public boolean isNsfw() {
        return nsfw;
    }

    public void setNsfw(boolean nsfw) {
        this.nsfw = nsfw;
    }

    public boolean isNsfb() {
        return nsfb;
    }

    public void setNsfb(boolean nsfb) {
        this.nsfb = nsfb;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getThumb_10_4() {
        return thumb_10_4;
    }

    public void setThumb_10_4(String thumb_10_4) {
        this.thumb_10_4 = thumb_10_4;
    }

    public String getThumb_10_3() {
        return thumb_10_3;
    }

    public void setThumb_10_3(String thumb_10_3) {
        this.thumb_10_3 = thumb_10_3;
    }

    public String getThumb_16_9() {
        return thumb_16_9;
    }

    public void setThumb_16_9(String thumb_16_9) {
        this.thumb_16_9 = thumb_16_9;
    }

    public String getThumb_7_10() {
        return thumb_7_10;
    }

    public void setThumb_7_10(String thumb_7_10) {
        this.thumb_7_10 = thumb_7_10;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public String getOtherParts() {
        return otherParts;
    }

    public void setOtherParts(String otherParts) {
        this.otherParts = otherParts;
    }

    public String getDnd() {
        return dnd;
    }

    public void setDnd(String dnd) {
        this.dnd = dnd;
    }
}
