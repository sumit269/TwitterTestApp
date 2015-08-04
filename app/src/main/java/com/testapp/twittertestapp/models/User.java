package com.testapp.twittertestapp.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by SumitBhatia on 3/08/15.
 */
public class User implements Serializable {

    @SerializedName("id")
    private long id;

    @SerializedName("name")
    private String name;

    @SerializedName("screen_name")
    private String screen_name;

    @SerializedName("profile_image_url")
    private String profile_image_url;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getScreen_name() {
        return screen_name;
    }

    public String getProfile_image_url() {
        return profile_image_url;
    }
}
