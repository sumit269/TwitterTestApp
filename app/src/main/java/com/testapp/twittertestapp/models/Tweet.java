package com.testapp.twittertestapp.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by SumitBhatia on 3/08/15.
 */
public class Tweet implements Serializable {

    @SerializedName("id")
    long id;

    @SerializedName("text")
    String text;

    @SerializedName("created_at")
    String created_at;

    @SerializedName("source")
    String source;

    @SerializedName("user")
    User user;


    public long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getSource() {
        return source;
    }

    public User getUser() {
        return user;
    }
}