package com.example.pandaapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Category {
    @SerializedName("categoryName")
    @Expose
    private String categoryName;
    @SerializedName("idcategory")
    @Expose
    private int idcategory;
    @SerializedName("thumbnailCate")
    @Expose
    private String thumbnailCate;

    public Category(int idcategory, String categoryName, String thumbnailCate) {
        this.categoryName = categoryName;
        this.idcategory = idcategory;
        this.thumbnailCate = thumbnailCate;
    }

    public Category(String categoryName, String thumbnailCate) {
        this.categoryName = categoryName;
        this.thumbnailCate = thumbnailCate;
    }

    public Category() {
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getIdcategory() {
        return idcategory;
    }

    public void setIdcategory(int idcategory) {
        this.idcategory = idcategory;
    }

    public String getThumbnailCate() {
        return thumbnailCate;
    }

    public void setThumbnailCate(String thumbnailCate) {
        this.thumbnailCate = thumbnailCate;
    }
}
