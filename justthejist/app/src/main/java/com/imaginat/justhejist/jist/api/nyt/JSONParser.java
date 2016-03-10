package com.imaginat.justhejist.jist.api.nyt;

import com.imaginat.justhejist.jist.models.Medium;
import com.imaginat.justhejist.jist.models.NewsStory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright 2016 Boloutare Doubeni
 */
public class JSONParser {

  private static final String RESULTS = "results";
  private static final String TITLE = "title";
  private static final String AUTHOR = "byline";
  private static final String SUMMARY = "abstract";
  private static final String MEDIA = "multimedia";
  private static final String SECTION = "section";
  private static final String URL = "url";

  private static final String  CAPTION = "caption";
  private static final String HEIGHT = "height";
  private static final String WIDTH = "width";
  private static final String MEDIA_TYPE = "format";

  private JSONParser() { throw new AssertionError(); }

  public static List<NewsStory> getStoriesFrom(String json) {
    List<NewsStory> stories = new ArrayList<>();
    try {
      JSONObject jsonEntity = new JSONObject(json);
      JSONArray results = jsonEntity.getJSONArray(RESULTS);
      for (int i = 0; i < results.length(); ++i) {
        ArrayList<Medium> media = new ArrayList<>();
        JSONObject result = results.getJSONObject(i);
        NewsStory.Builder storyBuilder = new NewsStory.Builder();
        Object mediaEntry =  result.get(MEDIA);
        if (mediaEntry instanceof JSONArray) {
          JSONArray mediaArray = (JSONArray) mediaEntry;
          for (int j = 0; j < mediaArray.length(); ++j) {
            JSONObject mediumEntry = mediaArray.getJSONObject(j);
            Medium.Builder mediumBuilder = new Medium.Builder();
            Medium medium = mediumBuilder
                .dimensions(mediumEntry.getDouble(HEIGHT), mediumEntry.getDouble(WIDTH))
                .url(mediumEntry.getString(URL))
                .mediaType(mediumEntry.getString(MEDIA_TYPE))
                .caption(mediumEntry.getString(CAPTION))
                .build();

            media.add(medium);
          }
        }
        NewsStory story = storyBuilder.title(result.getString(TITLE))
                              .author(result.getString(AUTHOR))
                              .summary(result.getString(SUMMARY))
                              .url(result.getString(URL))
                              .section(result.getString(SECTION))
                              .multimedia(media)
                              .build();
        stories.add(story);
      }
    } catch (JSONException e) {
      e.printStackTrace();
      return null;
    }
    return stories;
  }
}
