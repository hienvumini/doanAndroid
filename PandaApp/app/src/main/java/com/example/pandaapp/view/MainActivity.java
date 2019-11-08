package com.example.pandaapp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.Toolbar;
import android.widget.ViewFlipper;

import com.example.pandaapp.Util.FragmentUtils;
import com.example.pandaapp.Util.GlobalApplication;
import com.example.pandaapp.fragment.FragmentCart;
import com.example.pandaapp.fragment.FragmentCategory;
import com.example.pandaapp.fragment.FragmentMain;
import com.example.pandaapp.fragment.FragmentProfile;
import com.example.pandaapp.fragment.FragmentSearch;
import com.example.pandaapp.Models.Account;
import com.example.pandaapp.Models.Product;
import com.example.pandaapp.R;
import com.example.pandaapp.adapter.AdapterProduct;
import com.example.pandaapp.fragment.FragmentShopProfile;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterProduct.AdapterItemClickListener {

    Account account0 = new Account();
    BottomNavigationView nav_bottom_MainActivity;
    FragmentCategory fragmentCategory = new FragmentCategory();
    FragmentSearch fragmentSearch = new FragmentSearch();
    FragmentProfile fragmentProfile = new FragmentProfile();
    FragmentMain fragmentMain = new FragmentMain();
    FragmentShopProfile fragmentShopProfile = new FragmentShopProfile();
    GlobalApplication globalApplication;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        globalApplication = (GlobalApplication) getApplicationContext();
        FragmentUtils.openFragment(fragmentMain, getSupportFragmentManager(), R.id.framMainActivity);
        if (globalApplication.account != null) {
            account0 = globalApplication.account;


            nav_bottom_MainActivity = findViewById(R.id.ctNavigationbotton);
            nav_bottom_MainActivity.setOnNavigationItemSelectedListener(categoryFragmentListennerItem);
            nav_bottom_MainActivity.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
        } else {
            Toast.makeText(getApplicationContext(), "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();

        }


    }

    public BottomNavigationView.OnNavigationItemSelectedListener categoryFragmentListennerItem = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment fragmentselect = null;
            switch (menuItem.getItemId()) {
                case R.id.menu_nav_home:
                    fragmentselect = fragmentMain;
                    break;
                case R.id.menu_nav_cate:
                    fragmentselect = fragmentCategory;

                    break;
                case R.id.menu_nav_seach:
                    fragmentselect = fragmentSearch;

                    break;
                case R.id.menu_nav_profile:

                    if (account0.getRoleId() == 1) {
                        fragmentselect = fragmentProfile;
                    } else if (account0.getRoleId() == 2) {
                        fragmentselect = fragmentShopProfile;
                    }


                    break;


            }
            FragmentUtils.openFragment(fragmentselect, getSupportFragmentManager(), R.id.framMainActivity);

            return true;
        }
    };

    @Override
    public void onClick(View view, int position, boolean isLongClick) {

    }

    public void changeNavigationBottomto(int sttFragment) {
        nav_bottom_MainActivity.setSelectedItemId(R.id.menu_nav_home);

    }


}


