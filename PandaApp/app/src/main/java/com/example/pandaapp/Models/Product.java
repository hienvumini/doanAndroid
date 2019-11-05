package com.example.pandaapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Product {
    @SerializedName("productId")
    @Expose
    private int productId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("price")
    @Expose
    private double price;
    @SerializedName("discount")
    @Expose
    private double discount;
    @SerializedName("shopName")
    @Expose
    private String shopName;
    @SerializedName("idShop")
    @Expose
    private int idShop;

    private int idcategory;
    @SerializedName("images")
    @Expose
    private ArrayList<String> images;
    @SerializedName("detail")
    @Expose
    private String detail;

    public Product(int productId, String name, double price, double discount, String shopName, int idShop, int idcategory) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.discount = discount;
        this.shopName = shopName;
        this.idShop = idShop;
        this.idcategory = idcategory;
    }

    public Product(int productId, String name, double price, double discount, String shopName, int idShop) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.discount = discount;
        this.shopName = shopName;
        this.idShop = idShop;
    }

    public Product(int productId, String name, double price, double discount, String shopName, int idShop, ArrayList<String> images, String detail) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.discount = discount;
        this.shopName = shopName;
        this.idShop = idShop;
        this.images = images;
        this.detail = detail;
    }

    public Product(String name, double price, String shopName, ArrayList<String> images, String detail) {
        this.name = name;
        this.price = price;
        this.shopName = shopName;
        this.images = images;
        this.detail = detail;
    }

    public Product(String name, double price, double discount, int idShop, int idcategory, String detail) {
        this.name = name;
        this.price = price;
        this.discount = discount;
        this.idShop = idShop;
        this.idcategory = idcategory;
        this.detail = detail;
    }

    public Product() {
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", discount=" + discount +
                ", shopName='" + shopName + '\'' +
                ", idShop=" + idShop +
                ", idcategory=" + idcategory +
                ", images=" + images +
                ", detail='" + detail + '\'' +
                '}';
    }

    public String getDis() {
        return detail;
    }

    public void setDis(String dis) {
        detail = dis;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        images = images;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public int getIdShop() {
        return idShop;
    }

    public void setIdShop(int idShop) {
        this.idShop = idShop;
    }

    public int getIdcategory() {
        return idcategory;
    }

    public void setIdcategory(int idcategory) {
        this.idcategory = idcategory;
    }
}



