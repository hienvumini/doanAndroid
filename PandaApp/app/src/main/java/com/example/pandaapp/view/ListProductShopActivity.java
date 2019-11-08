package com.example.pandaapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pandaapp.Models.Product;
import com.example.pandaapp.R;
import com.example.pandaapp.Retrofit2.APIUltils;
import com.example.pandaapp.Retrofit2.DataClient;
import com.example.pandaapp.Util.GlobalApplication;
import com.example.pandaapp.adapter.AdapterProduct;

import org.json.JSONArray;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListProductShopActivity extends AppCompatActivity {
    RecyclerView recyclerViewListProduct;
    ArrayList<Product> listProduct;
    AdapterProduct adapterProduct;
    int idshop;
    GlobalApplication globalApplication;
    ImageView imageViewcatagory_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_product_shop);
        init();
        getProductShop();
        onClickListener();
    }


    private void init() {
        imageViewcatagory_back=(ImageView) findViewById(R.id.catagory_back_ProductShop);
        recyclerViewListProduct = (RecyclerView) findViewById(R.id.recycleview_ShopProduct);
        listProduct = new ArrayList<>();
        adapterProduct = new AdapterProduct(this, R.id.recycleview_ShopProduct, listProduct);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerViewListProduct.setLayoutManager(layoutManager);
        recyclerViewListProduct.setHasFixedSize(true);
        recyclerViewListProduct.setAdapter(adapterProduct);
         globalApplication = (GlobalApplication) getApplicationContext();
        idshop = globalApplication.account.getIdShop();


    }

    private void getProductShop() {

            DataClient getproduct = APIUltils.getData();
            Call<ArrayList<Product>> stringCall = getproduct.getProductShop(idshop);
            stringCall.enqueue(new Callback<ArrayList<Product>>() {
                @Override
                public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                    Log.d("AAA", "onResponse: " + response.body());
                    listProduct = response.body();
                    Toast.makeText(ListProductShopActivity.this, "San pham: " + listProduct.size(), Toast.LENGTH_SHORT).show();
                    adapterProduct = new AdapterProduct(getApplicationContext(), R.id.recycleview_ShopProduct, listProduct);
                    adapterProduct.notifyDataSetChanged();
                    recyclerViewListProduct.setAdapter(adapterProduct);

                }

                @Override
                public void onFailure(Call<ArrayList<Product>> call, Throwable t) {
                    Log.d("BBB", "onResponse: " + t.toString());

                }
            });


    }
    public void onClickListener(){
        imageViewcatagory_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
