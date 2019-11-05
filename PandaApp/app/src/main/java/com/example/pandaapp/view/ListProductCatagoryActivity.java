package com.example.pandaapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pandaapp.Models.Category;
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
import java.util.Map;

public class ListProductCatagoryActivity extends AppCompatActivity {
    RecyclerView recyclerViewListProduct;
    ArrayList<Product> listProduct;
    AdapterProduct adapterProduct;
    GlobalApplication globalApplication;
    Category mcategory;
    int idCate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_product_catagory);
        globalApplication = (GlobalApplication) getApplicationContext();
        init();
        if (globalApplication.category != null) {
            mcategory = globalApplication.category;
            idCate = globalApplication.category.getIdcategory();
            getlistProductCate();

        } else {
            Toast.makeText(getApplicationContext(), "Danh mục lỗi", Toast.LENGTH_SHORT).show();

        }

    }


    private void init() {
        recyclerViewListProduct = (RecyclerView) findViewById(R.id.recycleview_CategoryProduct);
        listProduct = new ArrayList<>();
        //fakedata();
        adapterProduct = new AdapterProduct(getApplicationContext(), R.id.recycleview_CategoryProduct, listProduct);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerViewListProduct.setLayoutManager(layoutManager);
        recyclerViewListProduct.setHasFixedSize(true);
        recyclerViewListProduct.setAdapter(adapterProduct);

    }

    private void getlistProductCate() {

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.getListProductAsCate, new Response.Listener<String>() {
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
                            strings.add(images.get(i).toString());
                        }
                        Product product = new Product(productId, name, price, discount, shopName, idShop, strings,detail );
                        listProduct.add(product);

                        adapterProduct.notifyDataSetChanged();
                        recyclerViewListProduct.setAdapter(adapterProduct);
                        Toast.makeText(getApplicationContext(), "Load thêm " + listProduct.size(), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Không thể load sản phẩm", Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("idcategory", idCate + "");
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void fakedata() {

        ArrayList<String> manganh = new ArrayList<>();
        manganh.add("https://cf.shopee.vn/file/b094ce2dc84d13b302e147e1b3cfa6d8");
        manganh.add("https://cf.shopee.vn/file/b094ce2dc84d13b302e147e1b3cfa6d8");
        manganh.add("https://cf.shopee.vn/file/b094ce2dc84d13b302e147e1b3cfa6d8");
        manganh.add("https://cf.shopee.vn/file/b094ce2dc84d13b302e147e1b3cfa6d8");
        listProduct.add(new Product("Ao 1", 150000, "Tu", manganh, "Đây là áo 1"));
        listProduct.add(new Product("Ao 2", 150000, "Tu", manganh, "Đây là áo 1"));
        listProduct.add(new Product("Ao 3", 150000, "Tu", manganh, "Đây là áo 1"));
        listProduct.add(new Product("Ao 4", 150000, "Tu", manganh, "Đây là áo 1"));
        listProduct.add(new Product("Ao 5", 150000, "Tu", manganh, "Đây là áo 1"));

    }

}
