package com.imaginat.justhejist.jist.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Copyright 2016 Boloutare Doubeni
 *
 * A class that holds the news story data for the main view.
 *
 * TODO(boloutaredoubeni): add the multimedia
 */
public class NewsStory {

  private final String mSummary;
  private final String mAuthor;
  private final String mTitle;
  private final String mUrl;
  private final String mSection;
  private List<Medium> mMedia;
  private Set<String> mKeywords;

  private NewsStory(String title, String author, String summary, String url,
                    String section, Set<String> keywords, List<Medium> media) {
    mTitle = title;
    mAuthor = author;
    mSummary = summary;
    mUrl = url;
    mSection = section;
    mMedia = media;
  }

  public String getTitle() { return mTitle; }

  public String getAuthor() { return mAuthor; }

  public String getSummary() { return mSummary; }

  public String getUrl() { return mUrl; }

  public String getSection() { return mSection; }

  public List<Medium> getMedia() { return mMedia; }

  public Set<String> getKeywords() { return mKeywords; }

  public static class Builder {
    private String mAuthor;
    private String mSummary;
    private String mTitle;
    private String mUrl;
    private String mSection;
    private Set<String> mKeywords = new HashSet<>();
    private List<Medium> mMedia = new ArrayList<>();

    public Builder() {}

    public Builder author(String author) {
      mAuthor = author;
      return this;
    }

    public Builder summary(String summary) {
      mSummary = summary;
      return this;
    }

    public Builder title(String title) {
      mTitle = title;
      return this;
    }

    public Builder url(String url) {
      mUrl = url;
      return this;
    }

    public Builder section(String section) {
      mSection = section;
      return this;
    }

    public Builder multimedia(ArrayList<Medium> media) {
      mMedia.clear();
      mMedia.addAll(media);
      return this;
    }

    public Builder keyword(String word) {
      mKeywords.add(word);
      return this;
    }

    public Builder keyword(List<String> words) {
      mKeywords.addAll(words);
      return this;
    }

    public NewsStory build() {
      return new NewsStory(mTitle, mAuthor, mSummary, mUrl, mSection,
                           mKeywords, mMedia);
    }
  }
}
