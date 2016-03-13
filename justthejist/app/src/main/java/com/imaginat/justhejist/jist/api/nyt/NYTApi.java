package com.imaginat.justhejist.jist.api.nyt;

import com.imaginat.justhejist.jist.models.NewsStory;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Copyright 2016 Boloutare Doubeni
 *
 * Contains the code for making the API to the times api
 */
public class NYTApi {

  /*package*/ static final String API_KEY =
      "56fdb568f4650663376b28f5493fa58d:5:71410972";
  public static final String BASE_URL = "http://api.nytimes.com/svc/";

  public static List<NewsStory> getStories(String section) throws IOException {
    Retrofit retrofit = new Retrofit.Builder().baseUrl(NYTApi.BASE_URL).build();
    NYTService service = retrofit.create(NYTService.class);
    Call<ResponseBody> call = service.getTopStories(section);
    ResponseBody body = call.execute().body();
    String json = body.string();
    return JSONParser.getStoriesFrom(json);
  }

  private NYTApi() {
    throw new AssertionError("This should not be instantiated");
  }
}
