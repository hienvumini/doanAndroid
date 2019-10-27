package com.example.pandaapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.pandaapp.LoginActivity;
import com.example.pandaapp.Models.Account;
import com.example.pandaapp.R;
import com.example.pandaapp.Util.GlobalApplication;


public class FragmentProfile extends Fragment {
    TextView textViewName,textViewUsername,textViewPhone,textViewAddress;
    Button btnLogout;
    GlobalApplication globalApplication;
    Account account;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        globalApplication = (GlobalApplication) getActivity().getApplicationContext();
        if (globalApplication.account != null) {
            account = globalApplication.account;
            init(view);
            setProfileView(view);
            Listener();
        }
        return view;
    }
    public void init(View view){
        textViewName=(TextView)view.findViewById(R.id.textviewNameFull_Profile);
        textViewUsername=(TextView)view.findViewById(R.id.textviewUsernameFull_Profile);
        textViewAddress=(TextView)view.findViewById(R.id.textviewAddress_Profile);
        textViewPhone=(TextView)view.findViewById(R.id.textviewPhone_Profile);
        btnLogout=(Button) view.findViewById(R.id.btnLogOut_Profile);

    }
    public void setProfileView(View view){
        textViewName.setText(account.getName());
        textViewUsername.setText(account.getUsename());
        textViewPhone.setText(account.getPhone_number());
        textViewAddress.setText(account.getAddress());

    }
    public void Listener(){
btnLogout.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
});

    }


}
