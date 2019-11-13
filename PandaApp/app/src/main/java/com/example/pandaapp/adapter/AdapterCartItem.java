package com.example.pandaapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pandaapp.Models.CartItem;
import com.example.pandaapp.Models.Product;
import com.example.pandaapp.R;
import com.example.pandaapp.Util.LoadImage;
import com.example.pandaapp.Util.OtherUltil;

import java.text.DecimalFormat;
import java.util.List;

public class AdapterCartItem extends ArrayAdapter {
    Context mctx;
    int layout;
    List<CartItem> cartItemList;
    CartItemListerner listener;
    public AdapterCartItem( Context context, int resource,  List<CartItem> object) {
        super(context, resource, object);
        this.mctx = context;
        this.layout = resource;
        this.cartItemList = object;
    }

    public void setListener(CartItemListerner listener)
    {
        this.listener = listener;
    }

    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(mctx).inflate(R.layout.item_cart, parent, false);

        }
        ImageView imageViewProduct = (ImageView) view.findViewById(R.id.img_Anh_ItemCart);
        TextView textViewName = (TextView) view.findViewById(R.id.tv_ten_ItemCart);
        TextView textViewShop = (TextView) view.findViewById(R.id.tv_tenShop_ItemCart);
        TextView textViewPrice = (TextView) view.findViewById(R.id.tv_gia_SP_ItemCart);
        TextView textViewsoMount = (TextView) view.findViewById(R.id.tv_soluong_ItemCart);
        TextView textViewMinussoMount = (TextView) view.findViewById(R.id.tv_tru_ItemCart);
        TextView textViewAddsoMoutt = (TextView) view.findViewById(R.id.tv_cong_ItemCart);

        ImageView imgDeleteItem = (ImageView) view.findViewById(R.id.tv_delete_ItemCart);

        textViewAddsoMoutt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnClickAddAmount(position);
            }
        });
       textViewMinussoMount.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               listener.OnClickMinusAmount(position);
           }
       });
        imgDeleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnClickRemoveAmount(position);
            }
        });

        if (cartItemList.get(position).getProduct().getImages().size()>0) {
            LoadImage.getImageInServer(mctx,cartItemList.get(position).getProduct().getImages().get(0),imageViewProduct);

        }

        textViewName.setText(cartItemList.get(position).getProduct().getName());


        textViewPrice.setText((OtherUltil.fomattien.format(cartItemList.get(position).getProduct().getPrice()))+"đ");
        textViewsoMount.setText(cartItemList.get(position).getMount()+"");
        return view;
    }

    public  interface  CartItemListerner
    {
        public void OnClickAddAmount(int position);
        public void OnClickMinusAmount(int position);
        public void OnClickRemoveAmount(int position);

    }
}
