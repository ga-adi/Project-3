package com.imaginat.justhejist.jist.api.nyt;

import com.imaginat.justhejist.jist.api.nyt.gson.TopStoriesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Copyright 2016 Boloutare Doubeni
 */
public interface NYTService {

  @GET("topstories/v1/{section}.json?api-key=" + NYTApi.API_KEY)
  Call<TopStoriesResponse> listResults(@Path("section") String section);
}
