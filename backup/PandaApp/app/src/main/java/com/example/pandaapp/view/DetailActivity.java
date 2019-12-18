package com.example.pandaapp.view;

import android.content.Intent;
import android.graphics.Paint;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.pandaapp.Models.Account;
import com.example.pandaapp.Models.CartItem;
import com.example.pandaapp.Models.Product;
import com.example.pandaapp.R;
import com.example.pandaapp.Util.CacheUltils;
import com.example.pandaapp.Util.ChangeActivity;
import com.example.pandaapp.Util.FragmentUtils;
import com.example.pandaapp.Util.GlobalApplication;
import com.example.pandaapp.Util.LoadImage;
import com.example.pandaapp.Util.OtherUltil;
import com.example.pandaapp.fragment.FragmentShowImage;

import java.util.ArrayList;
import java.util.HashSet;

public class DetailActivity extends AppCompatActivity {
    ImageView imageViewSP, imageViewButtonBack, imageViewCart;
    TextView textViewTen, textViewgia, textViewmota, textViewdaBan, textviewSTTImage, textViewDiscount;
    Button btnAddCart, btnEdit, btnBuyNow;
    GlobalApplication globalApplication;
    Product product = new Product();
    Account account;
    int REQUESTCODE_EDIT = 112;
    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        globalApplication = (GlobalApplication) getApplicationContext();
        if (globalApplication.product != null) {
            product = globalApplication.product;
            account = globalApplication.account;
        }
        CacheUltils cacheUltils = new CacheUltils(getApplicationContext());
        cacheUltils.RefreshProduct(product.getProductId());
        try {
            init();
            setDataSP(product);
            Listener();
        } catch (Exception e) {
            System.out.println(e.toString());

        }


    }

    private void init() throws Exception {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh_DetailProduct);
        btnEdit = (Button) findViewById(R.id.btnEdit_ProductDetail);
        btnAddCart = (Button) findViewById(R.id.btnAddCart_Detail);
        btnBuyNow = (Button) findViewById(R.id.btn_buy_ProductDetail);
        imageViewSP = (ImageView) findViewById(R.id.Image_Detail);
        textViewTen = (TextView) findViewById(R.id.textviewTen_Detail);
        textViewgia = (TextView) findViewById(R.id.textviewGia_Deatail);
        textViewdaBan = (TextView) findViewById(R.id.textviewSold_Detail);
        textViewmota = (TextView) findViewById(R.id.textviewDis_Detail);
        imageViewCart = (ImageView) findViewById(R.id.detail_product_cart);
        imageViewButtonBack = (ImageView) findViewById(R.id.detail_product_back);
        textviewSTTImage = (TextView) findViewById(R.id.textviewSTT_DetailProduct);
        textViewDiscount = (TextView) findViewById(R.id.textviewGiaDiscount_Deatail);


        if (product.getIdShop() == account.getIdShop()) {
            btnEdit.setVisibility(View.VISIBLE);
            btnAddCart.setVisibility(View.GONE);
            btnBuyNow.setVisibility(View.GONE);
        }

    }

    public void setDataSP(Product product) throws Exception {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        // getParent().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        imageViewSP.setMinimumHeight(height);
        imageViewSP.setMinimumHeight(width);
        if (product.getImages().size() > 0) {
            LoadImage.getImageInServer(this, product.getImages().get(0), imageViewSP);
        } else {
            LoadImage.getImageInServer(getApplicationContext(), "image/image/thumbnail.png", imageViewSP);

        }

        textViewTen.setText(product.getName());
        textViewgia.setText(OtherUltil.fomattien.format(product.getPrice()) + "đ");
        textViewmota.setText(product.getDis());
        textviewSTTImage.setText(1 + "/" + product.getImages().size());
        textViewDiscount.setText(OtherUltil.fomattien.format(product.getDiscount()+product.getPrice())+"đ");
        textViewDiscount.setPaintFlags(textViewDiscount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

    }

    public void Listener() throws Exception {
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

                Intent intent = new Intent(DetailActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });
        btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (globalApplication.ListcartItems == null) {
                    globalApplication.ListcartItems = new ArrayList<>();

                }
                boolean trung = false;
                for (int i = 0; i < globalApplication.ListcartItems.size(); i++) {
                    if (product.getProductId() == globalApplication.ListcartItems.get(i).getProduct().getProductId()) {
                        trung = true;
                        globalApplication.ListcartItems.get(i).setMount(globalApplication.ListcartItems.get(i).getMount() + 1);
                    }

                }
                if (trung == false) {
                    globalApplication.ListcartItems.add(new CartItem(product, 1));
                }

            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CacheUltils cacheUltils = new CacheUltils(getApplicationContext());
                cacheUltils.RefreshProduct(product.getProductId());
                Intent intent = new Intent(getApplicationContext(), EditProductActivity.class);
                startActivityForResult(intent, REQUESTCODE_EDIT);
            }
        });
        imageViewSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentUtils.openFragment(new FragmentShowImage(), getSupportFragmentManager(), R.id.Frame_ShowImage);
            }
        });
        swipeRefreshLayout.setColorSchemeResources(R.color.color_pink2);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                finish();
                startActivity(getIntent());
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUESTCODE_EDIT && requestCode == RESULT_OK) {
            Toast.makeText(getBaseContext(), "Sửa sản phẩm xong", Toast.LENGTH_SHORT).show();

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
