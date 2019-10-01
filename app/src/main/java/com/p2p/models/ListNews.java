package com.p2p.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListNews {
    @SerializedName("Data")
    List<News> news;

    public List<News> getNews() {
        return news;
    }

    public void setNews(List<News> news) {
        this.news = news;
    }
}
