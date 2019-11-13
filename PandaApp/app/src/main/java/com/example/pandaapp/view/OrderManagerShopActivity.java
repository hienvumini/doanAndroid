package com.example.pandaapp.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import com.example.pandaapp.R;
import com.example.pandaapp.adapter.AdapterOrderShop;
import com.example.pandaapp.adapter.AdapterTabOrder;
import com.example.pandaapp.fragment.FragmentOrderShop;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class OrderManagerShopActivity extends AppCompatActivity implements FragmentOrderShop.callbackActivity, AdapterOrderShop.onlistenerAdapterOrder {
    TabLayout tabLayout;
    ViewPager viewPager;
    ArrayList<String> listTab;

    ArrayList<Fragment> listFragment;
    AdapterTabOrder adapterTabOrder;
    int positonTab;
    int REQUEST_DETAILORDER = 112;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_manager_shop);
        init();
        creatDataTab();
        viewPager.setAdapter(adapterTabOrder);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                positonTab = tab.getPosition();
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

        listFragment.add(new FragmentOrderShop());
        listFragment.add(new FragmentOrderShop());
        listFragment.add(new FragmentOrderShop());
        listFragment.add(new FragmentOrderShop());


    }

    @Override
    public int gettab() {
        return positonTab;
    }


    @Override
    public void startActivityforResultListener() {
        Intent intent = new Intent(this, OrderDetail.class);
        intent.putExtra("status",positonTab);
        startActivityForResult(intent, REQUEST_DETAILORDER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_DETAILORDER && resultCode == RESULT_OK) {
            viewPager.setAdapter(adapterTabOrder);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
