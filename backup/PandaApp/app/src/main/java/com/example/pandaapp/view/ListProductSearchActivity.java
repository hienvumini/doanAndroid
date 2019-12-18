package com.example.pandaapp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    Spinner spinnersort;
    TextView textViewSearch;
    ImageView imageView_back,imageViewCart;
    int offset=0;
    boolean isScolling = false;
    int pastVisiableItems, totalItems, visiableItemCount;
    GridLayoutManager layoutManager;
    ProgressBar progressBar;
    int idsort=0;
    boolean intial = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_product_search);

        try {
            init();
            onListener();
            Intent intent = getIntent();
            key = intent.getStringExtra("key");
            textViewSearch.setText(key);
            getProductSearch(0);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void init() throws Exception{
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh_Search);
        recyclerViewListProduct = (RecyclerView) findViewById(R.id.recycleview_SearchProduct);
        listProduct = new ArrayList<>();
        adapterProduct = new AdapterProduct(getApplicationContext(), R.id.recycleview_SearchProduct, listProduct);
       layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerViewListProduct.setLayoutManager(layoutManager);
        recyclerViewListProduct.setHasFixedSize(true);
        recyclerViewListProduct.setAdapter(adapterProduct);
        spinnersort=(Spinner) findViewById(R.id.sort_ProductSearch);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.array_sort, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnersort.setBackgroundColor(Color.TRANSPARENT);
        spinnersort.setAdapter(adapter);
        progressBar=(ProgressBar) findViewById(R.id.processbar_Search);
        textViewSearch=(TextView) findViewById(R.id.search_tenSP);
        imageView_back=(ImageView) findViewById(R.id.search_back_Search);
        imageViewCart=(ImageView) findViewById(R.id.search_cart_Search);
        imageViewCart.setColorFilter(Color.WHITE);



    }

    private void onListener() throws Exception{
        swipeRefreshLayout.setColorSchemeResources(R.color.color_pink2);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                finish();
                startActivity(getIntent());
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        spinnersort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    idsort=position;
                    getProductSearch(position);
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
                            getProductSearch(idsort);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        //
                    }
                } else {




                }

            }

        });
        textViewSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),SearchActivity.class);
                startActivity(intent);
                finish();
            }
        });
        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imageViewCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),CartActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void getProductSearch(final int sortID) throws Exception{
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
        if (intial) {
            offset = 0;

        } else {

            offset = listProduct.size();
        }
        APIUltils.getData().getProductSearch(key,sortID,offset).enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
//                try {
//                    if (response.body().size() > 0) {
//                        listProduct = response.body();
//                        Log.d("giatri", "onResponse: " + listProduct.size());
//                        for (int i = 0; i < listProduct.size(); i++) {
//                            System.out.println("A11A " + listProduct.get(i).toString());
//                        }
//                        adapterProduct = new AdapterProduct(getApplicationContext(), R.id.recycleview_SearchProduct, listProduct);
//                        adapterProduct.notifyDataSetChanged();
//                        recyclerViewListProduct.setAdapter(adapterProduct);
//                    } else {
//                        Toasty.error(getApplicationContext(),"Không tìm thấy sản phẩm nào",2000).show();
//
//                    }
//                }catch (Exception e){
//                    System.out.println(e.toString());
//                }
                try {
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
                } catch (Exception e){
                    System.out.println(e.toString());

                }

            }

            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {
                Log.d("giatri1", "onResponse: " + t.toString());
            }
        });
        progressBar.setVisibility(View.GONE);
            }
        }, 500);
    }


}
