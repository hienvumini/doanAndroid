package com.example.pandaapp.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pandaapp.Models.Account;
import com.example.pandaapp.Models.Product;
import com.example.pandaapp.R;
import com.example.pandaapp.Util.GlobalApplication;
import com.example.pandaapp.Util.LoadImage;
import com.example.pandaapp.view.DetailActivity;

import java.util.List;

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.ViewHolder> implements View.OnClickListener {
    Context mctx;
    int layout;
    List<Product> listproduct;
    int stt;
    GlobalApplication globalApplication;


    public AdapterProduct(Context context, int resource, List<Product> object) {
        this.mctx = context;
        this.layout = resource;
        this.listproduct = object;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mctx).inflate(R.layout.item_product, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.textviewTen.setText(listproduct.get(position).getName());
        holder.textViewGia.setText(listproduct.get(position).getPrice() + "đ");
        if (listproduct.get(position).getImages().size() != 0) {
            LoadImage.getImageInServer(mctx, listproduct.get(position).getImages().get(0), holder.imageView); /// Load ảnh từ internet. nếu load từ server phải đổi
        } else {
            LoadImage.getImageInServer(mctx, "image/image/thumbnail.png", holder.imageView);
        }

        holder.linearLayoutItemProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalApplication = (GlobalApplication) mctx.getApplicationContext();

                if (globalApplication.product == null) {
                    globalApplication.product = new Product();

                }
                globalApplication.product = listproduct.get(position);

                Account account = new Account();
                account = globalApplication.account;
                if (account.getRoleId() == 1) {
                    Intent intent = new Intent(mctx, DetailActivity.class);
                    mctx.startActivity(intent);

                } else {
                    if (listproduct.get(position).getIdShop() == account.getIdShop()) {

                    } else {  // sản phẩm không thuộc về shop , dẫn tới trang thông tin sản phẩm


                    }
                    Intent intent = new Intent(mctx, DetailActivity.class);
                    globalApplication.product = listproduct.get(position);
                    mctx.startActivity(intent);

                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return listproduct.size();
    }

    @Override
    public void onClick(View v) {

    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textviewTen, textViewGia;
        LinearLayout linearLayoutItemProduct;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.ImageanhSP);
            textviewTen = (TextView) itemView.findViewById(R.id.textviewTenSP);
            textViewGia = (TextView) itemView.findViewById(R.id.textviewGiaSP);
            linearLayoutItemProduct = (LinearLayout) itemView.findViewById(R.id.itemProduct);

        }
    }

    public interface AdapterItemClickListener {
        public void onClick(View view, int position, boolean isLongClick);
    }
}
