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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pandaapp.view.LoginActivity;
import com.example.pandaapp.Models.Account;
import com.example.pandaapp.R;
import com.example.pandaapp.Util.GlobalApplication;
import com.example.pandaapp.server.Server;
import com.example.pandaapp.view.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class FragmentLogin extends Fragment {

    EditText edittextuser, edittextpass;
    Button btnlogin;
    ImageView btnfacebookLogin, btnGoogleLogin;
    String txtusername, txtpassword;
    TextView textViewSignupLogin;
    Account account = new Account();


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

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.getCheckAccount, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                Log.d("FFF", "onResponse: "+response);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    if (jsonArray.length() == 0) {
                        Toast.makeText(getActivity(), "Tên đăng nhập hoặc mật khẩu sai!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Đang đăng nhập...", Toast.LENGTH_SHORT).show();
                        int roleId = 0;
                        int idShop = 0;
                        String usename = "";
                        String password = "";
                        String name = "";
                        String phone_number = "";
                        String address = "";
                        int gender = 0;
                        String email = "";
                        String DateOfBirth = "";
                        int accountStatus = 0;
                        int accountId=0;
                        JSONObject jsonObject = jsonArray.getJSONObject(0);

                        accountId = jsonObject.getInt("AccountId");
                        roleId = jsonObject.getInt("roleId");
                        idShop = jsonObject.getInt("idShop");
                        usename = jsonObject.getString("usename");
                        password = jsonObject.getString("password");
                        name = jsonObject.getString("name");
                        phone_number = jsonObject.getString("phone_number");
                        address = jsonObject.getString("address");
                        gender = jsonObject.getInt("gender");
                        email = jsonObject.getString("email");
                        DateOfBirth = jsonObject.getString("DateOfBirth");
                        accountStatus = jsonObject.getInt("accountStatus");
                        Account account1 = new Account(roleId, idShop,accountId, usename, password, name, phone_number, address, gender, email, DateOfBirth, accountStatus);
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        GlobalApplication globalApplication = (GlobalApplication) getActivity().getApplicationContext();
                        globalApplication.account = account1;
                        startActivity(intent);
                        getActivity().finish();


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("AAA", "onErrorResponse: "+error.toString());

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("usernameaccount", ussername);
                hashMap.put("passwordaccount", password);
                return hashMap;
            }

        };
        requestQueue.add(stringRequest);
    }


}