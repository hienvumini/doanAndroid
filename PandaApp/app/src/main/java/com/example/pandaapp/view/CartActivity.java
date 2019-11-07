package com.example.pandaapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pandaapp.Models.CartItem;
import com.example.pandaapp.R;
import com.example.pandaapp.Util.GlobalApplication;
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
    TextView textViewMinussoMount ;
    TextView textViewAddsoMoutt ;
    TextView textViewDeleteProduct ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        init();
        cart_total.setText(String.valueOf(globalApplication.updatetotal()));
        if (globalApplication.ListcartItems != null) {
            cart_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(CartActivity.this, PaymentActivity.class);
                    startActivity(intent);
                }
            });
        }else {
            Toast.makeText(globalApplication, "Giỏ hàng trống", Toast.LENGTH_SHORT).show();

        }

    }






    private void init() {
        textViewMinussoMount = (TextView)findViewById(R.id.textviewMinusProduct_Cart);
        textViewAddsoMoutt = (TextView) findViewById(R.id.textviewAddProduct_Cart);
        textViewDeleteProduct = (TextView) findViewById(R.id.textviewDelteProductCart);

        listViewCart = (ListView) findViewById(R.id.listviewProduct_Cart);
        cart_button =(Button) findViewById(R.id.cart_button);
        cart_total = (TextView) findViewById(R.id.cart_total);
        LiCartItemList = new ArrayList<>();
        globalApplication= (GlobalApplication) getApplicationContext();
        if (globalApplication.ListcartItems != null) {
            LiCartItemList = globalApplication.ListcartItems;
            adapterCartItem = new AdapterCartItem(getApplicationContext(), R.id.listviewProduct_Cart, LiCartItemList);
            listViewCart.setAdapter(adapterCartItem);
        }else {
            Toast.makeText(globalApplication, "Giỏ hàng trống", Toast.LENGTH_SHORT).show();

        }

    }
}
