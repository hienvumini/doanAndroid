package com.example.pandaapp.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.example.pandaapp.Retrofit2.APIUltils;
import com.example.pandaapp.Retrofit2.DataClient;
import com.example.pandaapp.Util.CacheUltils;
import com.example.pandaapp.Util.ChangeActivity;
import com.example.pandaapp.Util.FragmentUtils;
import com.example.pandaapp.Util.GlobalApplication;
import com.example.pandaapp.Util.LoadImage;
import com.example.pandaapp.Util.OrderUltils;
import com.example.pandaapp.Util.OtherUltil;
import com.example.pandaapp.fragment.FragmentShowImage;

import java.util.ArrayList;
import java.util.HashSet;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {
    ImageView imageViewSP, imageViewButtonBack, imageViewCart;
    TextView textViewTen, textViewgia, textViewmota, textViewdaBan, textviewSTTImage, textViewDiscount;
    Button btnAddCart, btnEdit, btnBuyNow, btnDel;
    GlobalApplication globalApplication;
    Product product = new Product();
    Account account;
    int REQUESTCODE_EDIT = 112;
    SwipeRefreshLayout swipeRefreshLayout;
    LinearLayout layoutCustomer, layoutShop;


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
        btnDel = (Button) findViewById(R.id.btnDel_ProductDetail);
        layoutCustomer = (LinearLayout) findViewById(R.id.detail_product_footer_Customer);
        layoutShop = (LinearLayout) findViewById(R.id.detail_product_footer_Shop);


        if (product.getIdShop() == account.getIdShop()) {
//            btnEdit.setVisibility(View.VISIBLE);
//            btnAddCart.setVisibility(View.GONE);
//            btnBuyNow.setVisibility(View.GONE);
            layoutShop.setVisibility(View.VISIBLE);
            layoutCustomer.setVisibility(View.GONE);
        } else {
            layoutShop.setVisibility(View.GONE);
            layoutCustomer.setVisibility(View.VISIBLE);

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
        textViewDiscount.setText(OtherUltil.fomattien.format(product.getDiscount() + product.getPrice()) + "đ");
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
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText edtText = new EditText(getBaseContext());
                edtText.setWidth(200);
                LinearLayout linearLayout = new LinearLayout(DetailActivity.this);
                linearLayout.setGravity(Gravity.CENTER);

                linearLayout.addView(edtText);
                final AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
                builder.setTitle("Xác nhận xóa sản phẩm");
                builder.setMessage("Bạn chắc chắn xóa sản phẩm này? điền \"xoa\" sau đó bấm \"Xác nhận xóa\"");
                builder.setView(linearLayout);
                builder.setNeutralButton("Xác nhận xóa!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        if (edtText.getText().toString().trim().equalsIgnoreCase("Xoa")) {

                            DataClient dataClient = APIUltils.getData();
                            Call<String> stringCall = dataClient.DeleteProduct(globalApplication.product.getProductId());
                            stringCall.enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {

                                    if (response != null) {
                                        if (response.body().equals("0xx0")) {
                                            AlertDialog.Builder builder1 = new AlertDialog.Builder(DetailActivity.this);
                                            builder1.setTitle("Không thể xóa!");
                                            builder1.setMessage("Sản phẩm này hiện đang trong đơn đặt hàng của khách, bạn không thể xóa!");
                                            builder1.setPositiveButton("Trở lại", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    finish();
                                                }
                                            });
                                            builder1.show();
                                        } else if (response.body().equals("1xx1")) {
                                            AlertDialog.Builder builder1 = new AlertDialog.Builder(DetailActivity.this);
                                            builder1.setTitle("Xóa thành công");
                                            builder1.setMessage("Bấm OK để trở lại");
                                            builder1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Intent refresh = new Intent(DetailActivity.this, MainActivity.class);
                                                    startActivity(refresh);
                                                    finish();
                                                }
                                            });
                                            builder1.show();


                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {

                                }
                            });

                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }

        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUESTCODE_EDIT && requestCode == RESULT_OK) {
            Toasty.success(DetailActivity.this, "Sửa sản phẩm xong").show();

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
