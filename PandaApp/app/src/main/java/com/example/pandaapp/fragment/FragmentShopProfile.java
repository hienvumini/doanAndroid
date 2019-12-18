package com.example.pandaapp.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.pandaapp.Util.ChangeActivity;
import com.example.pandaapp.Util.FragmentUtils;
import com.example.pandaapp.view.AddProductActivity;
import com.example.pandaapp.view.ListProductShopActivity;
import com.example.pandaapp.view.LoginActivity;
import com.example.pandaapp.R;
import com.example.pandaapp.Util.GlobalApplication;
import com.example.pandaapp.view.MainActivity;
import com.example.pandaapp.view.OpenShopActivity;
import com.example.pandaapp.view.OrderManagerShopActivity;
import com.example.pandaapp.view.RevenueActivity;
import com.facebook.login.LoginManager;

import es.dmoral.toasty.Toasty;


public class FragmentShopProfile extends Fragment implements View.OnClickListener {
    LinearLayout linearAddProduct, linearRevenue, linearProductList, linearProcess, linearOnWay, linearReceived, linearCancel, linearProfile, linearEditShop;
    Button btnLogoutShop;
    ImageView img_back_black;
    int REQUEST_CODE_ADDPRODUCT = 112;
    int status;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop_profile, container, false);
        init(view);
        listenerEvent(view);
        return view;
    }

    private void listenerEvent(View view) {
        linearProcess.setOnClickListener(this);
        linearOnWay.setOnClickListener(this);
        linearReceived.setOnClickListener(this);
        linearCancel.setOnClickListener(this);
        linearAddProduct.setOnClickListener(this);
        linearProductList.setOnClickListener(this);
        linearRevenue.setOnClickListener(this);
        linearProfile.setOnClickListener(this);
        btnLogoutShop.setOnClickListener(this);
        img_back_black.setOnClickListener(this);
        linearEditShop.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.layoutVeri_ProfileShop:
                status = 0;

                ChangeActivity.toActivity(getActivity(), OrderManagerShopActivity.class, "status", status);
                break;
            case R.id.layoutOnWay_ProfileShop:
                status = 1;

                ChangeActivity.toActivity(getActivity(), OrderManagerShopActivity.class, "status", status);
                break;
            case R.id.layoutReceived_ProfileShop:
                status = 2;

                ChangeActivity.toActivity(getActivity(), OrderManagerShopActivity.class, "status", status);
                break;
            case R.id.layoutCancel_ProfileShop:
                status = 3;

                ChangeActivity.toActivity(getActivity(), OrderManagerShopActivity.class, "status", status);
                break;
            case R.id.layoutAddProduct_ProfileShop:

                Intent intent = new Intent(getActivity(), AddProductActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADDPRODUCT);

                break;
            case R.id.layoutRevenue_ProfileShop:
                intent = new Intent(getActivity(), RevenueActivity.class);
                startActivity(intent);

                break;
            case R.id.layoutListProduct_ProfileShop:

                intent = new Intent(getActivity(), ListProductShopActivity.class);
                startActivity(intent);
                break;
            case R.id.layoutProfileInfomation_ProfileShop:

                FragmentUtils.openFragment((new FragmentProfile()), getActivity().getSupportFragmentManager(), R.id.frameProfile_ProfileShop);
                break;
            case R.id.btnLogOut_ProfileShop:
                intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
                GlobalApplication globalApplication = (GlobalApplication) getActivity().getApplicationContext();
                Toasty.custom(getActivity(), "Đăng xuất", R.drawable.logo, R.color.color_red_text, 2000, false, true).show();
                LoginManager.getInstance().logOut();
                break;

            case R.id.layoutEditShop_Profile:
                Intent intent1 = new Intent(getActivity(), OpenShopActivity.class);
                intent1.putExtra("mode", 2);
                startActivity(intent1);
                break;

            case R.id.img_back_black:
                ((MainActivity) getActivity()).changeNavigationBottomto(1);


                break;


        }

    }

    private void init(View view) {
        linearAddProduct = (LinearLayout) view.findViewById(R.id.layoutAddProduct_ProfileShop);
        linearProcess = (LinearLayout) view.findViewById(R.id.layoutVeri_ProfileShop);
        linearOnWay = (LinearLayout) view.findViewById(R.id.layoutOnWay_ProfileShop);
        linearReceived = (LinearLayout) view.findViewById(R.id.layoutReceived_ProfileShop);
        linearCancel = (LinearLayout) view.findViewById(R.id.layoutCancel_ProfileShop);
        linearProductList = (LinearLayout) view.findViewById(R.id.layoutListProduct_ProfileShop);
        linearRevenue = (LinearLayout) view.findViewById(R.id.layoutRevenue_ProfileShop);
        linearProfile = (LinearLayout) view.findViewById(R.id.layoutProfileInfomation_ProfileShop);
        btnLogoutShop = (Button) view.findViewById(R.id.btnLogOut_ProfileShop);
        img_back_black = (ImageView) view.findViewById(R.id.img_back_black);
        linearEditShop = (LinearLayout) view.findViewById(R.id.layoutEditShop_Profile);
    }


}
