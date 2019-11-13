package com.example.pandaapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pandaapp.Models.Order;
import com.example.pandaapp.Models.Product;
import com.example.pandaapp.R;
import com.example.pandaapp.Retrofit2.APIUltils;
import com.example.pandaapp.Retrofit2.DataClient;
import com.example.pandaapp.Util.CacheUltils;
import com.example.pandaapp.Util.ChangeActivity;
import com.example.pandaapp.Util.GlobalApplication;
import com.example.pandaapp.Util.LoadImage;
import com.example.pandaapp.Util.OtherUltil;
import com.example.pandaapp.view.OrderDetail;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterOrderShop extends ArrayAdapter {
    Context mctx;
    List<Order> orderList;
    int layout;
    GlobalApplication globalApplication;
    onlistenerAdapterOrder onlistenerAdapterOrder;


    public AdapterOrderShop(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        this.mctx = context;
        this.orderList = objects;
        this.layout = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(mctx).inflate(R.layout.item_order, parent, false);
        }
        final TextView tvUser = (TextView) view.findViewById(R.id.tv_user_ItemOrder);
        TextView tvstatus = (TextView) view.findViewById(R.id.tv_status_ItemOrder);
        final TextView tvNameProduct = (TextView) view.findViewById(R.id.tv_nameProduct_ItemOrder);
        final TextView tvAmount = (TextView) view.findViewById(R.id.tv_Mount_ItemOrder);
        TextView tvTotalAmount = (TextView) view.findViewById(R.id.tv_total_product);
        final TextView tvPriceProduct = (TextView) view.findViewById(R.id.tv_Price_ItemOrder);
        final TextView tvTotalPay = (TextView) view.findViewById(R.id.tv_MoneyPay_ItemOrder);
        final ImageView imgAnhSanPham = (ImageView) view.findViewById(R.id.img_ImageProduct_ItemOrder);
         final Order order = orderList.get(position);
//        Product product = new Product();
//        GlobalApplication globalApplication = (GlobalApplication) mctx.getApplicationContext();
//        CacheUltils cacheUltils = new CacheUltils(mctx);
//        cacheUltils.RefreshProduct(orderList.get(position).getOrderitem().get(0).getProductId());
//        tvNameProduct.setText(globalApplication.product.getName());
//        LoadImage.getImageInServer(mctx, globalApplication.product.getImages().get(0), imgAnhSanPham);
        if (order.getTotalPrice() != null) {
            tvTotalPay.setText(OtherUltil.fomattien.format(orderList.get(position).getTotalPrice()) + "đ");
        }
        if (order.getOrderitem().size() >= 0) {
            tvAmount.setText("x" + orderList.get(position).getOrderitem().get(0).getAmount());
        }

        int tongsanpham = 0;
        double discount=0;
        for (int i = 0; i < orderList.get(position).getOrderitem().size(); i++) {
            tongsanpham += orderList.get(position).getOrderitem().get(i).getAmount();

        }
        tvTotalAmount.setText(tongsanpham + " sản phẩm");
        tvUser.setText(order.getName());

        DataClient getProduct = APIUltils.getData();
        Call<ArrayList<Product>> productCall = getProduct.getProduct(orderList.get(position).getOrderitem().get(0).getProductId());
        productCall.enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                Product product = new Product();
                product = response.body().get(0);
                if (product.getImages().size() > 0) {
                    LoadImage.getImageInServer(mctx, product.getImages().get(0), imgAnhSanPham);
                }
                tvNameProduct.setText(product.getName());
                tvPriceProduct.setText(OtherUltil.fomattien.format(product.getPrice()) + "đ");


            }

            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {

            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                globalApplication = (GlobalApplication) mctx.getApplicationContext();
                globalApplication.order = order;
                onlistenerAdapterOrder=(onlistenerAdapterOrder) mctx;
                onlistenerAdapterOrder.startActivityforResultListener();

            }
        });

        return view;
    }
    public interface onlistenerAdapterOrder{
        public void startActivityforResultListener();
    }
}
