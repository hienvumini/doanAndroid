package com.example.pandaapp.Models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Shop {

@SerializedName("idShop")
@Expose
private int idShop;
@SerializedName("shopName")
@Expose
private String shopName;
@SerializedName("introduce")
@Expose
private String introduce;
@SerializedName("address")
@Expose
private String address;
@SerializedName("phone")
@Expose
private String phone;
@SerializedName("email")
@Expose
private String email;

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

public String getIntroduce() {
return introduce;
}

public void setIntroduce(String introduce) {
this.introduce = introduce;
}

public String getAddress() {
return address;
}

public void setAddress(String address) {
this.address = address;
}

public String getPhone() {
return phone;
}

public void setPhone(String phone) {
this.phone = phone;
}

public String getEmail() {
return email;
}

public void setEmail(String email) {
this.email = email;
}

}