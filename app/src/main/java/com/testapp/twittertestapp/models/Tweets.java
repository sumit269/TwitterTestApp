package com.testapp.twittertestapp.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SumitBhatia on 3/08/15.
 */
public class Tweets implements Serializable {

    @SerializedName("statuses")
    private ArrayList<Tweet> tweetList;

    public ArrayList<Tweet> getTweetList() {
        return tweetList;
    }
}
