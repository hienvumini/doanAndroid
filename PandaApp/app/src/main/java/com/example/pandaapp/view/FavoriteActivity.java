package com.example.pandaapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;

import com.example.pandaapp.Models.Account;
import com.example.pandaapp.Models.Product;
import com.example.pandaapp.R;
import com.example.pandaapp.Retrofit2.APIUltils;
import com.example.pandaapp.Retrofit2.DataClient;
import com.example.pandaapp.Util.GlobalApplication;
import com.example.pandaapp.adapter.AdapterProduct;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteActivity extends AppCompatActivity {

    List<Product> products = new ArrayList<>();
    AdapterProduct favoriteProductAdapter;
    RecyclerView recyclerView;
    GlobalApplication globalApplication;
    Account account;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        globalApplication = (GlobalApplication) getApplicationContext();
        account = globalApplication.account;
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh_Favorite);
        swipeRefreshLayout.setColorSchemeResources(R.color.color_pink2);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                finish();
                startActivity(getIntent());
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        recyclerView = findViewById(R.id.recycleview_favorite);
        favoriteProductAdapter = new AdapterProduct(getApplicationContext(), R.id.recycleview_favorite, products);
        recyclerView.setAdapter(favoriteProductAdapter);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(layoutManager);
        getlistProduct();
    }

    private void getlistProduct() {
        DataClient dataClient = APIUltils.getData();
        Call<ArrayList<Product>> arrayListFavoriteCall = dataClient.getFavoritesProduct(globalApplication.account.getAccountId());
        arrayListFavoriteCall.enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                if (response.body() != null) {
                    products = response.body();
                    favoriteProductAdapter = new AdapterProduct(getApplicationContext(), R.id.recycleview_favorite, products);
                    recyclerView.setAdapter(favoriteProductAdapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {

            }
        });
    }

}
