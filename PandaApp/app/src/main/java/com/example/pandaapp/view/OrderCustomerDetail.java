package com.example.pandaapp.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pandaapp.Models.Order;
import com.example.pandaapp.Models.OrderCustomer;
import com.example.pandaapp.R;
import com.example.pandaapp.Util.GlobalApplication;
import com.example.pandaapp.Util.OrderUltils;
import com.example.pandaapp.Util.OtherUltil;

import java.text.ParseException;

public class OrderCustomerDetail extends AppCompatActivity {
    ImageView img_back_OrderDetail;
    TextView tvname, tvphone, tvaddress, tvpricetotal, tvdiscount, tvtotalPay, tvIdorder, tvDateCreat, tvShopname;
    ListView listView;
    GlobalApplication globalApplication;
    OrderCustomer order;
    int status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_customer_detail);

        globalApplication = (GlobalApplication) getApplicationContext();
        if (globalApplication.orderCustomer != null) {
            order = globalApplication.orderCustomer;
        }

        init();
        try {
            setdataview();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (order == null) {
            Toast.makeText(getApplicationContext(), "Null 1111", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), order.toString(), Toast.LENGTH_SHORT).show();

        }
        Intent intent = getIntent();
        status = intent.getIntExtra("status", 1);
        Toast.makeText(getApplicationContext(), "Trang thani dong hang: " + status, Toast.LENGTH_SHORT).show();

        listener();

    }


    private void init() {
        img_back_OrderDetail = (ImageView) findViewById(R.id.img_back_OrderCustomerDetail);
        tvname = (TextView) findViewById(R.id.tv_nameReceiver_OrderCustomerDetail);
        tvphone = (TextView) findViewById(R.id.tv_phoneReceiver_OrderCustomerDetail);
        tvaddress = (TextView) findViewById(R.id.tv_addressReceiver_OrderCustomerDetail);
        tvpricetotal = (TextView) findViewById(R.id.tv_Price_ItemCustomerOrder);
        tvdiscount = (TextView) findViewById(R.id.tv_discountMount_OrderCustomerDetail);
        tvtotalPay = (TextView) findViewById(R.id.tv_MoneyPay_ItemCustomerOrder);
        tvIdorder = (TextView) findViewById(R.id.tv_IDorder_OrderCustomerDetail);
        tvDateCreat = (TextView) findViewById(R.id.tv_dateCreat_OrderCustomerDetail);
        tvShopname = (TextView) findViewById(R.id.tv_shopName_OrderCustomerDetail);
        listView = (ListView) findViewById(R.id.listviewOrderCustomerDetail);
    }

    private void setdataview() throws ParseException {
        tvname.setText(order.getName());
        tvphone.setText(order.getPhoneNumber());
        tvaddress.setText(order.getAddress());
        tvDateCreat.setText(OtherUltil.convertTime(order.getDateCreated()));
        tvIdorder.setText(order.getOderId() + "");

    }

    private void listener() {
        img_back_OrderDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });





    }

}
