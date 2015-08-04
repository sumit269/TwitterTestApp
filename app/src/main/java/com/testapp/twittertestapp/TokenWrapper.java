package com.testapp.twittertestapp;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SumitBhatia on 3/08/15.
 */
public class TokenWrapper {

    @SerializedName("token_type")
    String token_type;

    @SerializedName("access_token")
    String access_token;

    public String getToken_type() {
        return token_type;
    }

    public String getAccess_token() {
        return access_token;
    }
}
