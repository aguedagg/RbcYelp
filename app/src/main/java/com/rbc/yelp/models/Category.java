package com.rbc.yelp.models;

import com.google.gson.annotations.SerializedName;

import java.util.Comparator;

public class Category {
    @SerializedName("alias")
    private String alias;
    @SerializedName("title")
    private String title;

    public String getAlias() {
        return alias;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return title;
    }
}
