package com.testapp.twittertestapp;

import retrofit.RequestInterceptor;

/**
 * Created by SumitBhatia on 3/08/15.
 */
public class CustomRequestInterceptor implements RequestInterceptor {
    private final TwitterTestApp twiterTestApp;

    public CustomRequestInterceptor(TwitterTestApp twiterTestApp) {
        this.twiterTestApp = twiterTestApp;
    }

    @Override
    public void intercept(RequestFacade requestFacade) {
        requestFacade.addHeader("Accept", "application/json");

    }
}
