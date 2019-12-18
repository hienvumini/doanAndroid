package com.example.pandaapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pandaapp.Models.Account;
import com.example.pandaapp.Models.Shop;
import com.example.pandaapp.R;
import com.example.pandaapp.Retrofit2.APIUltils;
import com.example.pandaapp.Retrofit2.DataClient;
import com.example.pandaapp.Util.GlobalApplication;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OpenShopActivity extends AppCompatActivity {
    EditText edittextNameShopSignup, editTextIntroduce, editTextAddressShop, editTextphoneShop, editTextmailShop;
    Button buttonOpenShop;
    String txtnameshop, txtaddressshop, txtemailshop, txtintroduceshop, txtphoneshop;
    GlobalApplication globalApplication;
    Account account;
    int mode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_shop);
        init();
        Intent intent = getIntent();
        mode = intent.getIntExtra("mode", 1);
        setintilizedatashop();


        getdatafillOpenShop();

        listener();

    }


    private void init() {
        edittextNameShopSignup = (EditText) findViewById(R.id.edittextNameOpenShop);
        editTextIntroduce = (EditText) findViewById(R.id.edittextIntroduceOpenShop);
        editTextAddressShop = (EditText) findViewById(R.id.edittextAddressOpenShop);
        editTextphoneShop = (EditText) findViewById(R.id.edittextPhonelOpenShop);
        editTextmailShop = (EditText) findViewById(R.id.edittextEmailOpenShop);
        buttonOpenShop = (Button) findViewById(R.id.signup_buttonOpenShop);
        globalApplication = (GlobalApplication) getApplicationContext();
        account = new Account();
        account = globalApplication.account;
    }

    private void setintilizedatashop() {


        switch (mode) {
            case 1:
                edittextNameShopSignup.setText(account.getName() + "");
                editTextIntroduce.setText("Chào mừng khách hàng đến với cửa hàng của " + account.getName());
                editTextAddressShop.setText(account.getAddress() + "");
                editTextphoneShop.setText(account.getPhone_number() + "");
                editTextmailShop.setText(account.getEmail() + "");

                break;
            case 2:
                buttonOpenShop.setText("Xác nhận");
                DataClient dataClient = APIUltils.getData();
                Call<ArrayList<Shop>> stringCall = dataClient.getInfoShop(account.getIdShop());
                stringCall.enqueue(new Callback<ArrayList<Shop>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Shop>> call, Response<ArrayList<Shop>> response) {
                        if (response.body() != null) {
                            Shop shop = response.body().get(0);
                            edittextNameShopSignup.setText(shop.getShopName() + "");
                            editTextIntroduce.setText(shop.getIntroduce() + "");
                            editTextAddressShop.setText(shop.getAddress() + "");
                            editTextphoneShop.setText(shop.getPhone() + "");
                            editTextmailShop.setText(shop.getEmail() + "");


                        }

                    }

                    @Override
                    public void onFailure(Call<ArrayList<Shop>> call, Throwable t) {

                    }
                });


                break;


        }
    }

    private void listener() {
        buttonOpenShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Openshop();

            }
        });
    }

    public void getdatafillOpenShop() {
        txtnameshop = edittextNameShopSignup.getText().toString().trim() + "";
        txtintroduceshop = editTextIntroduce.getText().toString().trim() + "";
        txtaddressshop = editTextAddressShop.getText().toString().trim() + "";
        txtphoneshop = editTextphoneShop.getText().toString().trim() + "";
        txtemailshop = editTextmailShop.getText().toString().trim() + "";

        if (txtnameshop.length() == 0) {
            txtnameshop = account.getName();
        }

    }

    private void Openshop() {
        getdatafillOpenShop();

        if (mode == 1) {
            DataClient dataClient = APIUltils.getData();
            Call<String> stringCall = dataClient.OpenShop(txtnameshop, txtintroduceshop, txtaddressshop, txtphoneshop, txtemailshop, account.getAccountId());
            stringCall.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.body().equalsIgnoreCase("1xx1")) {
                        Toast.makeText(OpenShopActivity.this, "Chúc mừng " + account.getName() + "\nBạn đã là nhà bán hàng\n Vui lòng đăng nhập lại và bắt đầu bán sản phẩm của bạn", Toast.LENGTH_SHORT).show();
                        globalApplication.account = null;
                        Intent intent = new Intent(OpenShopActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });

        } else if (mode==2){
            DataClient dataClient = APIUltils.getData();
            Call<String> stringCall = dataClient.EditShop(account.getIdShop(), txtnameshop, txtintroduceshop, txtaddressshop, txtphoneshop, txtemailshop);
            stringCall.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.body().equalsIgnoreCase("1xx1")) {
                        Toasty.success(OpenShopActivity.this, "Sửa thông tin cửa hàng thành công!").show();

                        finish();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });

        }
    }
}
