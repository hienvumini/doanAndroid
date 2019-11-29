package com.example.pandaapp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.pandaapp.Util.FragmentUtils;
import com.example.pandaapp.Util.GlobalApplication;
import com.example.pandaapp.fragment.FragmentCategory;
import com.example.pandaapp.fragment.FragmentMain;
import com.example.pandaapp.fragment.FragmentProfile;
import com.example.pandaapp.fragment.FragmentNews;
import com.example.pandaapp.Models.Account;
import com.example.pandaapp.R;
import com.example.pandaapp.fragment.FragmentShopProfile;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;

public class MainActivity extends AppCompatActivity  {

    Account account0 = new Account();
    BottomNavigationView nav_bottom_MainActivity;
    FragmentCategory fragmentCategory = new FragmentCategory();
    FragmentNews fragmentNews = new FragmentNews();
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
                case R.id.menu_nav_news:
                    fragmentselect = fragmentNews;

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



    public void changeNavigationBottomto(int sttFragment) {
        nav_bottom_MainActivity.setSelectedItemId(R.id.menu_nav_home);

    }



}


