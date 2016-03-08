package com.charlesdrews.hud;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by williamtygret on 3/8/16.
 */
public interface NYTimesAPI {

    String search = "new+york";
    String BASE_URL = "http://api.nytimes.com/svc/";

    @GET("search/v2/articlesearch.json?q=" + search + "&page=1&sort=newest&api-key=29975101513df1dfdf9895c3324ca6d2:6:74605150")
    Call <NYTimesAPIResponse> getNYTimes();

    class Factory {

        private static NYTimesAPI service;

        public static NYTimesAPI getInstance() {
            if (service == null) {
                Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(BASE_URL).build();

                service = retrofit.create(NYTimesAPI.class);
                return service;

            } else {
                return service;
            }

        }

    }
}
