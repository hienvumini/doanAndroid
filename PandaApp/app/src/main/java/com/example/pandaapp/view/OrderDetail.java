package com.example.pandaapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pandaapp.Models.Order;
import com.example.pandaapp.R;
import com.example.pandaapp.Util.GlobalApplication;
import com.example.pandaapp.Util.OtherUltil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class OrderDetail extends AppCompatActivity {
    ImageView img_back_OrderDetail;
    Button btnProcess_OrderDeatil;
    TextView tvname, tvphone, tvaddress, tvpricetotal, tvdiscount, tvtotalPay, tvIdorder, tvDateCreat;
    ListView listView;
    GlobalApplication globalApplication;
    Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        globalApplication = (GlobalApplication) getApplicationContext();
        if (globalApplication.order != null) {
            order = globalApplication.order;
        }

        init();
        try {
            setdataview();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        listener();

    }


    private void init() {
        img_back_OrderDetail = (ImageView) findViewById(R.id.img_back_OrderDetail);
        btnProcess_OrderDeatil = (Button) findViewById(R.id.btnProcess_OrderDeatil);
        tvname = (TextView) findViewById(R.id.tv_nameReceiver_OrderDetail);
        tvphone = (TextView) findViewById(R.id.tv_phoneReceiver_OrderDetail);
        tvaddress = (TextView) findViewById(R.id.tv_addressReceiver_OrderDetail);
        tvpricetotal = (TextView) findViewById(R.id.tv_Price_ItemOrder);
        tvdiscount = (TextView) findViewById(R.id.tv_discountMount_OrderDetail);
        tvtotalPay = (TextView) findViewById(R.id.tv_MoneyPay_ItemOrder);
        tvIdorder = (TextView) findViewById(R.id.tv_IDorder_OrderDetail);
        tvDateCreat = (TextView) findViewById(R.id.tv_dateCreat_OrderDetail);
        listView=(ListView) findViewById(R.id.listviewOrderDetail);
    }

    private void setdataview() throws ParseException {
        tvname.setText(order.getName());
        tvphone.setText(order.getPhone_number());
        tvaddress.setText(order.getAddress());
        tvDateCreat.setText(OtherUltil.convertTime(order.getDate_created()));
        tvIdorder.setText(order.getOderId() + "");

    }

    private void listener() {
        img_back_OrderDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnProcess_OrderDeatil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(OrderDetail.this, "Xu ly", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
