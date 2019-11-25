package com.example.pandaapp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.pandaapp.Models.Category;
import com.example.pandaapp.Models.Product;
import com.example.pandaapp.R;
import com.example.pandaapp.Retrofit2.APIUltils;
import com.example.pandaapp.Retrofit2.DataClient;
import com.example.pandaapp.Util.GlobalApplication;
import com.example.pandaapp.adapter.AdapterProduct;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
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

    boolean isScolling = false;
    int pastVisiableItems, totalItems, visiableItemCount;
    LinearLayoutManager linnerlayout;
    GridLayoutManager gridLayoutManager;
    GridLayoutManager layoutManager;
    ProgressBar progressBar;
    int page_number = 1;
    int item_count = 6;
    int offset = 0;
    boolean intial = true;
    SwipeRefreshLayout swipeRefreshLayout;


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
            Toasty.error(getApplicationContext(),"Danh mục lỗi").show();

        }

    }


    private void init() {
        swipeRefreshLayout=(SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        progressBar = (ProgressBar) findViewById(R.id.processbar_Category);
        recyclerViewListProduct = (RecyclerView) findViewById(R.id.recycleview_CategoryProduct);
        listProduct = new ArrayList<>();
        //fakedata();
        adapterProduct = new AdapterProduct(getApplicationContext(), R.id.recycleview_CategoryProduct, listProduct);
        layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerViewListProduct.setLayoutManager(layoutManager);
        recyclerViewListProduct.setHasFixedSize(true);
        recyclerViewListProduct.setAdapter(adapterProduct);

    }

    private void getlistProductCate() {

        fetchData();


        recyclerViewListProduct.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScolling = true;
                }

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visiableItemCount = layoutManager.getChildCount();
                totalItems = layoutManager.getItemCount();
                pastVisiableItems = layoutManager.findFirstVisibleItemPosition();

                if (dy > 0) {

                    if (isScolling && (visiableItemCount + pastVisiableItems == totalItems)) {

                        isScolling = false;
                        progressBar.setVisibility(View.VISIBLE);
                        fetchData();

                        //
                    }
                } else {


                    

                }

            }
        });
        swipeRefreshLayout.setColorSchemeResources(R.color.color_pink2);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                listProduct.clear();
                fetchData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    private void fetchData() {


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                DataClient dataClient = APIUltils.getData();

                if (intial) {
                    offset = 0;

                } else {

                    offset = listProduct.size();
                }
                Call<ArrayList<Product>> arrayListCall = dataClient.getProductCategory(idCate, offset);
                arrayListCall.enqueue(new Callback<ArrayList<Product>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                        ArrayList<Product> listadd;
                        listadd = response.body();
                        if (intial) {

                            listProduct = response.body();
                            adapterProduct.notifyDataSetChanged();
                            adapterProduct = new AdapterProduct(getBaseContext(), R.id.recycleview_ShopProduct, listProduct);
                            recyclerViewListProduct.setAdapter(adapterProduct);
                            Toast.makeText(getApplicationContext(), listProduct.size() + "", Toast.LENGTH_SHORT).show();
                            intial = false;
                        } else {
                            if (listadd.size() > 0) {
                                listProduct.addAll(listadd);
                                Log.d("EEE", "That bai: " + listProduct.size());
                                adapterProduct.notifyDataSetChanged();
                                adapterProduct = new AdapterProduct(getBaseContext(), R.id.recycleview_ShopProduct, listProduct);

                                recyclerViewListProduct.setAdapter(adapterProduct);

                                Toast.makeText(getApplicationContext(), listProduct.size() + "", Toast.LENGTH_SHORT).show();

                            } else {
                                Toasty.custom(getApplicationContext(),"Đã tải xong tất cả sản phẩm",R.drawable.ok, R.color.color_pink2,2000,true,true).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Product>> call, Throwable t) {
                        Log.d("BBB", "That bai: " + t.toString());

                    }
                });
                progressBar.setVisibility(View.GONE);

            }
        }, 500);

    }


}
