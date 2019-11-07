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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pandaapp.Retrofit2.APIUltils;
import com.example.pandaapp.Retrofit2.DataClient;
import com.example.pandaapp.view.LoginActivity;
import com.example.pandaapp.Models.Account;
import com.example.pandaapp.R;
import com.example.pandaapp.Util.GlobalApplication;
import com.example.pandaapp.server.Server;
import com.example.pandaapp.view.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String username = pref.getString("username", null);
        String password = pref.getString("password", null);
        Toast.makeText(getActivity(), username + "   " + password, Toast.LENGTH_SHORT).show();

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
                    Toast.makeText(getActivity(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
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
                Log.d("BBBB", "THAnh Cong: " + t.toString());

            }
        });

    }


}
