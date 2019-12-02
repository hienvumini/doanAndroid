package com.example.pandaapp.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pandaapp.Models.Account;
import com.example.pandaapp.Models.ILoadmoreProduct;
import com.example.pandaapp.Models.Product;
import com.example.pandaapp.R;
import com.example.pandaapp.Util.GlobalApplication;
import com.example.pandaapp.Util.LoadImage;
import com.example.pandaapp.Util.OtherUltil;
import com.example.pandaapp.view.DetailActivity;

import java.util.List;

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.ViewHolder> implements View.OnClickListener {
    Context mctx;
    int layout;
    List<Product> listproduct;
    int stt;
    int VIEW_TYPE_LOADING=1, VIEW_TYPE_ITEM=0;;




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
    public int getItemViewType(int position) {
       return listproduct.get(position)==null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.textviewTen.setText(listproduct.get(position).getName());
        holder.textViewGia.setText(OtherUltil.fomattien.format(listproduct.get(position).getPrice()) + "đ");
        if (listproduct.get(position).getImages().size() != 0) {
            LoadImage.getImageInServer(mctx, listproduct.get(position).getImages().get(0), holder.imageView);
        } else {
            LoadImage.getImageInServer(mctx, "image/image/thumbnail.png", holder.imageView);
        }

        holder.linearLayoutItemProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalApplication globalApplication = (GlobalApplication) mctx.getApplicationContext();
                //globalApplication.product=listproduct.get(position);
                if (globalApplication.product == null) {
                    globalApplication.product = new Product();

                }
                globalApplication.product = listproduct.get(position);

                Intent intent = new Intent(mctx, DetailActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mctx.startActivity(intent);


            }
        });
        holder.iconheart.setColorFilter(0x00000000);
        holder.iconheart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.iconheart.setColorFilter(0xffff0000);
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
        ImageView imageView,iconheart;
        TextView textviewTen, textViewGia;
        RelativeLayout linearLayoutItemProduct;


        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.ImageanhSP);
            textviewTen = (TextView) itemView.findViewById(R.id.textviewTenSP);
            textViewGia = (TextView) itemView.findViewById(R.id.textviewGiaSP);
            linearLayoutItemProduct = (RelativeLayout) itemView.findViewById(R.id.itemProduct);
            iconheart=(ImageView) itemView.findViewById(R.id.btn_favorite_ItemProduct);

        }
    }


}
