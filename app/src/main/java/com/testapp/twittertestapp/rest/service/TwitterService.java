package com.testapp.twittertestapp.rest.service;

import com.testapp.twittertestapp.rest.model.TokenWrapper;
import com.testapp.twittertestapp.models.Tweets;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by SumitBhatia on 3/08/15.
 */
public interface TwitterService {

    @POST("/oauth2/token")
    void getBearerToken(@Header("Authorization") String authorization,
                        @Query("grant_type") String client_credentials,
                        Callback<TokenWrapper> tokenWrapperCallback);

    @GET("/1.1/search/tweets.json")
    void getTweets(@Header("Authorization") String authorization,
                   @Query("q") String param,
                   Callback<Tweets> successWrapperCallback);
}
