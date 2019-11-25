package com.example.pandaapp.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pandaapp.Retrofit2.APIUltils;
import com.example.pandaapp.Retrofit2.DataClient;
import com.example.pandaapp.view.LoginActivity;
import com.example.pandaapp.Models.Account;
import com.example.pandaapp.R;
import com.example.pandaapp.Util.GlobalApplication;
import com.example.pandaapp.view.MainActivity;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentLogin extends Fragment {

    EditText edittextuser, edittextpass;
    Button btnlogin;
    ImageView btnfacebookLogin, btnGoogleLogin;
    String txtusername, txtpassword;
    TextView textViewSignupLogin;
    Account account = new Account();
    GlobalApplication globalApplication;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_login, container, false);
        super.onCreate(savedInstanceState);







        edittextuser = (EditText) view.findViewById(R.id.edittextusernameLogin);
        edittextpass = (EditText) view.findViewById(R.id.edittextpassLogin);
        btnlogin = (Button) view.findViewById(R.id.login_buttonLogin);
        btnfacebookLogin = (ImageView) view.findViewById(R.id.login_facebookLogin);
        btnGoogleLogin = (ImageView) view.findViewById(R.id.login_googleLogin);
        textViewSignupLogin = (TextView) view.findViewById(R.id.textview_sigupLogin);
        textViewSignupLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LoginActivity) getActivity()).toSignUpFragment();
            }
        });
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getdataformLogin();
                checkaccount(txtusername, txtpassword);
            }
        });
        return view;
    }

    public void getdataformLogin() {
        txtusername = edittextuser.getText().toString().trim();
        txtpassword = edittextpass.getText().toString().trim();


    }

    public void checkaccount(final String ussername, final String password) {
        DataClient processLogin = APIUltils.getData();
        Call<ArrayList<Account>> accountCall = processLogin.CheckAccount(ussername, password);
        accountCall.enqueue(new Callback<ArrayList<Account>>() {
            @Override
            public void onResponse(Call<ArrayList<Account>> call, Response<ArrayList<Account>> response) {
                if (response.body().size() > 0) {
                    Toasty.custom(getActivity(),"Đăng nhập thành công",R.drawable.ok,R.color.color_pink2,2000,false,true).show();
                    Log.d("AZ", "Dăng nhập: "+response.body());
                    ArrayList<Account> accountslist = response.body();
                    Account account = accountslist.get(0);
                    if (globalApplication == null) {
                        globalApplication = (GlobalApplication) getActivity().getApplicationContext();

                    }
                    globalApplication.account = account;
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<Account>> call, Throwable t) {
                Log.d("BBBB", "That bai: " + t.toString());

            }
        });

    }


}
