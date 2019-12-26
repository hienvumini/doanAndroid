package com.example.pandaapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.os.Bundle;

import androidx.annotation.Nullable;
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
import com.example.pandaapp.Util.FragmentUtils;
import com.example.pandaapp.view.LoginActivity;
import com.example.pandaapp.Models.Account;
import com.example.pandaapp.R;
import com.example.pandaapp.Util.GlobalApplication;
import com.example.pandaapp.view.MainActivity;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentLogin extends Fragment {
    private final static String SHARED_PREFERENCES_NAME = "user";

    EditText edittextuser, edittextpass;
    Button btnlogin;
    ImageView btnfacebookLogin, btnGoogleLogin;
    String txtusername, txtpassword;
    TextView textViewSignupLogin;
    Account account = new Account();
    GlobalApplication globalApplication;
    CallbackManager callbackManager;
    LoginButton loginButton;
    private static final String EMAIL = "email";
    int mode; // mode=1-> đăng kí bình thường bằng tên đăng nhâp; mode=2-> đăng kí bằng facebook; mode=3-> đăng kí bằng google;
    Account accountFB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_login, container, false);
        super.onCreate(savedInstanceState);
        callbackManager = CallbackManager.Factory.create();

        loginButton = (LoginButton) view.findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList(EMAIL));

        loginButton.setFragment(FragmentLogin.this);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                try {
                    getFbInfo();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Log.e("loiapi", "onError: loiiiiii");
            }
        });


        init(view);
        getdataSharePreference();
        textViewSignupLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null) {
                    try {
                        ((LoginActivity) getActivity()).toSignUpFragment(account, 1);
                    } catch (Exception e) {
                        System.out.println(e.toString());

                    }
                }
            }
        });
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getdataformLogin();
                try {
                    checkaccount(txtusername, txtpassword);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return view;
    }

    private void init(View view) {
        edittextuser = (EditText) view.findViewById(R.id.edittextusernameLogin);
        edittextpass = (EditText) view.findViewById(R.id.edittextpassLogin);
        btnlogin = (Button) view.findViewById(R.id.login_buttonLogin);

        textViewSignupLogin = (TextView) view.findViewById(R.id.textview_sigupLogin);
    }

    public void getdataformLogin() {
        txtusername = edittextuser.getText().toString().trim();
        txtpassword = edittextpass.getText().toString().trim();


    }

    public void checkaccount(final String ussername, final String password) throws Exception {
        DataClient processLogin = APIUltils.getData();
        Call<ArrayList<Account>> accountCall = processLogin.CheckAccount(ussername, password);
        accountCall.enqueue(new Callback<ArrayList<Account>>() {
            @Override
            public void onResponse(Call<ArrayList<Account>> call, Response<ArrayList<Account>> response) {
                if (response.body().size() > 0) {

                    ArrayList<Account> accountslist = response.body();
                    Account account = accountslist.get(0);
                   if (account.getAccountStatus()!=1){
                       Toasty.error(getActivity(),"Tài khoản của bạn đã bị khóa!").show();

                   } else {
                       Toasty.custom(getActivity(), "Đăng nhập thành công", R.drawable.ok, R.color.color_pink2, 2000, false, true).show();
                       Log.d("AZ", "Dăng nhập: " + response.body());
                       if (globalApplication == null) {
                           globalApplication = (GlobalApplication) getActivity().getApplicationContext();

                       }
                       globalApplication.account = account;
                       Intent intent = new Intent(getActivity(), MainActivity.class);
                       startActivity(intent);
                       getActivity().finish();
                       SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
                       SharedPreferences.Editor editor = sharedPreferences.edit();
                       editor.putString("user", ussername);
                       editor.putString("pass", password);
                       editor.apply();

                   }


                } else {
                   Toasty.error(getActivity(),"Tên đăng nhập hoặc mật khẩu không chính xác!").show();


                }

            }

            @Override
            public void onFailure(Call<ArrayList<Account>> call, Throwable t) {
                Log.d("BBBB", "That bai: " + t.toString());

            }
        });

    }

    private void getFbInfo() throws Exception {

        if (AccessToken.getCurrentAccessToken() != null) {
            GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(final JSONObject me, GraphResponse response) {
                            if (me != null) {
                                accountFB = new Account();
                                Log.i("Login: ", me.optString("name"));
                                Log.i("ID: ", me.optString("id"));
                                edittextuser.setText(me.optString("email"));
                                int roleid = 1;
                                String name = me.optString("name");
                                String number = "84";
                                try {
                                    JSONObject locationJson = me.getJSONObject("location");
                                    String Address = locationJson.getString("name");
                                    accountFB.setAddress(Address);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                int gender = 1;
                                if (me.optString("gender").equalsIgnoreCase("male")) {
                                    gender = 0;
                                } else {
                                    gender = 1;
                                }
                                String email = me.optString("email");
                                String datebirth = me.optString("birthday");
                                Log.d("NgaySinh", "onCompleted: " + datebirth);
                                accountFB.setName(name);
                                accountFB.setEmail(email);
                                accountFB.setGender(gender);
                                accountFB.setDateOfBirth(datebirth);
                                accountFB.setName(name);
                                accountFB.setName(name);
                                checkaccFB(accountFB.getEmail());


                            }
                        }
                    });

            Bundle parameters = new Bundle();
            parameters.putString("fields", "name,email,birthday,gender,location");
            request.setParameters(parameters);
            request.executeAsync();
            checkaccFB(parameters.getString("email"));


        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void logoutFB() {
        loginButton.unregisterCallback(callbackManager);

    }

    public void checkaccFB(String emailFB) {

        DataClient dataClient = APIUltils.getData();
        DataClient processLogin = APIUltils.getData();
        Call<ArrayList<Account>> accountCall = processLogin.CheckAccountFB(emailFB);
        accountCall.enqueue(new Callback<ArrayList<Account>>() {
            @Override
            public void onResponse(Call<ArrayList<Account>> call, Response<ArrayList<Account>> response) {
                if (response.body().size() > 0) {
                    Toasty.custom(getActivity(), "Đăng nhập thành công", R.drawable.ok, R.color.color_pink2, 2000, false, true).show();

                    ArrayList<Account> accountslist = response.body();
                    Account acc = accountslist.get(0);
                    Log.d("A222", "onCompleted: " + acc.toString());
                    if (globalApplication == null) {
                        globalApplication = (GlobalApplication) getActivity().getApplicationContext();

                    }
                    globalApplication.account = acc;
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    getActivity().finish();

                } else {
                    try {
                        ((LoginActivity) getActivity()).toSignUpFragment(accountFB, 2);
                    } catch (Exception e) {
                        System.out.println(e.toString());
                    }

                }

            }

            @Override
            public void onFailure(Call<ArrayList<Account>> call, Throwable t) {
                Log.d("BBBB", "That bai: " + t.toString());

            }
        });

    }

    public void getdataSharePreference() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences != null) {
            String olduser = sharedPreferences.getString("user", "");
            String oldpass = sharedPreferences.getString("pass", "");
            edittextuser.setText(olduser + "");
            edittextpass.setText(oldpass + "");
        }

    }


}
