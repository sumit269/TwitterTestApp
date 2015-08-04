package com.testapp.twittertestapp.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by SumitBhatia on 3/08/15.
 */
public class RestClient {

    public final String BASE_API_URL = "https://api.twitter.com";
    private TwitterService twitterService;

    public RestClient() {

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(BASE_API_URL)
//                .setRequestInterceptor(new CustomRequestInterceptor())
                .setConverter(new GsonConverter(gson))
                .build();

        twitterService = restAdapter.create(TwitterService.class);
    }

    public TwitterService getService() {
        return twitterService;
    }

}
