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

    @SerializedName("location")
    private String location;

    @SerializedName("description")
    private String description;

    @SerializedName("url")
    private String url;

    @SerializedName("followers_count")
    private long followers_count;

    @SerializedName("friends_count")
    private long friends_count;

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

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public long getFollowers_count() {
        return followers_count;
    }

    public long getFriends_count() {
        return friends_count;
    }

    public String getProfile_image_url() {
        return profile_image_url;
    }
}
