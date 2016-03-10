package com.imaginat.justhejist.jist.models;

import android.util.Pair;

/**
 * Copyright 2016 Boloutare Doubeni
 */
public class Medium {
  private final String mCaption;
  private final double mHeight;
  private final double mWidth;
  private final String mUrl;
  private final String mMediaType;

  private Medium(String caption, double height, double width, String url, String mediaType) {
    mCaption = caption;
    mHeight = height;
    mWidth = width;
    mUrl = url;
    mMediaType = mediaType;
  }

  public String getCaption() {
    return mCaption;
  }


  public Pair<Double, Double> getDimensions() {
    return  new Pair<>(mHeight, mWidth);
  }

  public String getUrl() {
    return mUrl;
  }

  public String getMediaType() {
    return mMediaType;
  }

  public static class Builder {
    private String mCaption;
    private double mHeight;
    private double mWidth;
    private String mUrl;
    private String mMediaType;

    public Builder() {}

    public Builder caption(String caption) {
      mCaption = caption;
      return this;
    }

    public Builder dimensions(double height, double width) {
      mHeight = height;
      mWidth = width;
      return this;
    }

    public Builder url(String url) {
      mUrl = url;
      return this;
    }

    public Builder mediaType(String mediaType) {
      mMediaType = mediaType;
      return this;
    }

    public Medium build() {
      return new Medium(mCaption, mHeight, mWidth, mUrl, mMediaType);
    }
  }
}
