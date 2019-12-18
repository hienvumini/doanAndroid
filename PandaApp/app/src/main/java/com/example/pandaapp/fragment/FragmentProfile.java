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
import android.widget.TextView;

import com.example.pandaapp.view.ChangePassActivity;
import com.example.pandaapp.view.EditInfoAccountActivity;
import com.example.pandaapp.view.FavoriteActivity;
import com.example.pandaapp.view.LoginActivity;
import com.example.pandaapp.Models.Account;
import com.example.pandaapp.R;
import com.example.pandaapp.Util.GlobalApplication;
import com.example.pandaapp.view.OpenShopActivity;
import com.example.pandaapp.view.OrderManagerCustomerActivity;
import com.facebook.login.LoginManager;

import es.dmoral.toasty.Toasty;


public class FragmentProfile extends Fragment {
    TextView textViewName, textViewUsername, textViewPhone, textViewAddress;
    Button btnLogout;
    GlobalApplication globalApplication;
    Account account;
    LinearLayout linearLayoutFavorite;
    ImageView imageViewi_black_Profile;
    LinearLayout layoutMyOrder_Profile, layoutLayoutEditProdile,layoutOpenShop,layoutChangePass;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        globalApplication = (GlobalApplication) getActivity().getApplicationContext();
        if (globalApplication.account != null) {
            account = globalApplication.account;
            try {
                init(view);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                setProfileView(view);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Listenner();
        }
        return view;
    }

    public void init(View view) throws Exception {
        imageViewi_black_Profile = (ImageView) view.findViewById(R.id.img_back_black_Profile);
        textViewName = (TextView) view.findViewById(R.id.textviewName_Profile);
        textViewUsername = (TextView) view.findViewById(R.id.textviewUsename_Profile);
        textViewAddress = (TextView) view.findViewById(R.id.textviewAddress_Profile);
        textViewPhone = (TextView) view.findViewById(R.id.textviewPhone_Profile);
        btnLogout = (Button) view.findViewById(R.id.btnLogOut_Profile);
        linearLayoutFavorite = (LinearLayout) view.findViewById(R.id.layoutFavorite_Profile);
        layoutMyOrder_Profile = (LinearLayout) view.findViewById(R.id.layoutMyOrder_Profile);
        layoutLayoutEditProdile =(LinearLayout) view.findViewById(R.id.layoutEdit_Profile);
        layoutOpenShop=(LinearLayout) view.findViewById(R.id.layoutOpenShop_Profile);
        layoutChangePass=(LinearLayout) view.findViewById(R.id.layoutChangePasss_Profile);
        if (globalApplication.account.getRoleId()==2){
            layoutOpenShop.setVisibility(View.GONE);

        }

    }

    public void setProfileView(View view) throws NullPointerException {
        textViewName.setText(account.getName());
        textViewUsername.setText(account.getUsename());
        textViewPhone.setText(account.getPhone_number());
        textViewAddress.setText(account.getAddress());

    }

    public void Listenner() throws NullPointerException {
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);

                Toasty.custom(getActivity(), "Đăng xuất", R.drawable.logo, R.color.color_red_text, 2000, false, true).show();
                LoginManager.getInstance().logOut();
                getActivity().finish();


            }
        });
        linearLayoutFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FavoriteActivity.class);
                startActivity(intent);
            }
        });
        imageViewi_black_Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });
        layoutMyOrder_Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), OrderManagerCustomerActivity.class);
                getActivity().startActivity(intent);

            }
        });
        layoutLayoutEditProdile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), EditInfoAccountActivity.class);
                startActivity(intent);
            }
        });
        layoutOpenShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), OpenShopActivity.class);
                intent.putExtra("mode",1);
                startActivity(intent);

            }
        });
        layoutChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), ChangePassActivity.class);

                startActivity(intent);

            }
        });


    }


}
