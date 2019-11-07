package com.example.pandaapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pandaapp.Models.CartItem;
import com.example.pandaapp.Models.Product;
import com.example.pandaapp.R;
import com.example.pandaapp.Util.ChangeActivity;
import com.example.pandaapp.Util.GlobalApplication;
import com.example.pandaapp.Util.LoadImage;

import java.util.HashSet;

public class DetailActivity extends AppCompatActivity {
    ImageView imageViewSP, imageViewButtonBack, imageViewCart;
    TextView textViewTen, textViewgia, textViewmota, textViewdaBan, textViewDisc;
    Button btnAddCart,btnBuyNow;
    GlobalApplication globalApplication;
    Product product = new Product();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        globalApplication = (GlobalApplication) getApplicationContext();
        if (globalApplication.product != null) {
            product = globalApplication.product;
        }
        init();


        setDataSP(product);

        // Listener();


    }

    private void init() {
        imageViewSP = (ImageView) findViewById(R.id.Image_Detail);
        textViewTen = (TextView) findViewById(R.id.textviewTen_Detail);
        textViewgia = (TextView) findViewById(R.id.textviewGia_Deatail);
        textViewdaBan = (TextView) findViewById(R.id.textviewSold_Detail);
        textViewmota = (TextView) findViewById(R.id.textviewDis_Detail);
        imageViewCart = (ImageView) findViewById(R.id.detail_product_cart);
        imageViewButtonBack = (ImageView) findViewById(R.id.detail_product_back);
        btnAddCart = (Button) findViewById(R.id.btnAddCart_Detail);

    }

    public void setDataSP(Product product) {
        if (product.getImages().size() > 0) {
            LoadImage.getImageInServer(this, product.getImages().get(0), imageViewSP);
        } else {
            LoadImage.getImageInServer(getApplicationContext(), "image/image/thumbnail.png", imageViewSP);

        }

        textViewTen.setText(product.getName());
        textViewgia.setText(product.getPrice() + "đ");
        textViewmota.setText(product.getDis());

    }

    public void Listener() {
        imageViewButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        imageViewCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeActivity.toActivity(getApplicationContext(), CartActivity.class);
            }
        });
        btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (globalApplication.SetcartItems == null) {
                    globalApplication.SetcartItems = new HashSet<>();

                }
                globalApplication.SetcartItems.add(new CartItem(product, 1));
            }
        });


    }


}
