package com.example.pandaapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pandaapp.Models.Order;
import com.example.pandaapp.Models.OrderItemDetail;
import com.example.pandaapp.Models.Product;
import com.example.pandaapp.R;
import com.example.pandaapp.Retrofit2.APIUltils;
import com.example.pandaapp.Retrofit2.DataClient;
import com.example.pandaapp.Util.GlobalApplication;
import com.example.pandaapp.Util.LoadImage;
import com.example.pandaapp.Util.OtherUltil;
import com.example.pandaapp.view.DetailActivity;
import com.example.pandaapp.view.OrderDetail;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterOrderItem extends ArrayAdapter {
    Context mctx;
    List<OrderItemDetail> orderItemList;
    int layout;
    GlobalApplication globalApplication;


    public AdapterOrderItem(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        this.mctx = context;
        this.orderItemList = objects;
        this.layout = resource;

    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(mctx).inflate(R.layout.item_order, parent, false);
        }
        globalApplication=(GlobalApplication) mctx.getApplicationContext();
        final TextView tvUser = (TextView) view.findViewById(R.id.tv_user_ItemOrder);
        final TextView tvstatus = (TextView) view.findViewById(R.id.tv_status_ItemOrder_Shop);
        final TextView tvNameProduct = (TextView) view.findViewById(R.id.tv_nameProduct_ItemOrder);
        final TextView tvAmount = (TextView) view.findViewById(R.id.tv_Mount_ItemOrder);
        TextView tvTotalAmount = (TextView) view.findViewById(R.id.tv_total_product);
        final TextView tvPriceProduct = (TextView) view.findViewById(R.id.tv_Price_ItemOrder);
        final TextView tvTotalPay = (TextView) view.findViewById(R.id.tv_MoneyPay_ItemOrder);
        final ImageView imgAnhSanPham = (ImageView) view.findViewById(R.id.img_ImageProduct_ItemOrder);
        final OrderItemDetail order = orderItemList.get(position);
        final LinearLayout linearLayoutShop = (LinearLayout) view.findViewById(R.id.ctShoplayout_ItemOrder);


        if (order.getTotal() != 0) {
            tvTotalPay.setText(OtherUltil.fomattien.format(orderItemList.get(position).getTotal()) + "đ");
        }
        tvPriceProduct.setText(OtherUltil.fomattien.format(orderItemList.get(position).getPrice()+orderItemList.get(position).getDiscount()) + "đ");
        Toast.makeText(mctx, ""+orderItemList.get(position).getDiscount(), Toast.LENGTH_SHORT).show();
        tvAmount.setText("x" + orderItemList.get(position).getAmount());
        linearLayoutShop.setVisibility(View.GONE);
        tvTotalAmount.setVisibility(View.GONE);
        tvNameProduct.setText(orderItemList.get(position).getName());
        LoadImage.getImageInServer(mctx, orderItemList.get(position).getImage(), imgAnhSanPham);


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataClient dataClient=APIUltils.getData();
                Call<ArrayList<Product>> arrayListCall=dataClient.getProduct(orderItemList.get(position).getProductId());
                arrayListCall.enqueue(new Callback<ArrayList<Product>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                        if (response.body().size()>0) {
                            globalApplication.product=response.body().get(0);
                            Intent intent=new Intent(mctx, DetailActivity.class);
                            mctx.startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Product>> call, Throwable t) {

                    }
                });
            }
        });

        return view;
    }

    @Override
    public int getCount() {
        return orderItemList.size();
    }
}
