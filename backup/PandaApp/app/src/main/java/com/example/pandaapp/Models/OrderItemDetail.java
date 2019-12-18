package com.example.pandaapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderItemDetail {

@SerializedName("productId")
@Expose
private int productId;
@SerializedName("name")
@Expose
private String name;
@SerializedName("image")
@Expose
private String image;
@SerializedName("amount")
@Expose
private int amount;
@SerializedName("price")
@Expose
private double price;
@SerializedName("total")
@Expose
private double total;
@SerializedName("discount")
@Expose
private double discount;

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

public String getImage() {
return image;
}

public void setImage(String image) {
this.image = image;
}

public int getAmount() {
return amount;
}

public void setAmount(int amount) {
this.amount = amount;
}

public double getPrice() {
return price;
}

public void setPrice(double price) {
this.price = price;
}

public double getTotal() {
return total;
}

public void setTotal(double total) {
this.total = total;
}

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}