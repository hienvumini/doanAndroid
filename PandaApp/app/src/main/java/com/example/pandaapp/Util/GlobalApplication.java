package com.example.pandaapp.Util;

import android.app.Application;
import android.content.Context;

import com.example.pandaapp.Models.Account;
import com.example.pandaapp.Models.CartItem;
import com.example.pandaapp.Models.Category;
import com.example.pandaapp.Models.Product;

import java.util.ArrayList;
import java.util.Set;

public class GlobalApplication extends Application {
    Context context;
    public Product product;
    public Account account;
    public ArrayList<Product> listProduct;
    public ArrayList<CartItem> listcartItems;
    public Set<CartItem> SetcartItems;
    public Category category;

    public void destroy(Object o) {
        destroy(o);

    }


}

