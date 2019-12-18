package com.example.pandaapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.pandaapp.R;
import com.example.pandaapp.adapter.AdapterOrderCustomer;
import com.example.pandaapp.adapter.AdapterOrderShop;
import com.example.pandaapp.adapter.AdapterTabOrder;
import com.example.pandaapp.fragment.FragmentOrderCustomer;
import com.example.pandaapp.fragment.FragmentOrderShop;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class OrderManagerCustomerActivity extends AppCompatActivity implements FragmentOrderCustomer.callbackActivityCustomer, AdapterOrderCustomer.onlistenerAdapterOrderCustomer {
    TabLayout tabLayout;
    ViewPager viewPager;
    ArrayList<String> listTab;
    ImageView img_back_OrderCustomer;

    ArrayList<Fragment> listFragment;
    AdapterTabOrder adapterTabOrder;
    int positonTab;
    int REQUEST_DETAILORDER = 112;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_manager_customer);
        try {
            init();
            creatDataTab();
        } catch (NullPointerException e) {
            System.out.println("OrderManagerCustomerActivity: " + e.toString());

        } catch (Exception e) {
            System.out.println("OrderManagerCustomerActivity_E: " + e.toString());

        }
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
        img_back_OrderCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }


    private void init() throws NullPointerException, Exception {
        tabLayout = (TabLayout) findViewById(R.id.tablayoutStatus_OrderCustomer);
        viewPager = (ViewPager) findViewById(R.id.viewpagerStatus_OrderCustomer);
        img_back_OrderCustomer = (ImageView) findViewById(R.id.img_back_OrderCustomer);
        tabLayout.setupWithViewPager(viewPager, true);
        listTab = new ArrayList<>();
        listFragment = new ArrayList<>();
        adapterTabOrder = new AdapterTabOrder(getSupportFragmentManager(), listTab, listFragment);


    }

    private void creatDataTab() throws Exception {
        listTab.add("Chờ xác nhận");
        listTab.add("Đang giao");
        listTab.add("Đã nhận");
        listTab.add("Hủy");

        listFragment.add(new FragmentOrderCustomer());
        listFragment.add(new FragmentOrderCustomer());
        listFragment.add(new FragmentOrderCustomer());
        listFragment.add(new FragmentOrderCustomer());


    }

    @Override
    public int gettab() {
        return positonTab;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_DETAILORDER && resultCode == RESULT_OK) {
            viewPager.setAdapter(adapterTabOrder);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void startActivityforResultListener() {

        Intent intent = new Intent(this, OrderDetail.class);
        intent.putExtra("status", positonTab);
        startActivityForResult(intent, REQUEST_DETAILORDER);

    }
}
