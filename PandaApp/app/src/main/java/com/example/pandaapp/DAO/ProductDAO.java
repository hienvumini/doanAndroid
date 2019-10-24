package com.example.pandaapp.DAO;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pandaapp.DAOimple.ProductDAOimple;
import com.example.pandaapp.Models.Product;
import com.example.pandaapp.server.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDAO implements ProductDAOimple {

    Context mctx;
    String url;
    StringBuilder result = new StringBuilder();


    public StringBuilder getResult() {
        return result;
    }

    public void setResult(StringBuilder result) {
        this.result = result;
    }

    public ProductDAO(Context mctx) {
        this.mctx = mctx;
    }

    @Override
    public void getAll() {
        final List<Product> ListProduct = new ArrayList<>();
        String url = Server.GetAllProduct;

        RequestQueue requestQueue = Volley.newRequestQueue(mctx);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                result.append(response);

                Toast.makeText(mctx, result, Toast.LENGTH_SHORT).show();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    int productId = 0;
                    String name = "";
                    double price = 0;
                    double discount = 0;
                    String shopName = "";
                    int idShop = 0;


                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        idShop = jsonObject.getInt("idShop");
                        name = jsonObject.getString("name");
                        price = jsonObject.getDouble("price");
                        discount = jsonObject.getDouble("discount");
                        shopName = jsonObject.getString("shopName");
                        idShop = jsonObject.getInt("idShop");
                        Product product = new Product(idShop, name, price, discount, shopName, idShop);
                        ListProduct.add(product);

                    }
                    // Toast.makeText(mctx, jsonArray.length()+"", Toast.LENGTH_SHORT).show();
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
                hashMap.put("id_subcate", "9");
                return hashMap;
            }
        };

        requestQueue.add(stringRequest);


    }


}
