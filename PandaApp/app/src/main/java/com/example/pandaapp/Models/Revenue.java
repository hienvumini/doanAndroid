package com.example.pandaapp.Models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Revenue {
    @SerializedName("date_created")
    @Expose
    private String date;
    @SerializedName("revenue")
    @Expose
    private double revenue;

    public Revenue(String date, double revenue) {

        this.date = date;
        this.revenue = revenue;
    }

    public Revenue() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    @Override
    public String toString() {
        return "Revenue{" +
                "date='" + date + '\'' +
                ", revenue=" + revenue +
                '}';
    }
}
