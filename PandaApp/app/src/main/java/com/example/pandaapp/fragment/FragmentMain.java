package com.example.pandaapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;
import android.widget.ViewFlipper;

import com.example.pandaapp.Retrofit2.APIUltils;
import com.example.pandaapp.Retrofit2.DataClient;
import com.example.pandaapp.view.CartActivity;
import com.example.pandaapp.Models.Account;
import com.example.pandaapp.Models.Product;
import com.example.pandaapp.R;
import com.example.pandaapp.Util.ChangeActivity;
import com.example.pandaapp.adapter.AdapterProduct;
import com.example.pandaapp.view.SearchActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentMain extends Fragment {
    Toolbar toolbar;
    List<Product> ListProduct;
    TextView searchView;
    ArrayList<Product> listproduct;
    AdapterProduct mainAdapter;
    RecyclerView recyclerView;
    ViewFlipper viewFlipper;
    ImageView imgmyCart;
    Account account = new Account();
    BottomNavigationView nav_bottom_MainActivity;
    FragmentCategory fragmentCategory = new FragmentCategory();
    FragmentSearch fragmentSearch = new FragmentSearch();
    FragmentProfile fragmentProfile = new FragmentProfile();
    FragmentCart fragmentCart = new FragmentCart();
    int limitProductPerPull = 6;
    boolean isScolling = false;
    int pastVisiableItems, totalItems, visiableItemCount, prevous_total, view_threshold = 10;
    LinearLayoutManager linnerlayout;
    GridLayoutManager gridLayoutManager;
    ProgressBar progressBarLoadMore;
    boolean intial = true;
    int offset = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        init(view);

        getlistProduct();


        imgmyCart.setOnClickListener(onClickListenerCartItem);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }


    private void getlistProduct() {
        DataClient dataClient = APIUltils.getData();
        Call<ArrayList<Product>> listProductCall = dataClient.getUpdateProduct(limitProductPerPull,listproduct.size());
        listProductCall.enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                if (response.body() != null) {
                    listproduct = response.body();
                    mainAdapter = new AdapterProduct(getActivity(), R.id.recycleviewLastlate_Main, listproduct);
                    recyclerView.setAdapter(mainAdapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {

            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                pastVisiableItems = gridLayoutManager.findFirstVisibleItemPosition();
                visiableItemCount = gridLayoutManager.getChildCount();
                totalItems = gridLayoutManager.getItemCount();

//                if (isScolling && (visiableItemCount + pastVisiableItems == totalItems)) {
//                    fetchData();
//                }
                if (dy > 0) {

                    if (isScolling && (visiableItemCount + pastVisiableItems == totalItems)) {

                        isScolling = false;
                        progressBarLoadMore.setVisibility(View.VISIBLE);
                        fetchData();

                        //
                    }
                } else {


                }
            }
        });


    }

    private void fetchData() {
        Toast.makeText(getActivity(), "Add more", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                DataClient dataClient = APIUltils.getData();

                if (intial) {
                    offset = 0;

                } else {

                    offset = listproduct.size();
                }
                Call<ArrayList<Product>> arrayListCall = dataClient.getUpdateProduct(limitProductPerPull,listproduct.size());
                arrayListCall.enqueue(new Callback<ArrayList<Product>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                        ArrayList<Product> listadd;
                        listadd = response.body();
                        if (intial) {

                            listproduct = response.body();
                            mainAdapter.notifyDataSetChanged();
                            mainAdapter = new AdapterProduct(getActivity(), R.id.recycleview_ShopProduct, listproduct);
                            recyclerView.setAdapter(mainAdapter);
                            Toast.makeText(getActivity(), listproduct.size() + "", Toast.LENGTH_SHORT).show();
                            intial = false;
                        } else {
                            if (listadd.size() > 0) {
                                listproduct.addAll(listadd);
                                Log.d("EEE", "That bai: " + listproduct.size());
                                mainAdapter.notifyDataSetChanged();
                                mainAdapter = new AdapterProduct(getActivity(), R.id.recycleview_ShopProduct, listproduct);

                                recyclerView.setAdapter(mainAdapter);

                                Toast.makeText(getActivity(), listproduct.size() + "", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getActivity(), "Đã tải xong tất cả", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Product>> call, Throwable t) {
                        Log.d("BBB", "That bai: " + t.toString());

                    }
                });
                progressBarLoadMore.setVisibility(View.GONE);

            }
        }, 500);

    }


    public void init(View view) {
        Context context;
        progressBarLoadMore = (ProgressBar) view.findViewById(R.id.progressBar_LoadMore_FragmentMain);
        //manager = new LinearLayoutManager(getActivity());
        listproduct = new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.recycleviewLastlate_Main);
        mainAdapter = new AdapterProduct(getActivity(), R.id.recycleviewLastlate_Main, listproduct);
        gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        linnerlayout
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        viewFlipper = (ViewFlipper) view.findViewById(R.id.viewflipperquangcao);
        ActionViewflipper(view);
        imgmyCart = (ImageView) view.findViewById(R.id.imgcartMain);
        searchView = (TextView) view.findViewById(R.id.tv_Search_Search);

    }

    public void ActionViewflipper(View view) {

        ArrayList<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://cdn.tgdd.vn/2019/10/banner/V17-800-300-800x300-(1).png");
        mangquangcao.add("https://cdn.tgdd.vn/2019/10/banner/800-300-800x300-(2).png");
        mangquangcao.add("https://cdn.tgdd.vn/2019/10/banner/800-300-800x300-(7).png");
        mangquangcao.add("https://cdn.tgdd.vn/2019/10/banner/Realme-5-Teaser-800-300-800x300.png");
        mangquangcao.add("https://cdn.tgdd.vn/2019/10/banner/800-300-800x300-(1).png");
        mangquangcao.add("https://cdn.tgdd.vn/2019/10/banner/Phu-kien-online--800-300-800x300.png");

        for (int i = 0; i < mangquangcao.size(); i++) {
            ImageView imageView = new ImageView(getActivity());
            Picasso.with(getActivity()).load(mangquangcao.get(i))
                    .into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);


        }
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_out_right);
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);


    }

    public ImageView.OnClickListener onClickListenerCartItem = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ChangeActivity.toActivity(getActivity(), CartActivity.class);
        }
    };


}
