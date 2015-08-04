package com.testapp.twittertestapp.models;

import java.io.Serializable;

/**
 * Created by SumitBhatia on 3/08/15.
 */
public class Error implements Serializable {

    String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
