package com.example.pandaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pandaapp.Models.Account;
import com.example.pandaapp.server.Server;
import com.example.pandaapp.view.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    EditText edittextuser, edittextpass;
    Button btnlogin;
    ImageView btnfacebookLogin, btnGoogleLogin;
    String txtusername, txtpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getdataformLogin();

                checkaccount(txtusername, txtpassword);
            }
        });


    }

    private void init() {
        edittextuser = (EditText) findViewById(R.id.edittextusernameLogin);
        edittextpass = (EditText) findViewById(R.id.edittextpassLogin);
        btnlogin = (Button) findViewById(R.id.login_buttonLogin);
        btnfacebookLogin = (ImageView) findViewById(R.id.login_facebookLogin);
        btnGoogleLogin = (ImageView) findViewById(R.id.login_googleLogin);

    }

    public void getdataformLogin() {
        txtusername = edittextuser.getText().toString().trim();
        txtpassword = edittextpass.getText().toString().trim();


    }

    public void checkaccount(final String ussername, final String password) {

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.getCheckAccount, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    if (jsonArray.length() == 0) {
                        Toast.makeText(LoginActivity.this, "Tên đăng nhập hoặc mật khẩu sai!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "Đang đăng nhập...", Toast.LENGTH_SHORT).show();
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
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
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
                        Account account = new Account(roleId, idShop, usename, password, name, phone_number, address, gender, email, DateOfBirth, accountStatus);
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("account", account);
                        startActivity(intent);
                        finish();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

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

