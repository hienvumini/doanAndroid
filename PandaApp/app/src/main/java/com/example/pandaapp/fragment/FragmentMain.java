package com.example.pandaapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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

import com.example.pandaapp.Models.News;
import com.example.pandaapp.Retrofit2.APIUltils;
import com.example.pandaapp.Retrofit2.DataClient;
import com.example.pandaapp.Util.GlobalApplication;
import com.example.pandaapp.Util.LoadImage;
import com.example.pandaapp.adapter.AdapterNews;
import com.example.pandaapp.view.CartActivity;
import com.example.pandaapp.Models.Account;
import com.example.pandaapp.Models.Product;
import com.example.pandaapp.R;
import com.example.pandaapp.Util.ChangeActivity;
import com.example.pandaapp.adapter.AdapterProduct;
import com.example.pandaapp.view.NewsDetailActivity;
import com.example.pandaapp.view.SearchActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
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
    FragmentNews fragmentNews = new FragmentNews();
    FragmentProfile fragmentProfile = new FragmentProfile();
    ImageView imageViewFlipper;
    int limitProductPerPull = 20;
    boolean isScolling = false;
    int pastVisiableItems, totalItems, visiableItemCount;
    LinearLayoutManager linnerlayout;
    GridLayoutManager gridLayoutManager;
    ProgressBar progressBarLoadMore;
    boolean intial = true;
    int offset = 0, positionFlipper;
    SwipeRefreshLayout swipeRefreshLayout;
    ArrayList<News> listNews;
    View view;
    GlobalApplication globalApplication;
    ProgressBar progressBarLoading;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        try {
            init(view);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            getlistProduct();
        } catch (Exception e) {
            e.printStackTrace();
        }


        imgmyCart.setOnClickListener(onClickListenerCartItem);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(getActivity(), SearchActivity.class);
                    startActivity(intent);
                } catch (Exception e) {

                }

            }
        });
        listener();
        getlistnews();

        return view;
    }

    public void init(View view) throws Exception {
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

        imgmyCart = (ImageView) view.findViewById(R.id.imgcartMain);
        imgmyCart.setColorFilter(Color.WHITE);
        searchView = (TextView) view.findViewById(R.id.tv_Search_Search);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefrefresh_FragmentMain);
        listNews = new ArrayList<>();
        globalApplication = (GlobalApplication) getActivity().getApplicationContext();


    }

    private void getlistnews() {
        DataClient dataClient = APIUltils.getData();
        Call<ArrayList<News>> arrayListCall = dataClient.getNews(0);
        arrayListCall.enqueue(new Callback<ArrayList<News>>() {
            @Override
            public void onResponse(Call<ArrayList<News>> call, Response<ArrayList<News>> response) {

                listNews = response.body();
                try {
                    ActionViewflipper(view, listNews);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<News>> call, Throwable t) {

            }
        });
    }

    private void listener() {
        viewFlipper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                globalApplication.news = listNews.get(viewFlipper.getCurrentView().getId());
                Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
                startActivity(intent);

            }
        });

    }


    private void getlistProduct() throws Exception {
        DataClient dataClient = APIUltils.getData();
        Call<ArrayList<Product>> listProductCall = dataClient.getUpdateProduct(limitProductPerPull, listproduct.size());
        listProductCall.enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                if (response.body() != null) {
                    listproduct = response.body();
                    mainAdapter = new AdapterProduct(getActivity(), R.id.recycleviewLastlate_Main, listproduct);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(mainAdapter);
                    mainAdapter.notifyDataSetChanged();

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
                        try {
                            fetchData();
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
                listproduct.clear();
                mainAdapter.notifyDataSetChanged();
                try {
                    fetchData();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                swipeRefreshLayout.setRefreshing(false);
            }

        });



    }

    private void fetchData() throws Exception {


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                DataClient dataClient = APIUltils.getData();

                if (intial) {
                    offset = 0;

                } else {

                    offset = listproduct.size();
                }
                Call<ArrayList<Product>> arrayListCall = dataClient.getUpdateProduct(limitProductPerPull, listproduct.size());
                arrayListCall.enqueue(new Callback<ArrayList<Product>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                        ArrayList<Product> listadd;
                        listadd = response.body();
                        if (intial) {

                            listproduct = response.body();
                            mainAdapter.notifyDataSetChanged();
                            mainAdapter = new AdapterProduct(getActivity(), R.id.recycleview_ShopProduct, listproduct);
                            recyclerView.setLayoutManager(gridLayoutManager);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setAdapter(mainAdapter);
                            mainAdapter.notifyDataSetChanged();


                            intial = false;
                        } else {
                            if (listadd.size() > 0) {
                                listproduct.addAll(listadd);
                                Log.d("EEE", "That bai: " + listproduct.size());
                                mainAdapter.notifyDataSetChanged();


                            } else {
                                Toasty.custom(getActivity(), "Đã tải xong tất cả sản phẩm", R.drawable.ok, R.color.color_pink2, 2000, true, true).show();
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


    public void ActionViewflipper(View view, ArrayList<News> news) throws Exception {
        imageViewFlipper = new ImageView(getActivity());
        for (int i = 0; i < news.size(); i++) {
            positionFlipper = i;
            imageViewFlipper = new ImageView(getActivity());
            LoadImage.getImageInServer(getActivity(), news.get(i).getImageNews(), imageViewFlipper);
            imageViewFlipper.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageViewFlipper);

            imageViewFlipper.setId(i);


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
