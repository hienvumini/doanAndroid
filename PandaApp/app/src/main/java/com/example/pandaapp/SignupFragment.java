package com.example.pandaapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pandaapp.Models.Account;
import com.example.pandaapp.server.Server;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class SignupFragment extends Fragment {
    EditText edittextusernameSignup, edittextpassSignup, edittextNameSignup, edittextPhoneSignup, edittextAddressSignup, edittextEmailSignup, edittextDateOfBirthSignup, edittextNameShopSignup;
    RadioGroup radiogroupGioitinh;
    RadioButton checkNuSignup, checkNamSignup;
    Button signup_buttonSignup;
    String txtusername, txtpassword, txtnamefull, txtphone, txtaddress, txtemail, txtdateofbirth, txtNameShop;
    int gioitinh;
    boolean enableShop=false;
    int  idShop, AccountId;
    int idrole=1;
    Account account = new Account();
    CheckBox checkenableShop;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_signup, container, false);
        init(view);

        signup_buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edittextNameSignup.getText().toString().equalsIgnoreCase("") ||
                        edittextusernameSignup.getText().toString().equalsIgnoreCase("") ||
                        edittextpassSignup.getText().toString().equalsIgnoreCase("") ||
                        edittextAddressSignup.getText().toString().equalsIgnoreCase("") ||
                        edittextDateOfBirthSignup.getText().toString().equalsIgnoreCase("") ||
                        edittextPhoneSignup.getText().toString().equalsIgnoreCase("")
                ) {
                    Toast.makeText(getActivity(), "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                } else {

                    getdataformSignup();
                    Log.d("AAA", "Cong   "  +account.toString());
                    //requestSignUp();
                }
//                if (edittextNameSignup.toString().equalsIgnoreCase("") ||
//                        edittextusernameSignup.toString().equalsIgnoreCase("") ||
//                        edittextpassSignup.toString().equalsIgnoreCase("") ||
//                        edittextAddressSignup.toString().equalsIgnoreCase("") ||
//                        edittextDateOfBirthSignup.toString().equalsIgnoreCase("") ||
//                        edittextPhoneSignup.toString().equalsIgnoreCase("")
//                ) {
//                    Toast.makeText(getActivity(), "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(getActivity(), "Đang đăng kí!", Toast.LENGTH_SHORT).show();
//                    getdataformSignup();
//                    requestSignUp();
//                }


            }

            private void requestSignUp() {
                RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.AddAccount, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show();
                        if (response.contains("xxx001")) {
                            Toast.makeText(getActivity(), "Tài khoản đã tồn tại!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                            ((LoginActivity) getActivity()).toSigninFragment();


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
                        hashMap.put("shopName", txtNameShop);
                        hashMap.put("idrole", account.getRoleId() + "");
                        hashMap.put("txtusername", account.getUsename());
                        hashMap.put("txtpassword", account.getPassword());
                        hashMap.put("txtnamefull", account.getName());
                        hashMap.put("txtphone", account.getPhone_number());
                        hashMap.put("txtaddress", account.getAddress());
                        hashMap.put("gender", account.getGender() + "");
                        hashMap.put("txtemail", account.getEmail());
                        return hashMap;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });
        checkenableShop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    enableShop = true;
                    ((TextView) view.findViewById(R.id.texviewlableOpenShopSignUp)).setVisibility(View.VISIBLE);
                    edittextNameShopSignup.setVisibility(View.VISIBLE);
                    edittextNameShopSignup.setText(edittextNameSignup.getText().toString().trim());
                    idrole=2;



                } else {
                    ((TextView) view.findViewById(R.id.texviewlableOpenShopSignUp)).setVisibility(View.INVISIBLE);
                    edittextNameShopSignup.setVisibility(View.INVISIBLE);
                    idrole=1;


                }
            }
        });
        return view;
    }


    private void init(View view) {
        edittextAddressSignup = (EditText) view.findViewById(R.id.edittextAddressSignup);
        edittextDateOfBirthSignup = (EditText) view.findViewById(R.id.edittextDateOfBirthSignup);
        edittextEmailSignup = (EditText) view.findViewById(R.id.edittextEmailSignup);
        edittextNameSignup = (EditText) view.findViewById(R.id.edittextNameSignup);
        edittextpassSignup = (EditText) view.findViewById(R.id.edittextpassSignup);
        edittextPhoneSignup = (EditText) view.findViewById(R.id.edittextPhoneSignup);
        edittextusernameSignup = (EditText) view.findViewById(R.id.edittextusernameSignup);
        edittextNameShopSignup = (EditText) view.findViewById(R.id.edittextNameShopSignup);
        radiogroupGioitinh = (RadioGroup) view.findViewById(R.id.radiogroupGioitinh);
        checkNamSignup = (RadioButton) view.findViewById(R.id.checkNamSignup);
        checkNuSignup = (RadioButton) view.findViewById(R.id.checkNuSignup);
        signup_buttonSignup = (Button) view.findViewById(R.id.signup_buttonSignup);
        checkenableShop = (CheckBox) view.findViewById(R.id.checkboxOpenShop);
    }

    public void getdataformSignup() {
        txtaddress = edittextAddressSignup.getText().toString().trim();
        txtdateofbirth = edittextDateOfBirthSignup.getText().toString().trim();
        if (edittextusernameSignup.getText().toString().equalsIgnoreCase("")) {
            txtemail = "";
        } else {
            txtemail = edittextEmailSignup.getText().toString().trim();
        }
        txtnamefull = edittextNameSignup.getText().toString().trim();
        txtpassword = edittextpassSignup.getText().toString().trim();
        txtphone = edittextPhoneSignup.getText().toString().trim();
        txtusername = edittextusernameSignup.getText().toString().trim();
        switch (radiogroupGioitinh.getId()) {
            case R.id.checkNamSignup:
                gioitinh = 1;
                break;
            case R.id.checkNuSignup:
                gioitinh = 0;
                break;
        }

        account = new Account(idrole, idShop, txtusername, txtpassword, txtnamefull, txtphone, txtaddress, gioitinh, txtemail, txtdateofbirth, 1);
        txtNameShop = txtnamefull;
        idrole = 1;
        if (enableShop == true) {
            txtNameShop = edittextNameShopSignup.getText().toString().trim();
            idrole = 2;
        }
        if (txtNameShop == null || txtNameShop.equalsIgnoreCase("")) {
            txtNameShop = txtnamefull;
        }


    }


}
