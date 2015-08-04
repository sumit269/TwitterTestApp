package com.testapp.twittertestapp;

import android.app.Application;

import com.testapp.twittertestapp.rest.service.CustomRequestInterceptor;
import com.testapp.twittertestapp.rest.service.TwitterService;

import retrofit.RestAdapter;

/**
 * Created by SumitBhatia on 3/08/15.
 */
public class TwitterTestApp extends Application {

    public final String API_URL = "https://api.twitter.com";
    private TwitterService twitterService;

    @Override
    public void onCreate() {
        super.onCreate();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(API_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setRequestInterceptor(new CustomRequestInterceptor(this))
                .build();

        twitterService = restAdapter.create(TwitterService.class);
    }

    public TwitterService getService() {
        return twitterService;
    }
}
