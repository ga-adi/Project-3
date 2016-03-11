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
 *
 * A series of methods to parse the JSON response
 */
public class JSONParser {

  private static final String RESULTS = "results";
  private static final String TITLE = "title";
  private static final String AUTHOR = "byline";
  private static final String SUMMARY = "abstract";
  private static final String MEDIA = "multimedia";
  private static final String SECTION = "section";
  private static final String URL = "url";
  private static final String DES_FACET = "des_facet";
  private static final String ORG_FACET = "org_facet";
  private static final String PER_FACET = "per_facet";
  private static final String GEO_FACET = "geo_facet";

  private static final String  CAPTION = "caption";
  private static final String HEIGHT = "height";
  private static final String WIDTH = "width";
  private static final String MEDIA_TYPE = "format";

  private static final String[] KEYWORDS = {DES_FACET, ORG_FACET, PER_FACET, GEO_FACET};

  private JSONParser() { throw new AssertionError(); }

  public static List<NewsStory> getStoriesFrom(String json) {
    try {
      JSONObject jsonEntity = new JSONObject(json);
      JSONArray results = jsonEntity.getJSONArray(RESULTS);
      return parseResults(results);
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return null;
  }

  private static List<NewsStory> parseResults(JSONArray results) throws JSONException {
    List<NewsStory> stories = new ArrayList<>();
    for (int i = 0; i < results.length(); ++i) {
      JSONObject result = results.getJSONObject(i);
      NewsStory.Builder storyBuilder = new NewsStory.Builder();
      Object mediaEntry =  result.get(MEDIA);

      NewsStory story = storyBuilder.title(result.getString(TITLE))
          .author(result.getString(AUTHOR))
          .summary(result.getString(SUMMARY))
          .url(result.getString(URL))
          .section(result.getString(SECTION))
          .keyword(parseKeywords(result))
          .multimedia((mediaEntry instanceof JSONArray) ? parseMultimedia((JSONArray) mediaEntry) : new ArrayList<Medium>())
          .build();
      stories.add(story);
    }
    return stories;
  }

  public  static List<Medium> parseMultimedia(JSONArray multimedia) throws JSONException {
    ArrayList<Medium> media = new ArrayList<>();
    for (int j = 0; j < multimedia.length(); ++j) {
      JSONObject mediumEntry = multimedia.getJSONObject(j);
      String json = mediumEntry.toString();
      Medium.Builder mediumBuilder = new Medium.Builder();
      Medium medium = mediumBuilder
          .dimensions(mediumEntry.getDouble(HEIGHT), mediumEntry.getDouble(WIDTH))
          .url(mediumEntry.getString(URL))
          .mediaType(mediumEntry.getString(MEDIA_TYPE))
          .caption(mediumEntry.getString(CAPTION))
          .json(json)
          .build();

      media.add(medium);
    }
    return media;
  }

  private static List<String> parseKeywords(JSONObject result)  {
    List<String> words = new ArrayList<>();
    try {

      for (String keyword : KEYWORDS) {
        Object jsonData = result.get(keyword);
        if (jsonData instanceof JSONArray) {
          JSONArray keywords = (JSONArray) jsonData;
          for (int i = 0; i <  keywords.length(); ++i) {
            words.add(keywords.getString(i));
          }
        }
      }
    } catch (JSONException e) {
      // Be more forgiving if keywords are not found
    }
    return words;
  }
}
