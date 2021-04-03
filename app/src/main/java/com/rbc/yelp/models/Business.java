package com.rbc.yelp.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Business model from the Yelp v3 API.
 * Update this file to include any fields you feel are missing.
 * @see <a href=https://www.yelp.ca/developers/documentation/v3/business_search>Yelp API Business Search</a>
 */
public class Business {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("image_url")
    private String thumbnail;
    @SerializedName("review_count")
    private String numReviews;
    @SerializedName("rating")
    private String rating;
    @SerializedName("categories")
    private List<Category> categories;

    public String getId() { return id; }

    public String getName() { return name; }

    public String getThumbnail() { return thumbnail; }

    public String getNumReviews() { return numReviews; }

    public String getRating() { return rating; }

    public List<Category> getCategories() { return categories; }
}
