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

import com.example.pandaapp.view.FavoriteActivity;
import com.example.pandaapp.view.LoginActivity;
import com.example.pandaapp.Models.Account;
import com.example.pandaapp.R;
import com.example.pandaapp.Util.GlobalApplication;
import com.example.pandaapp.view.OrderManagerCustomerActivity;

import es.dmoral.toasty.Toasty;


public class FragmentProfile extends Fragment {
    TextView textViewName, textViewUsername, textViewPhone, textViewAddress;
    Button btnLogout;
    GlobalApplication globalApplication;
    Account account;
    TextView textViewbtnFavorite;
    ImageView imageViewi_black_Profile;
    LinearLayout layoutMyOrder_Profile;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        globalApplication = (GlobalApplication) getActivity().getApplicationContext();
        if (globalApplication.account != null) {
            account = globalApplication.account;
            init(view);
            setProfileView(view);
            Listenner();
        }
        return view;
    }

    public void init(View view) {
        imageViewi_black_Profile=(ImageView) view.findViewById(R.id.img_back_black_Profile);
        textViewName = (TextView) view.findViewById(R.id.textviewName_Profile);
        textViewUsername = (TextView) view.findViewById(R.id.textviewUsename_Profile);
        textViewAddress = (TextView) view.findViewById(R.id.textviewAddress_Profile);
        textViewPhone = (TextView) view.findViewById(R.id.textviewPhone_Profile);
        btnLogout = (Button) view.findViewById(R.id.btnLogOut_Profile);
        textViewbtnFavorite = (TextView) view.findViewById(R.id.textview_Favorite_Profile);
        layoutMyOrder_Profile=(LinearLayout) view.findViewById(R.id.layoutMyOrder_Profile);

    }

    public void setProfileView(View view) {
        textViewName.setText(account.getName());
        textViewUsername.setText(account.getUsename());
        textViewPhone.setText(account.getPhone_number());
        textViewAddress.setText(account.getAddress());

    }

    public void Listenner() {
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
                Toasty.custom(getActivity(),"Đăng xuất",R.drawable.logo,R.color.color_red_text,2000,false,true).show();
            }
        });
        textViewbtnFavorite.setOnClickListener(new View.OnClickListener() {
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


    }


}
