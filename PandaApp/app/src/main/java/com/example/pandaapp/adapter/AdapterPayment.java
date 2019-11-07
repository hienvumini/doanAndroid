package com.example.pandaapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pandaapp.Models.CartItem;
import com.example.pandaapp.R;
import com.example.pandaapp.Util.LoadImage;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AdapterPayment extends ArrayAdapter {

    int layout;
    List<CartItem> listCartItem;
    Context mctx;

    public AdapterPayment( Context mctx, int layout, List<CartItem> listCartItem) {
        super(mctx, layout, listCartItem);
        this.layout = layout;
        this.listCartItem = listCartItem;
        this.mctx = mctx;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView==null)
        {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_payment, parent, false);
        }
        TextView paymentItemTenSP = convertView.findViewById(R.id.item_payment_tenSP);
        TextView paymentItemTenShop = convertView.findViewById(R.id.item_payment_tenShop);
        TextView paymentItemGia = convertView.findViewById(R.id.item_payment_gia_SP);
        TextView paymentItemSoLuong = convertView.findViewById(R.id.item_payment_soluong);
        ImageView payment_paymentAnh= convertView.findViewById(R.id.item_payment_img);

        paymentItemTenSP.setText(listCartItem.get(position).getProduct().getName());
        paymentItemTenShop.setText(listCartItem.get(position).getProduct().getShopName());
        paymentItemSoLuong.setText(String.valueOf(listCartItem.get(position).getMount()));
        paymentItemGia.setText(String.valueOf(listCartItem.get(position).getProduct().getPrice()));
        LoadImage.getImageInServer(mctx,listCartItem.get(position).getProduct().getImages().get(0).toString(),payment_paymentAnh);


        return convertView;
    }
}
