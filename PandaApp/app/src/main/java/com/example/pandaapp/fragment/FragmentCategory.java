package com.example.pandaapp.fragment;

import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import com.example.pandaapp.adapter.AdapterCategory;
import com.example.pandaapp.server.Server;
import com.example.pandaapp.view.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class FragmentCategory extends Fragment {
    RecyclerView recyclerViewListCate;
    AdapterCategory adapterCategory;
    ArrayList<Category> categoryList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        recyclerViewListCate = (RecyclerView) view.findViewById(R.id.recycleviewListCate);
        categoryList = new ArrayList<>();
        //fakelistCate();
        getListCategory();
        adapterCategory = new AdapterCategory(getActivity(), R.id.recycleviewListCate, categoryList);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        LinearLayoutManager linnerlayout
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewListCate.setLayoutManager(layoutManager);
        recyclerViewListCate.setHasFixedSize(true);
        recyclerViewListCate.setAdapter(adapterCategory);


        return view;

    }

    private void init(View view) {


    }

    public void fakelistCate() {
        categoryList.add(new Category(1,"Áo phông", "https://images.nike.com/is/image/DotCom/PDP_P/OS2711_657_A/icon-nba-rockets-older-basketball-t-shirt.png?fmt=png-alpha"));
        categoryList.add(new Category(1,"Áo Sơ mi", "https://images.nike.com/is/image/DotCom/PDP_P/OS2711_657_A/icon-nba-rockets-older-basketball-t-shirt.png?fmt=png-alpha"));
        categoryList.add(new Category(7,"Áo Lót", "https://images.nike.com/is/image/DotCom/PDP_P/OS2711_657_A/icon-nba-rockets-older-basketball-t-shirt.png?fmt=png-alpha"));
        categoryList.add(new Category(9,"Áo Ngực", "https://images.nike.com/is/image/DotCom/PDP_P/OS2711_657_A/icon-nba-rockets-older-basketball-t-shirt.png?fmt=png-alpha"));
        categoryList.add(new Category(9,"Quần đùi", "https://images.nike.com/is/image/DotCom/PDP_P/OS2711_657_A/icon-nba-rockets-older-basketball-t-shirt.png?fmt=png-alpha"));
        categoryList.add(new Category(9,"Quần âu", "https://images.nike.com/is/image/DotCom/PDP_P/OS2711_657_A/icon-nba-rockets-older-basketball-t-shirt.png?fmt=png-alpha"));

    }
public void getListCategory(){
    RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
    StringRequest stringRequest=new StringRequest(Request.Method.GET, Server.getCategory, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                JSONArray jsonArray=new JSONArray(response);
                int cateId=0;
                String categoryName="";
                String thumbnailCate="";
                for (int i = 0; i <jsonArray.length() ; i++) {
                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                    cateId=jsonObject.getInt("idcategory");
                    categoryName=jsonObject.getString("categoryName");
                    thumbnailCate=jsonObject.getString("thumbnailCate");
                    Category category=new Category(cateId,categoryName,thumbnailCate);
                    categoryList.add(category);

                    adapterCategory.notifyDataSetChanged();
                    recyclerViewListCate.setAdapter(adapterCategory);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    });
    requestQueue.add(stringRequest);

}

}
