package com.example.pandaapp.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toolbar;
import android.widget.ViewFlipper;

import com.example.pandaapp.Retrofit2.APIUltils;
import com.example.pandaapp.Retrofit2.DataClient;
import com.example.pandaapp.Util.FragmentUtils;
import com.example.pandaapp.view.CartActivity;
import com.example.pandaapp.Models.Account;
import com.example.pandaapp.Models.Product;
import com.example.pandaapp.R;
import com.example.pandaapp.Util.ChangeActivity;
import com.example.pandaapp.adapter.AdapterProduct;
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
    SearchView searchView;
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
    int limitProductPerPull = 10;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        init(view);

        getlistProduct();
        recyclerView.setAdapter(mainAdapter);
        imgmyCart.setOnClickListener(onClickListenerCartItem);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentUtils.openFragment(fragmentSearch, getActivity().getSupportFragmentManager(), R.id.ctNavigationbotton);
            }
        });


        return view;
    }

    private void getlistProduct() {
        DataClient dataClient = APIUltils.getData();
        Call<ArrayList<Product>> listProductCall = dataClient.getUpdateProduct(limitProductPerPull);
        listProductCall.enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                if (response.body() != null) {
                    listproduct = response.body();
                    mainAdapter=new AdapterProduct(getActivity(),R.id.recycleviewLastlate_Main,listproduct);
                    recyclerView.setAdapter(mainAdapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {

            }
        });
    }

    public void fakedata() {

        ArrayList<String> manganh = new ArrayList<>();
        manganh.add("https://cf.shopee.vn/file/b094ce2dc84d13b302e147e1b3cfa6d8");
        manganh.add("https://cf.shopee.vn/file/b094ce2dc84d13b302e147e1b3cfa6d8");
        manganh.add("https://cf.shopee.vn/file/b094ce2dc84d13b302e147e1b3cfa6d8");
        manganh.add("https://cf.shopee.vn/file/b094ce2dc84d13b302e147e1b3cfa6d8");
        listproduct.add(new Product("Ao 1", 150000, "Tu", manganh, "Đây là áo 1"));
        listproduct.add(new Product("Ao 2", 150000, "Tu", manganh, "Đây là áo 1"));
        listproduct.add(new Product("Ao 3", 150000, "Tu", manganh, "Đây là áo 1"));
        listproduct.add(new Product("Ao 4", 150000, "Tu", manganh, "Đây là áo 1"));
        listproduct.add(new Product("Ao 5", 150000, "Tu", manganh, "Đây là áo 1"));

    }

    public void init(View view) {
        listproduct = new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.recycleviewLastlate_Main);
        mainAdapter = new AdapterProduct(getActivity(), R.id.recycleviewLastlate_Main, listproduct);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        LinearLayoutManager linnerlayout
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        viewFlipper = (ViewFlipper) view.findViewById(R.id.viewflipperquangcao);
        ActionViewflipper(view);
        imgmyCart = (ImageView) view.findViewById(R.id.imgcartMain);
        searchView = (SearchView) view.findViewById(R.id.SearchView);

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
