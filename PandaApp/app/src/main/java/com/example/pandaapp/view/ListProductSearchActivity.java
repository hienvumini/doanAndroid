package com.example.pandaapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;

import com.example.pandaapp.Models.Product;
import com.example.pandaapp.R;
import com.example.pandaapp.Retrofit2.APIUltils;
import com.example.pandaapp.adapter.AdapterProduct;

import java.util.ArrayList;

public class ListProductSearchActivity extends AppCompatActivity {
    RecyclerView recyclerViewListProduct;
    ArrayList<Product> listProduct;
    AdapterProduct adapterProduct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_product_search);
        init();
    }

    private void init() {
        recyclerViewListProduct = (RecyclerView) findViewById(R.id.recycleview_SearchProduct);
        listProduct = new ArrayList<>();
        adapterProduct = new AdapterProduct(getApplicationContext(), R.id.recycleview_SearchProduct, listProduct);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerViewListProduct.setLayoutManager(layoutManager);
        recyclerViewListProduct.setHasFixedSize(true);
        recyclerViewListProduct.setAdapter(adapterProduct);

    }

    private void getProductSearch()
    {
        APIUltils.getData().getProductSearch("key").enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                listProduct = response.body();
                adapterProduct.notifyDataSetChanged();
                recyclerViewListProduct.setAdapter(adapterProduct);
            }

            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {

            }
        });
    }


}
