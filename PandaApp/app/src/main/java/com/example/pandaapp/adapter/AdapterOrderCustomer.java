package com.example.pandaapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pandaapp.Models.Order;
import com.example.pandaapp.Models.OrderCustomer;
import com.example.pandaapp.Models.Product;
import com.example.pandaapp.R;
import com.example.pandaapp.Retrofit2.APIUltils;
import com.example.pandaapp.Retrofit2.DataClient;
import com.example.pandaapp.Util.GlobalApplication;
import com.example.pandaapp.Util.LoadImage;
import com.example.pandaapp.Util.OtherUltil;
import com.example.pandaapp.view.OrderDetail;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterOrderCustomer extends ArrayAdapter {
    Context mctx;
    List<OrderCustomer> orderList;
    int layout;
    GlobalApplication globalApplication;
    onlistenerAdapterOrderCustomer onlistenerAdapterOrder;


    public AdapterOrderCustomer(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        this.mctx = context;
        this.orderList = objects;
        this.layout = resource;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(mctx).inflate(R.layout.item_order, parent, false);
            Animation animation_cycle = AnimationUtils.loadAnimation(mctx, R.anim.animation_listview);
            view.setAnimation(animation_cycle);
        }
        globalApplication = (GlobalApplication) mctx.getApplicationContext();
        final TextView nameShop = (TextView) view.findViewById(R.id.tv_user_ItemOrder);
        TextView tvstatus = (TextView) view.findViewById(R.id.tv_status_ItemOrder_Shop);
        final TextView tvNameProduct = (TextView) view.findViewById(R.id.tv_nameProduct_ItemOrder);
        final TextView tvAmount = (TextView) view.findViewById(R.id.tv_Mount_ItemOrder);
        TextView tvTotalAmount = (TextView) view.findViewById(R.id.tv_total_product);
        final TextView tvPriceProduct = (TextView) view.findViewById(R.id.tv_Price_ItemOrder);
        final TextView tvTotalPay = (TextView) view.findViewById(R.id.tv_MoneyPay_ItemOrder);
        final ImageView imgAnhSanPham = (ImageView) view.findViewById(R.id.img_ImageProduct_ItemOrder);
        final OrderCustomer order = orderList.get(position);

        if (order.getTotalPrice() >= 0) {
            tvTotalPay.setText(OtherUltil.fomattien.format(orderList.get(position).getTotalPrice()) + "??");
        }
        if (order.getOrderitem().size() >= 0) {
            tvAmount.setText("x" + orderList.get(position).getOrderitem().get(0).getAmount());
        }

        int tongsanpham = 0;
        double discount = 0;
        for (int i = 0; i < orderList.get(position).getOrderitem().size(); i++) {
            tongsanpham += orderList.get(position).getOrderitem().get(i).getAmount();

        }
        tvTotalAmount.setText(tongsanpham + " s???n ph???m");
        nameShop.setText(order.getShopName());
        String status = "";
        switch (order.getStatusId()) {
            case 1:
                status = "??ang ch??? x??c nh???n";
                break;
            case 2:
                status = "??ang giao h??ng";
                break;
            case 3:
                status = "???? giao h??ng";
                break;
            case 4:
                status = "???? h???y";
                break;


        }
        tvstatus.setText(status);
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
                tvPriceProduct.setText(OtherUltil.fomattien.format(product.getPrice()) + "??");


            }

            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {

            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                globalApplication.orderCustomer = order;
                onlistenerAdapterOrder = (onlistenerAdapterOrderCustomer) mctx;
                onlistenerAdapterOrder.startActivityforResultListener();

            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataClient dataClient = APIUltils.getData();

                Intent intent = new Intent(mctx, OrderDetail.class);
                globalApplication.orderID =order.getOderId();
                globalApplication.orderCustomer = orderList.get(position);
                intent.putExtra("role",1);
                mctx.startActivity(intent);
            }
        });

        return view;

    }

    public interface onlistenerAdapterOrderCustomer {
        public void startActivityforResultListener();
    }
}
