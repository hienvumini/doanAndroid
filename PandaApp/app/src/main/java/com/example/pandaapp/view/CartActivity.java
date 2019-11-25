package com.example.pandaapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pandaapp.Models.CartItem;
import com.example.pandaapp.R;
import com.example.pandaapp.Util.GlobalApplication;
import com.example.pandaapp.Util.OtherUltil;
import com.example.pandaapp.adapter.AdapterCartItem;

import java.util.ArrayList;
import java.util.List;


public class CartActivity extends AppCompatActivity {
    ListView listViewCart;

    AdapterCartItem adapterCartItem;
    List<CartItem> LiCartItemList;
    Button cart_button;
    TextView cart_total;
    GlobalApplication globalApplication;


    TextView textViewDeleteProduct;
    ImageView imageView_back_Cart;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        init();
        caculatorPriceTotal();
        onListener();

        ;
    }


    private void init() {
        swipeRefreshLayout=(SwipeRefreshLayout) findViewById(R.id.swiperefresh_Cart_Item);
        imageView_back_Cart = (ImageView) findViewById(R.id.img_back_Cart);
        textViewDeleteProduct = (TextView) findViewById(R.id.textviewDelteProductCart);

        listViewCart = (ListView) findViewById(R.id.listviewProduct_Cart);
        cart_button = (Button) findViewById(R.id.cart_button);
        cart_total = (TextView) findViewById(R.id.cart_total_CartItem);
        LiCartItemList = new ArrayList<>();
        globalApplication = (GlobalApplication) getApplicationContext();
        if (globalApplication.ListcartItems != null) {
            LiCartItemList = globalApplication.ListcartItems;
            adapterCartItem = new AdapterCartItem(getApplicationContext(), R.id.listviewProduct_Cart, LiCartItemList);
            listViewCart.setAdapter(adapterCartItem);
        } else {
            Toast.makeText(globalApplication, "Giỏ hàng trống", Toast.LENGTH_SHORT).show();

        }

    }


    private void onListener() {
        swipeRefreshLayout.setColorSchemeResources(R.color.color_pink2);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                finish();
                startActivity(getIntent());
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        if (globalApplication.ListcartItems != null) {
            cart_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(CartActivity.this, PaymentActivity.class);
                    startActivity(intent);
                }
            });

            adapterCartItem.setListener(new AdapterCartItem.CartItemListerner() {
                @Override
                public void OnClickAddAmount(int position) {
                    LiCartItemList.get(position).setMount(LiCartItemList.get(position).getMount() + 1);
                    adapterCartItem.notifyDataSetChanged();
                    // listViewCart.setAdapter(adapterCartItem);
                    caculatorPriceTotal();
                }

                @Override
                public void OnClickMinusAmount(int position) {
                    if (LiCartItemList.get(position).getMount() > 1) {
                        LiCartItemList.get(position).setMount(LiCartItemList.get(position).getMount() - 1);
                        adapterCartItem.notifyDataSetChanged();
                        // listViewCart.setAdapter(adapterCartItem);
                        caculatorPriceTotal();
                    }

                }

                @Override
                public void OnClickRemoveAmount(int position) {
                    LiCartItemList.remove(position);
                    adapterCartItem.notifyDataSetChanged();
                    listViewCart.setAdapter(adapterCartItem);
                    caculatorPriceTotal();
                }
            });

        } else {
            Toast.makeText(globalApplication, "Giỏ hàng trống", Toast.LENGTH_SHORT).show();

        }


        imageView_back_Cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }


    public void caculatorPriceTotal() {

        cart_total.setText(OtherUltil.fomattien.format(globalApplication.updatetotal()));
    }
}
