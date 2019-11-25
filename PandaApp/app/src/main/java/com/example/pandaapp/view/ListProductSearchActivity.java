package com.example.pandaapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.pandaapp.Models.Product;
import com.example.pandaapp.R;
import com.example.pandaapp.Retrofit2.APIUltils;
import com.example.pandaapp.adapter.AdapterProduct;

import java.util.ArrayList;

public class ListProductSearchActivity extends AppCompatActivity {
    RecyclerView recyclerViewListProduct;
    ArrayList<Product> listProduct;
    AdapterProduct adapterProduct;
    String key;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_product_search);

        init();
        Intent intent = getIntent();
        key = intent.getStringExtra("key");
        getProductSearch();
    }

    private void init() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh_Search);
        recyclerViewListProduct = (RecyclerView) findViewById(R.id.recycleview_SearchProduct);
        listProduct = new ArrayList<>();
        adapterProduct = new AdapterProduct(getApplicationContext(), R.id.recycleview_SearchProduct, listProduct);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerViewListProduct.setLayoutManager(layoutManager);
        recyclerViewListProduct.setHasFixedSize(true);
        recyclerViewListProduct.setAdapter(adapterProduct);
        onListener();

    }

    private void onListener() {
        swipeRefreshLayout.setColorSchemeResources(R.color.color_pink2);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                finish();
                startActivity(getIntent());
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void getProductSearch() {
        APIUltils.getData().getProductSearch(key).enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                if (response.body().size() > 0) {
                    listProduct = response.body();
                    Log.d("giatri", "onResponse: " + listProduct.size());
                    for (int i = 0; i < listProduct.size(); i++) {
                        System.out.println("A11A " + listProduct.get(i).toString());
                    }
                    adapterProduct = new AdapterProduct(getApplicationContext(), R.id.recycleview_SearchProduct, listProduct);
                    adapterProduct.notifyDataSetChanged();
                    recyclerViewListProduct.setAdapter(adapterProduct);
                } else {
                    Toasty.error(getApplicationContext(),"Không tìm thấy sản phẩm nào",2000).show();

                }
            }

            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {
                Log.d("giatri1", "onResponse: " + t.toString());
            }
        });
    }


}
