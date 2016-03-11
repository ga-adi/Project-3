package com.charlesdrews.hud;

/**
 * Created by Kyle McNee on 3/11/2016.
 */
public class FacebookPost {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
