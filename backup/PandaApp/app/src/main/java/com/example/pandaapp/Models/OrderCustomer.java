package com.example.pandaapp.Models;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderCustomer {

    @SerializedName("oderId")
    @Expose
    private int oderId;
    @SerializedName("idShop")
    @Expose
    private int idShop;
    @SerializedName("shopName")
    @Expose
    private String shopName;
    @SerializedName("date_created")
    @Expose
    private String dateCreated;
    @SerializedName("totalPrice")
    @Expose
    private double totalPrice;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;
    @SerializedName("orderitem")
    @Expose
    private List<Orderitem> orderitem = null;
    @SerializedName("totaldiscount")
    @Expose
    private double totaldiscount;
    @SerializedName("statusId")
    @Expose
    private int statusId;

    public int getOderId() {
        return oderId;
    }

    public void setOderId(int oderId) {
        this.oderId = oderId;
    }

    public int getIdShop() {
        return idShop;
    }

    public void setIdShop(int idShop) {
        this.idShop = idShop;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Orderitem> getOrderitem() {
        return orderitem;
    }

    public void setOrderitem(List<Orderitem> orderitem) {
        this.orderitem = orderitem;
    }

    public double getTotaldiscount() {
        return totaldiscount;
    }

    public void setTotaldiscount(double totaldiscount) {
        this.totaldiscount = totaldiscount;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    @Override
    public String toString() {
        return "OrderCustomer{" +
                "oderId=" + oderId +
                ", idShop=" + idShop +
                ", shopName='" + shopName + '\'' +
                ", dateCreated='" + dateCreated + '\'' +
                ", totalPrice=" + totalPrice +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", orderitem=" + orderitem +
                ", totaldiscount=" + totaldiscount +
                ", statusId=" + statusId +
                '}';
    }
}