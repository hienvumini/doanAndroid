package com.example.pandaapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Orderitem {

@SerializedName("productId")
@Expose
private int productId;
@SerializedName("amount")
@Expose
private int amount;
@SerializedName("total")
@Expose
private double total;

    public Orderitem(int productId, int amount, double total) {
        this.productId = productId;
        this.amount = amount;
        this.total = total;
    }

    public Orderitem() {
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}