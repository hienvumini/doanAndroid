package com.example.pandaapp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pandaapp.Models.Product;
import com.example.pandaapp.R;
import com.example.pandaapp.Retrofit2.APIUltils;
import com.example.pandaapp.Retrofit2.DataClient;
import com.example.pandaapp.Util.GlobalApplication;
import com.example.pandaapp.adapter.AdapterProduct;

import org.json.JSONArray;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListProductShopActivity extends AppCompatActivity {
    RecyclerView recyclerViewListProduct;
    ArrayList<Product> listProduct;
    AdapterProduct adapterProduct;
    Spinner spinner_sort;
    int idshop;
    GlobalApplication globalApplication;
    ImageView imageViewcatagory_back;
    SwipeRefreshLayout swipeRefreshLayout;
    GridLayoutManager layoutManager;
    int limit = 10;
    int offset = 0;
    boolean intial = true;
    boolean isScolling = false;
    ProgressBar progressBar;
    int visiableItemCount, totalItems, pastVisiableItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_product_shop);
        try {
            init();
            fetchData(3);
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
                            fetchData(1);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        //
                    }
                } else {




                }

            }
        });
        spinner_sort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                try {

                    fetchData(position);
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
                Call<ArrayList<Product>> arrayListCall = dataClient.getProductShop(idshop, limit,offset,sortID);
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


    private void init() throws Exception {
        progressBar = (ProgressBar) findViewById(R.id.processbar_ProductShop);
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
        spinner_sort=(Spinner) findViewById(R.id.sort_ProductShop);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.array_sort, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner_sort.setBackgroundColor(Color.TRANSPARENT);
        spinner_sort.setAdapter(adapter);


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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_context_product_shop,menu);

    }
}
