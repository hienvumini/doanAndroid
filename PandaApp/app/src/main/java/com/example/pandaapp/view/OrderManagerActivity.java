package com.example.pandaapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.pandaapp.R;
import com.example.pandaapp.adapter.AdapterTabOrder;
import com.example.pandaapp.fragment.FragmentCancel_OrderShop;
import com.example.pandaapp.fragment.FragmentOnWay_OrderShop;
import com.example.pandaapp.fragment.FragmentOnprocessing_OrderShop;
import com.example.pandaapp.fragment.FragmentSend_OrderShop;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.HashMap;

public class OrderManagerActivity extends AppCompatActivity implements FragmentOnprocessing_OrderShop.callbackActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    ArrayList<String> listTab;

    ArrayList<Fragment> listFragment;
    AdapterTabOrder adapterTabOrder;
    int positonTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_manager);
        init();
        creatDataTab();
        viewPager.setAdapter(adapterTabOrder);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                positonTab=tab.getPosition();
                viewPager.removeView(viewPager);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {


            }
        });


    }


    private void init() {
        tabLayout = (TabLayout) findViewById(R.id.tablayoutStatus_Order);
        viewPager = (ViewPager) findViewById(R.id.viewpagerStatus_Order);
        tabLayout.setupWithViewPager(viewPager, true);
        listTab = new ArrayList<>();
        listFragment = new ArrayList<>();
        adapterTabOrder = new AdapterTabOrder(getSupportFragmentManager(), listTab, listFragment);


    }

    private void creatDataTab() {
        listTab.add("Chờ xác nhận");
        listTab.add("Đang giao");
        listTab.add("Đã giao");
        listTab.add("Hủy");

        listFragment.add(new FragmentOnprocessing_OrderShop());
        listFragment.add(new FragmentOnprocessing_OrderShop());
        listFragment.add(new FragmentOnprocessing_OrderShop());
        listFragment.add(new FragmentOnprocessing_OrderShop());


    }

    @Override
    public int gettab() {
       return positonTab;
    }
}
