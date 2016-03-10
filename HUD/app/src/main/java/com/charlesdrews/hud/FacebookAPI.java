package com.charlesdrews.hud;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Kyle McNee on 3/10/2016.
 */
public interface FacebookAPI {

    //TODO put baseURL in
    String baseURL = "https://www.google.com";

    class factory {
        private static FacebookAPI service;

        public static FacebookAPI getInstance(){
            if (service == null){
                Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(baseURL).build();
                service = retrofit.create(FacebookAPI.class);
                return service;
            }else{
                return service;
            }
        }
    }
}
