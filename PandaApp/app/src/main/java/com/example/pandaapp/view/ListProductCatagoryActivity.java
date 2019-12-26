package com.example.pandaapp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
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
       int offset = 0;
    boolean intial = true;
    SwipeRefreshLayout swipeRefreshLayout;
    ImageView imageView_Cart,imageView_back;
    TextView textView_nameCategory;
    Spinner spinner_sort;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_product_catagory);
        globalApplication = (GlobalApplication) getApplicationContext();
        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (globalApplication.category != null) {
            mcategory = globalApplication.category;
            idCate = globalApplication.category.getIdcategory();
            try {
                getlistProductCate(3);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            Toasty.error(getApplicationContext(),"Danh mục lỗi").show();

        }
      listener();

    }

    private void listener() {
        imageView_Cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ListProductCatagoryActivity.this,CartActivity.class);
                startActivity(intent);
            }
        });
        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        spinner_sort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                try {

                    getlistProductCate(position);
                    listProduct.clear();
                    adapterProduct.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void init() throws Exception{
        spinner_sort=(Spinner) findViewById(R.id.sort_ProductCategory);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.array_sort, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner_sort.setBackgroundColor(Color.TRANSPARENT);
        spinner_sort.setAdapter(adapter);

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
        imageView_Cart=(ImageView) findViewById(R.id.catagory_cart_ProductCategory);
        textView_nameCategory=(TextView) findViewById(R.id.catagory_tenSP_ProductCategory);
        textView_nameCategory.setText(globalApplication.category.getCategoryName());
        imageView_back=(ImageView)findViewById(R.id.catagory_back_Category);


    }

    private void getlistProductCate(final int sortID) throws Exception{

        fetchData(sortID);


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
                        try {
                            fetchData(sortID);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

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
                try {
                    fetchData(sortID);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    private void fetchData(final int sortID) throws Exception{


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                DataClient dataClient = APIUltils.getData();

                if (intial) {
                    offset = 0;

                } else {

                    offset = listProduct.size();
                }
                Call<ArrayList<Product>> arrayListCall = dataClient.getProductCategory(idCate, offset,sortID);
                arrayListCall.enqueue(new Callback<ArrayList<Product>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                        try {
                            ArrayList<Product> listadd;
                            listadd = response.body();
                            if (intial) {

                                listProduct = response.body();
                                adapterProduct.notifyDataSetChanged();
                                adapterProduct = new AdapterProduct(getBaseContext(), R.id.recycleview_ShopProduct, listProduct);
                                recyclerViewListProduct.setAdapter(adapterProduct);

                                intial = false;
                            } else {
                                if (listadd.size() > 0) {
                                    listProduct.addAll(listadd);
                                    Log.d("EEE", "That bai: " + listProduct.size());
                                    adapterProduct.notifyDataSetChanged();
                                    adapterProduct = new AdapterProduct(getBaseContext(), R.id.recycleview_ShopProduct, listProduct);

                                    recyclerViewListProduct.setAdapter(adapterProduct);



                                } else {
                                    Toasty.custom(getApplicationContext(),"Đã tải xong tất cả sản phẩm",R.drawable.ok, R.color.color_pink2,2000,true,true).show();
                                }
                            }
                        } catch (Exception e){
                            System.out.println(e.toString());

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
