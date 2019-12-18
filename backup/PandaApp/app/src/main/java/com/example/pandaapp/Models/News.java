package com.example.pandaapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class News {
    @SerializedName("newsId")
    @Expose
    private int newsId;
    @SerializedName("newstitle")
    @Expose
    private String newstitle;
    @SerializedName("detailNews")
    @Expose
    private String detailNews;
    @SerializedName("imageNews")
    @Expose
    private String imageNews;
    @SerializedName("dateCreated")
    @Expose
    private String dateCreated;

    public News(int newsId, String newstitle, String detailNews, String imageNews, String dateCreated) {
        this.newsId = newsId;
        this.newstitle = newstitle;
        this.detailNews = detailNews;
        this.imageNews = imageNews;
        this.dateCreated = dateCreated;
    }

    public News(String newstitle, String detailNews, String imageNews, String dateCreated) {
        this.newstitle = newstitle;
        this.detailNews = detailNews;
        this.imageNews = imageNews;
        this.dateCreated = dateCreated;
    }

    public News() {
    }

    public int getNewsId() {
        return newsId;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    public String getNewstitle() {
        return newstitle;
    }

    public void setNewstitle(String newstitle) {
        this.newstitle = newstitle;
    }

    public String getDetailNews() {
        return detailNews;
    }

    public void setDetailNews(String detailNews) {
        this.detailNews = detailNews;
    }

    public String getImageNews() {
        return imageNews;
    }

    public void setImageNews(String imageNews) {
        this.imageNews = imageNews;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }
}
