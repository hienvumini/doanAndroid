package com.example.pandaapp.Models;

public class Category {
    private String NameCategory;
    private int CateID;
    private String ThumbnailCategory;

    public Category( int cateID,String nameCategory, String thumbnailCategory) {
        NameCategory = nameCategory;
        CateID = cateID;
        ThumbnailCategory = thumbnailCategory;
    }

    public Category(String nameCategory, String thumbnailCategory) {
        NameCategory = nameCategory;
        ThumbnailCategory = thumbnailCategory;
    }

    public Category() {
    }

    public String getNameCategory() {
        return NameCategory;
    }

    public void setNameCategory(String nameCategory) {
        NameCategory = nameCategory;
    }

    public int getCateID() {
        return CateID;
    }

    public void setCateID(int cateID) {
        CateID = cateID;
    }

    public String getThumbnailCategory() {
        return ThumbnailCategory;
    }

    public void setThumbnailCategory(String thumbnailCategory) {
        ThumbnailCategory = thumbnailCategory;
    }
}
