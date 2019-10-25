package com.example.pandaapp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.Toolbar;
import android.widget.ViewFlipper;

import com.example.pandaapp.CartActivity;
import com.example.pandaapp.FragmentCart;
import com.example.pandaapp.FragmentCategory;
import com.example.pandaapp.FragmentProfile;
import com.example.pandaapp.FragmentSearch;
import com.example.pandaapp.Models.Account;
import com.example.pandaapp.Models.Product;
import com.example.pandaapp.R;
import com.example.pandaapp.adapter.MainAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    List<Product> ListProduct;
    SearchView searchView;
    ArrayList<Product> listproduct;
    MainAdapter mainAdapter;
    RecyclerView recyclerView;
    ViewFlipper viewFlipper;
    ImageView imgmyCart;
    Account account = new Account();
    BottomNavigationView nav_bottom_MainActivity;
    FragmentCategory fragmentCategory = new FragmentCategory();
    FragmentSearch fragmentSearch = new FragmentSearch();
    FragmentProfile fragmentProfile = new FragmentProfile();
    FragmentCart fragmentCart = new FragmentCart();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        account = (Account) intent.getSerializableExtra("account");
        Toast.makeText(this, "Chào mừng " + account.getName(), Toast.LENGTH_SHORT).show();
        fakedata();
        init();
        recyclerView.setAdapter(mainAdapter);
        imgmyCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment(fragmentCart);
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this, "say hello", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                Toast.makeText(MainActivity.this, "Abc", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment(fragmentSearch);
            }
        });
        


    }

    public void fakedata() {
        listproduct = new ArrayList<>();
        ArrayList<String> manganh = new ArrayList<>();
        manganh.add("https://cf.shopee.vn/file/b094ce2dc84d13b302e147e1b3cfa6d8");
        manganh.add("https://cf.shopee.vn/file/b094ce2dc84d13b302e147e1b3cfa6d8");
        manganh.add("https://cf.shopee.vn/file/b094ce2dc84d13b302e147e1b3cfa6d8");
        manganh.add("https://cf.shopee.vn/file/b094ce2dc84d13b302e147e1b3cfa6d8");
        listproduct.add(new Product("Ao 1", 150000, "Tu", manganh));
        listproduct.add(new Product("Ao 2", 150000, "Tu", manganh));
        listproduct.add(new Product("Ao 3", 150000, "Tu", manganh));
        listproduct.add(new Product("Ao 4", 150000, "Tu", manganh));
        listproduct.add(new Product("Ao 5", 150000, "Tu", manganh));

    }

    public void init() {
        nav_bottom_MainActivity = (BottomNavigationView) findViewById(R.id.ctNavigationbotton);
        nav_bottom_MainActivity.setOnNavigationItemSelectedListener(categoryFragmentListennerItem);
        mainAdapter = new MainAdapter(getApplicationContext(), R.id.recycleviewLastlate, listproduct);
        recyclerView = (RecyclerView) findViewById(R.id.recycleviewLastlate);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        LinearLayoutManager linnerlayout
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        viewFlipper = (ViewFlipper) findViewById(R.id.viewflipperquangcao);
        ActionViewflipper();
        imgmyCart = (ImageView) findViewById(R.id.imgcartMain);
        searchView = (SearchView) findViewById(R.id.SearchView);
    }

    public void ActionViewflipper() {

        ArrayList<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://cdn.tgdd.vn/2019/10/banner/V17-800-300-800x300-(1).png");
        mangquangcao.add("https://cdn.tgdd.vn/2019/10/banner/800-300-800x300-(2).png");
        mangquangcao.add("https://cdn.tgdd.vn/2019/10/banner/800-300-800x300-(7).png");
        mangquangcao.add("https://cdn.tgdd.vn/2019/10/banner/Realme-5-Teaser-800-300-800x300.png");
        mangquangcao.add("https://cdn.tgdd.vn/2019/10/banner/800-300-800x300-(1).png");
        mangquangcao.add("https://cdn.tgdd.vn/2019/10/banner/Phu-kien-online--800-300-800x300.png");

        for (int i = 0; i < mangquangcao.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(mangquangcao.get(i))
                    .into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);


        }
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);


        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);


    }


    private void openFragment(final Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.framMainActivity, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    public BottomNavigationView.OnNavigationItemSelectedListener categoryFragmentListennerItem = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment fragmentselect = null;
            switch (menuItem.getItemId()) {
                case R.id.menu_nav_home:
                    getSupportFragmentManager().popBackStack();
                    break;
                case R.id.menu_nav_cate:
                    fragmentselect = new FragmentCategory();
                    openFragment(fragmentselect);
                    break;
                case R.id.menu_nav_seach:
                    fragmentselect = new FragmentSearch();
                    openFragment(fragmentselect);
                    break;
                case R.id.menu_nav_profile:
                    fragmentselect = new FragmentProfile();
                    openFragment(fragmentselect);

                    break;


            }

            return false;
        }
    };
}


