package com.example.pandaapp.server;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

public class QueryAPI {
    Context mctx;
    String urlserver;

    public QueryAPI(Context mctx, String url,String key) {
        this.mctx = mctx;
        this.urlserver = url;
    }

//    public JSONArray MethodGET(String urlserver){
//        JSONArray jsonArray=new JSONArray();
//
//        RequestQueue requestQueue= Volley.newRequestQueue(mctx);
//        JsonArrayRequest arrayRequest=new JsonArrayRequest(Request.Method.GET, urlserver, null,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//
//                        Toast.makeText(mctx, response.length()+"", Toast.LENGTH_SHORT).show();
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                    }
//                });
//    }




}
