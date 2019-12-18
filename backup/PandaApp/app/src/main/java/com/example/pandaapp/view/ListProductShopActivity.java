package com.example.pandaapp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
    SwipeRefreshLayout swipeRefreshLayout;
    GridLayoutManager  layoutManager;
    int limit=10;
    int offset=0;
    boolean intial = true;
    boolean isScolling = false;
    ProgressBar progressBar;
    int visiableItemCount,totalItems,pastVisiableItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_product_shop);
        try {
            init();
            getProductShop();
            onClickListener();
        } catch (Exception e) {

            System.out.println(e.toString());
        }
        listener();
    }

    private void listener() {
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

                } catch (Exception e) {
                    e.printStackTrace();
                }

                //
            }
        } else {




        }
    }
});
    }


    private void init() throws Exception {
        progressBar=(ProgressBar) findViewById(R.id.processbar_ProductShop);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefreshShop);
        imageViewcatagory_back = (ImageView) findViewById(R.id.catagory_back_ProductShop);
        recyclerViewListProduct = (RecyclerView) findViewById(R.id.recycleview_ShopProduct);
        listProduct = new ArrayList<>();
        adapterProduct = new AdapterProduct(this, R.id.recycleview_ShopProduct, listProduct);
         layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerViewListProduct.setLayoutManager(layoutManager);
        recyclerViewListProduct.setHasFixedSize(true);
        recyclerViewListProduct.setAdapter(adapterProduct);
        globalApplication = (GlobalApplication) getApplicationContext();
        idshop = globalApplication.account.getIdShop();


    }

    private void getProductShop() throws Exception {

        DataClient getproduct = APIUltils.getData();
        Call<ArrayList<Product>> stringCall = getproduct.getProductShop(idshop,limit,offset);
        stringCall.enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                Log.d("AAA", "onResponse: " + response.body());
                listProduct = response.body();
                Toast.makeText(ListProductShopActivity.this, "San pham: " + listProduct.size(), Toast.LENGTH_SHORT).show();
                adapterProduct = new AdapterProduct(getApplicationContext(), R.id.recycleview_ShopProduct, listProduct);
                adapterProduct.notifyDataSetChanged();
                recyclerViewListProduct.setAdapter(adapterProduct);
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {
                Log.d("BBB", "onResponse: " + t.toString());

            }
        });


    }

    public void onClickListener() throws Exception {
        imageViewcatagory_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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
}
