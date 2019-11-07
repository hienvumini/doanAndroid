package com.example.pandaapp.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.pandaapp.Models.Account;
import com.example.pandaapp.Util.CacheUltils;
import com.example.pandaapp.Util.FragmentUtils;
import com.example.pandaapp.view.AddProductActivity;
import com.example.pandaapp.view.ListProductShopActivity;
import com.example.pandaapp.view.LoginActivity;
import com.example.pandaapp.R;
import com.example.pandaapp.Util.GlobalApplication;
import com.example.pandaapp.view.MainActivity;

import java.io.File;


public class FragmentShopProfile extends Fragment implements View.OnClickListener {
    LinearLayout linearAddProduct, linearRevenue, linearProductList, linearOnWay, linearReceived, linearCancel, linearProfile;
    Button btnLogoutShop;
    ImageView img_back_black;
    int REQUEST_CODE_ADDPRODUCT = 112;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop_profile, container, false);
        init(view);
        listenerEvent(view);
        return view;
    }

    private void listenerEvent(View view) {
        linearOnWay.setOnClickListener(this);
        linearReceived.setOnClickListener(this);
        linearCancel.setOnClickListener(this);
        linearAddProduct.setOnClickListener(this);
        linearProductList.setOnClickListener(this);
        linearRevenue.setOnClickListener(this);
        linearProfile.setOnClickListener(this);
        btnLogoutShop.setOnClickListener(this);
        img_back_black.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layoutOnWay_ProfileShop:
                Toast.makeText(getActivity(), "OnWay", Toast.LENGTH_SHORT).show();
                break;
            case R.id.layoutReceived_ProfileShop:
                Toast.makeText(getActivity(), "ReceiVed ", Toast.LENGTH_SHORT).show();
                break;
            case R.id.layoutCancel_ProfileShop:
                Toast.makeText(getActivity(), "Cancel", Toast.LENGTH_SHORT).show();
                break;
            case R.id.layoutAddProduct_ProfileShop:
                Toast.makeText(getActivity(), "Add Product", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), AddProductActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADDPRODUCT);

                break;
            case R.id.layoutRevenue_ProfileShop:
                Toast.makeText(getActivity(), "Doanh thu", Toast.LENGTH_SHORT).show();
                break;
            case R.id.layoutListProduct_ProfileShop:

                intent = new Intent(getActivity(), ListProductShopActivity.class);
                startActivity(intent);
                break;
            case R.id.layoutProfileInfomation_ProfileShop:
                Toast.makeText(getActivity(), "THông tin tài khoản", Toast.LENGTH_SHORT).show();
                FragmentUtils.openFragment((new FragmentProfile()), getActivity().getSupportFragmentManager(), R.id.frameProfile_ProfileShop);
                break;
            case R.id.btnLogOut_ProfileShop:
                intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
                GlobalApplication globalApplication = (GlobalApplication) getActivity().getApplicationContext();


                break;
            case R.id.img_back_black:
//                intent=new Intent(getActivity(), MainActivity.class);
//                startActivity(intent);
//                getActivity().finish();
                // getFragmentManager().popBackStack();
                ((MainActivity) getActivity()).changeNavigationBottomto(1);


                break;

        }

    }

    private void init(View view) {
        linearAddProduct = (LinearLayout) view.findViewById(R.id.layoutAddProduct_ProfileShop);
        linearOnWay = (LinearLayout) view.findViewById(R.id.layoutOnWay_ProfileShop);
        linearReceived = (LinearLayout) view.findViewById(R.id.layoutReceived_ProfileShop);
        linearCancel = (LinearLayout) view.findViewById(R.id.layoutCancel_ProfileShop);
        linearProductList = (LinearLayout) view.findViewById(R.id.layoutListProduct_ProfileShop);
        linearRevenue = (LinearLayout) view.findViewById(R.id.layoutRevenue_ProfileShop);
        linearProfile = (LinearLayout) view.findViewById(R.id.layoutProfileInfomation_ProfileShop);
        btnLogoutShop = (Button) view.findViewById(R.id.btnLogOut_ProfileShop);
        img_back_black = (ImageView) view.findViewById(R.id.img_back_black);
    }


}
