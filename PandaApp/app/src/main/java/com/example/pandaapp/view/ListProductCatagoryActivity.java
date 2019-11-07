package com.example.pandaapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.pandaapp.Models.Category;
import com.example.pandaapp.Models.Product;
import com.example.pandaapp.R;
import com.example.pandaapp.Retrofit2.APIUltils;
import com.example.pandaapp.Retrofit2.DataClient;
import com.example.pandaapp.Util.GlobalApplication;
import com.example.pandaapp.adapter.AdapterProduct;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        Toast.makeText(this, "Danh muc "+idCate, Toast.LENGTH_SHORT).show();
        DataClient getProductCategory = APIUltils.getData();
        Call<ArrayList<Product>> arrayListCall = getProductCategory.getProductCategory(idCate);
        arrayListCall.enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                listProduct = response.body();
                adapterProduct.notifyDataSetChanged();
                adapterProduct = new AdapterProduct(getBaseContext(), R.id.recycleview_ShopProduct, listProduct);

                recyclerViewListProduct.setAdapter(adapterProduct);

                Toast.makeText(getApplicationContext(), listProduct.size() + "", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {
                Log.d("BBB", "That bai: " + t.toString());

            }
        });

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
