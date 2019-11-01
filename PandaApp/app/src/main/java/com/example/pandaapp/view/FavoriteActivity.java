package com.example.pandaapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pandaapp.Models.Account;
import com.example.pandaapp.Models.Product;
import com.example.pandaapp.R;
import com.example.pandaapp.Util.GlobalApplication;
import com.example.pandaapp.adapter.AdapterProduct;
import com.example.pandaapp.server.Server;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FavoriteActivity extends AppCompatActivity {

    List<Product> products= new ArrayList<>();
    AdapterProduct favoriteProduct;
    RecyclerView recyclerView ;
    GlobalApplication globalApplication;
    Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
         globalApplication = (GlobalApplication) getApplicationContext();
         account = globalApplication.account;



        recyclerView = findViewById(R.id.recycleview_favorite);
        favoriteProduct = new AdapterProduct(getApplicationContext(),R.id.recycleview_favorite,products);
        recyclerView.setAdapter(favoriteProduct);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(layoutManager);
        getlistProduct();
    }
    private void getlistProduct() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.getFavoriteItem, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    int productId = 0;
                    String name = "";
                    double price = 0;
                    double discount = 0;
                    String shopName = "";
                    int idShop = 0;
                    String detail = "";
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        productId = jsonObject.getInt("productId");
                        name = jsonObject.getString("name");
                        price = jsonObject.getDouble("price");
                        discount = jsonObject.getDouble("discount");
                        shopName = jsonObject.getString("shopName");
                        idShop = jsonObject.getInt("idShop");
                        detail=jsonObject.getString("detail");
                        JSONArray images = jsonObject.getJSONArray("images");
                        ArrayList<String> strings = new ArrayList<>();
                        for (int j = 0; j < images.length(); j++) {
                            strings.add(images.get(j).toString());
                        }
                        Product product = new Product(productId, name, price, discount, shopName, idShop, strings,detail );
                        products.add(product);
                        favoriteProduct.notifyDataSetChanged();
                        recyclerView.setAdapter(favoriteProduct);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("accID", String.valueOf(account.getAccountId()));
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }

}
