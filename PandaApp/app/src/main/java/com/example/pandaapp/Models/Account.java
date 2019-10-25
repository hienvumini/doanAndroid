package com.example.pandaapp.Models;

import java.io.Serializable;

public class Account implements Serializable {
    private int roleId;
    private int idShop;
    private String usename;
    private String password;
    private String name;
    private String phone_number;
    private String address;
    private int gender;
    private  String email;
    private String DateOfBirth;
    private int accountStatus;

    public Account(int roleId, int idShop, String usename, String password, String name, String phone_number, String address, int gender, String email, String dateOfBirth, int accountStatus) {
        this.roleId = roleId;
        this.idShop = idShop;
        this.usename = usename;
        this.password = password;
        this.name = name;
        this.phone_number = phone_number;
        this.address = address;
        this.gender = gender;
        this.email = email;
        DateOfBirth = dateOfBirth;
        this.accountStatus = accountStatus;
    }


    public Account() {
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getIdShop() {
        return idShop;
    }

    public void setIdShop(int idShop) {
        this.idShop = idShop;
    }

    public String getUsename() {
        return usename;
    }

    public void setUsename(String usename) {
        this.usename = usename;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        DateOfBirth = dateOfBirth;
    }

    public int getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(int accountStatus) {
        this.accountStatus = accountStatus;
    }

    @Override
    public String toString() {
        return "Account{" +
                "roleId=" + roleId +
                ", idShop=" + idShop +
                ", usename='" + usename + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", address='" + address + '\'' +
                ", gender=" + gender +
                ", email='" + email + '\'' +
                ", DateOfBirth='" + DateOfBirth + '\'' +
                ", accountStatus=" + accountStatus +
                '}';
    }
}



//$row['roleId'],
//        $row['idShop'],
//        $row['usename'],
//        $row['password'],
//        $row['name'],
//        $row['phone_number'],
//        $row['address'],
//        $row['gender'],
//        $row['email'],
//        $row['DateOfBirth'],
//        $row['accountStatus']
