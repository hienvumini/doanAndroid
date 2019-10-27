package com.example.pandaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.example.pandaapp.Models.CartItem;
import com.example.pandaapp.Util.GlobalApplication;
import com.example.pandaapp.adapter.AdapterCartItem;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class CartActivity extends AppCompatActivity {
    ListView listViewCart;
    ArrayList<CartItem> listCartItem;
    AdapterCartItem adapterCartItem;
    Set<CartItem> SetcartItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        init();

    }

    private void init() {
        listViewCart = (ListView) findViewById(R.id.listviewProduct_Cart);

        SetcartItems = new HashSet<>();
        GlobalApplication globalApplication = (GlobalApplication) getApplicationContext();
        if (globalApplication.SetcartItems != null) {
            SetcartItems = globalApplication.SetcartItems;
            listCartItem = new ArrayList<>(SetcartItems);
            adapterCartItem = new AdapterCartItem(getApplicationContext(), R.id.listviewProduct_Cart, listCartItem);
            listViewCart.setAdapter(adapterCartItem);
        }else {
            Toast.makeText(globalApplication, "Giỏ hàng trống", Toast.LENGTH_SHORT).show();

        }

    }
}
