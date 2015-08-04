package com.testapp.twittertestapp.rest.service;

import com.testapp.twittertestapp.models.Tweets;
import com.testapp.twittertestapp.rest.model.TokenWrapper;

import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by SumitBhatia on 3/08/15.
 */
public interface TwitterService {

    @POST("/oauth2/token")
    Observable<TokenWrapper> getBearerToken(@Header("Authorization") String authorization,
                                            @Query("grant_type") String client_credentials);

    @GET("/1.1/search/tweets.json")
    Observable<Tweets> getTweets(@Header("Authorization") String authorization,
                                 @Query("q") String param);
}
