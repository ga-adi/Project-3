package com.example.android.lately.Cards;

/**
 * Created by Wasabi on 3/11/2016.
 */
public class FacebookCard extends ParentCard{
    String username;
    String createdTime;
    String message;
    String imageUrl;

    public FacebookCard(String createdTime, String imageUrl, String message, String username, int mCardType, int mTabType, int mId) {
        super(mCardType, mTabType, mId);
        this.createdTime = createdTime;
        this.imageUrl = imageUrl;
        this.message = message;
        this.username = username;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
