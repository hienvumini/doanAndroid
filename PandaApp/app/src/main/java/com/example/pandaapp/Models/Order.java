package com.example.pandaapp.Models;

import androidx.annotation.NonNull;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Order {

@SerializedName("oderId")
@Expose
private int oderId;
@SerializedName("AccountId")
@Expose
private int AccountId;
@SerializedName("date_created")
@Expose
private String date_created;
@SerializedName("totalPrice")
@Expose
private Double totalPrice;
@SerializedName("name")
@Expose
private String name;
@SerializedName("address")
@Expose
private String address;
@SerializedName("phone_number")
@Expose
private String phone_number;
@SerializedName("orderitem")
@Expose
private List<Orderitem> orderitem;
    @SerializedName("totaldiscount")
    @Expose
private double totaldiscount;
    private  int statusId;


    public Order(int oderId, int accountId, String date_created, Double totalPrice,Double totaldiscount, String name, String address, String phone_number, List<Orderitem> orderitem,int statusId) {
        this.oderId = oderId;
        AccountId = accountId;
        this.date_created = date_created;
        this.totalPrice = totalPrice;
        this.name = name;
        this.address = address;
        this.phone_number = phone_number;
        this.orderitem = orderitem;
        this.totaldiscount=totaldiscount;
        this.statusId=statusId;
    }

    public Order() {
    }

    public int getOderId() {
        return oderId;
    }

    public void setOderId(int oderId) {
        this.oderId = oderId;
    }

    public int getAccountId() {
        return AccountId;
    }

    public void setAccountId(int accountId) {
        AccountId = accountId;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
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

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
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
        return "Order{" +
                "oderId=" + oderId +
                ", AccountId=" + AccountId +
                ", date_created='" + date_created + '\'' +
                ", totalPrice=" + totalPrice +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", orderitem=" + orderitem +
                ", totaldiscount=" + totaldiscount +
                ", statusId=" + statusId +
                '}';
    }
}