package com.example.pandaapp.Util;

import android.app.Application;
import android.content.Context;

import com.example.pandaapp.Models.Account;
import com.example.pandaapp.Models.CartItem;
import com.example.pandaapp.Models.Category;
import com.example.pandaapp.Models.Product;

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

