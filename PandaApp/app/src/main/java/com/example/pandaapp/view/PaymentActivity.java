package com.example.pandaapp.view;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pandaapp.Models.Account;
import com.example.pandaapp.Models.CartItem;
import com.example.pandaapp.R;
import com.example.pandaapp.Retrofit2.APIUltils;
import com.example.pandaapp.Util.GlobalApplication;
import com.example.pandaapp.adapter.AdapterPayment;

import java.util.ArrayList;
import java.util.List;

public class PaymentActivity extends AppCompatActivity {
    TextView payment_total;
    GlobalApplication globalApplication;
    EditText txtuser, txtDiaChi, txtSDT;
    ListView lstPayMent;
    Account account;
    AdapterPayment adapterPayment;
    List<CartItem> listCartItem;
    Button payment_button;
    double total=0;
    String oderId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        init();
        setData();

        adapterPayment = new AdapterPayment(getApplicationContext(), R.id.payment_listview, listCartItem);
        lstPayMent.setAdapter(adapterPayment);
        payment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBill();

            }
        });

    }




    private void addBill() {

           APIUltils.getData().addOder(account.getAccountId(),total,txtuser.getText().toString(),txtDiaChi.getText().toString(),txtSDT.getText().toString()).enqueue(new Callback<String>() {
               @Override
               public void onResponse(Call<String> call, Response<String> response) {

                   oderId= response.body();

                    Log.d("aaaaa", "onResponse:"+response.body()+"i");
                   insertToOrderItem();
               }

               @Override
               public void onFailure(Call<String> call, Throwable t) {
                   Log.e("payerror", "onFailure: "+t.toString() );
               }
           });



        globalApplication.ListcartItems=null;
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);

       }




    private void setData() {
        globalApplication = (GlobalApplication) getApplicationContext();
        account = globalApplication.account;
        txtuser.setText(account.getName().toString());
        txtSDT.setText(account.getPhone_number());
        txtDiaChi.setText(account.getAddress());
        listCartItem = new ArrayList<>();
        listCartItem = globalApplication.ListcartItems;
        payment_total.setText(String.valueOf(globalApplication.updatetotal()));
    }

    private void init() {
        txtuser = (EditText) findViewById(R.id.payment_username);
        txtDiaChi = (EditText) findViewById(R.id.payment_diachi);
        txtSDT = (EditText) findViewById(R.id.payment_sdt);
        lstPayMent = (ListView) findViewById(R.id.payment_listview);
        payment_button = (Button) findViewById(R.id.payment_button);
        payment_total=(TextView) findViewById(R.id.payment_total);

    }
    public void insertToOrderItem(){
        for (int i=0;i<listCartItem.size();i++)
        {
            Log.d("abcde", "addBill: "+oderId+"-"+listCartItem.get(i).getProduct().getProductId()+"-"+
                    listCartItem.get(i).getMount()+"-"+listCartItem.get(i).getTotal());
            APIUltils.getData().addOderItem(oderId,listCartItem.get(i).getProduct().getProductId(),listCartItem.get(i).getMount(),listCartItem.get(i).getTotal()).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    String a =response.body();
                    Log.d("payyy", "onResponse:"+oderId +" "+a);
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.e("ppp", "onFailure: "+t.toString() );
                }
            });
        }

    }


}
