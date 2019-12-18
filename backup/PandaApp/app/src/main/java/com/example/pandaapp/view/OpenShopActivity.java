package com.example.pandaapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.pandaapp.R;

public class OpenShopActivity extends AppCompatActivity {
    int mode; // mode=1; mở cửa hàng mới, mode=2 chỉnh sửa thông tin cửa hàng

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_shop);
        Intent intent=getIntent();
        mode=intent.getIntExtra("mode",1);
        Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
    }
}
