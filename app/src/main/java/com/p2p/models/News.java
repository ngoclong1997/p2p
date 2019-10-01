package com.p2p.models;

import com.google.gson.annotations.SerializedName;

public class News {
    @SerializedName("title")
    private String title;
    @SerializedName("body")
    private String description;
    @SerializedName("categories")
    private String tags;
    @SerializedName("imageurl")
    private String imageURL;
    @SerializedName("url")
    private String url;

    public News(String title, String description, String tags, String imageURL, String url) {
        this.title = title;
        this.description = description;
        this.tags = tags;
        this.imageURL = imageURL;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
