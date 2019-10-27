package com.example.pandaapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.pandaapp.Models.Product;
import com.example.pandaapp.R;
import com.example.pandaapp.Util.GlobalApplication;
import com.example.pandaapp.view.DetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.ViewHolder> implements View.OnClickListener {
    Context mctx;
    int layout;
    List<Product> listproduct;
    int stt;

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
        if (listproduct.get(position).getAnhSP().size() != 0) {
            Picasso.with(mctx).load(listproduct.get(position).getAnhSP().get(0))
                    .placeholder(R.drawable.logo)
                    .error(R.drawable.errror)
                    .into(holder.imageView);
        }

        holder.linearLayoutItemProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalApplication globalApplication = (GlobalApplication) mctx.getApplicationContext();
                //globalApplication.product=listproduct.get(position);
                if (globalApplication.listProduct == null) {
                    globalApplication.listProduct = new ArrayList<>();

                }
                globalApplication.listProduct.add(listproduct.get(position));
                Intent intent = new Intent(mctx, DetailActivity.class);
                mctx.startActivity(intent);

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
