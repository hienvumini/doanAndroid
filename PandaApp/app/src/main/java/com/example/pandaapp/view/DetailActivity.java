package com.example.pandaapp.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.pandaapp.CartActivity;
import com.example.pandaapp.Models.Account;
import com.example.pandaapp.Models.Product;
import com.example.pandaapp.R;
import com.example.pandaapp.Util.ChangeActivity;
import com.example.pandaapp.Util.GlobalApplication;
import com.example.pandaapp.Util.LoadImage;
import com.example.pandaapp.fragment.FragmentCart;

public class DetailActivity extends AppCompatActivity {
    ImageView imageViewSP, imageViewButtonBack,imageViewCart;
    TextView textViewTen, textViewgia, textViewmota, textViewdaBan, textViewDisc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        GlobalApplication globalApplication = (GlobalApplication) getApplicationContext();
        Product product = new Product();
        product = globalApplication.product;
        Toast.makeText(this, globalApplication.product.getName(), Toast.LENGTH_SHORT).show();
        init();
        setDataSP(product);
        imageViewButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        imageViewCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeActivity.toActivity(getApplicationContext(), CartActivity.class);
            }
        });


    }

    private void init() {
        imageViewSP = (ImageView) findViewById(R.id.Image_Detail);
        textViewTen = (TextView) findViewById(R.id.textviewTen_Detail);
        textViewgia = (TextView) findViewById(R.id.textviewGia_Deatail);
        textViewdaBan = (TextView) findViewById(R.id.textviewSold_Detail);
        textViewmota = (TextView) findViewById(R.id.textviewDis_Detail);
        imageViewCart=(ImageView) findViewById(R.id.detail_product_cart);
        imageViewButtonBack=(ImageView)findViewById(R.id.detail_product_back);

    }

    public void setDataSP(Product product) {
        LoadImage.getImage(this, product.getAnhSP().get(0), imageViewSP);
        textViewTen.setText(product.getName());
        textViewgia.setText(product.getPrice() + "đ");
        textViewmota.setText(product.getDis());
    }
    private void openFragment(final Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fram_Detail, fragment);
        transaction.commit();

    }
}
