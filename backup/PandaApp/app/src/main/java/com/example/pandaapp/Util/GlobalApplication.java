package com.example.pandaapp.Util;

import android.app.Application;
import android.content.Context;

import com.example.pandaapp.Models.Account;
import com.example.pandaapp.Models.CartItem;
import com.example.pandaapp.Models.Category;
import com.example.pandaapp.Models.News;
import com.example.pandaapp.Models.Order;
import com.example.pandaapp.Models.OrderCustomer;
import com.example.pandaapp.Models.Product;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GlobalApplication extends Application {
    Context context;
    public Product product;
    public Account account;
    public ArrayList<Product> listProduct;
    public List<CartItem> ListcartItems;
    public Category category;
    public Order order;
    public OrderCustomer orderCustomer;
    public News news;
    public String result;
    public int orderID;

    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }

    public double updatetotal() {
        double total = 0;
        if (ListcartItems!=null)
            for (int i = 0; i < ListcartItems.size(); i++) {
                double price = ListcartItems.get(i).getProduct().getPrice();
                double amount = (double) ListcartItems.get(i).getMount();
                total += price * amount;
            }

        return total;
    }


}

