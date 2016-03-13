package com.imaginat.justhejist.jist.api.nyt;

/**
 * Copyright 2016 Boloutare Doubeni
 */
public class MediaOptions {

  public static final String THUMBNAIL = "Standard Thumbnail";
  public static final String THUMBNAIL_LARGE = "thumbLarge";
  public static final String NORMAL = "Normal";
  public static final String MEDIUM = "mediumThreeByTwo210";
  public static final String JUMBO = "superJumbo";

  private MediaOptions() {
    throw new AssertionError("This should not be instantiated");
  }
}
