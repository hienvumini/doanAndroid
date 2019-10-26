package com.example.pandaapp.Models;

import java.util.ArrayList;

public class Product {
    private int productId;
    private String name;
    private  double price;
    private double discount;
    private String shopName;
    private int idShop;
private int sub_category;
private ArrayList<String> AnhSP;
private  String Dis;

    public Product(int productId, String name, double price, double discount, String shopName, int idShop, int sub_category) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.discount = discount;
        this.shopName = shopName;
        this.idShop = idShop;
        this.sub_category = sub_category;
    }

    public Product(int productId, String name, double price, double discount, String shopName, int idShop) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.discount = discount;
        this.shopName = shopName;
        this.idShop = idShop;
    }

    public Product(int productId, String name, double price, double discount, String shopName, int idShop, ArrayList<String> anhSP,String disc) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.discount = discount;
        this.shopName = shopName;
        this.idShop = idShop;
        AnhSP = anhSP;
        this.Dis=disc;
    }

    public Product(String name, double price, String shopName, ArrayList<String> anhSP,String disc) {
        this.name = name;
        this.price = price;
        this.shopName = shopName;
        AnhSP = anhSP;
        this.Dis=disc;
    }

    public Product() {
    }

    public String getDis() {
        return Dis;
    }

    public void setDis(String dis) {
        Dis = dis;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public ArrayList<String> getAnhSP() {
        return AnhSP;
    }

    public void setAnhSP(ArrayList<String> anhSP) {
        AnhSP = anhSP;
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

    public int getSub_category() {
        return sub_category;
    }

    public void setSub_category(int sub_category) {
        this.sub_category = sub_category;
    }
}



