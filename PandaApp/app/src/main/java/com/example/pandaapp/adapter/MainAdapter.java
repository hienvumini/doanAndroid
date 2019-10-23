package com.example.pandaapp.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pandaapp.Models.Product;
import com.example.pandaapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    Context mctx;
    int layout;
    List<Product> listproduct;

    public MainAdapter(Context context, int resource, List<Product> object) {
        this.mctx = context;
        this.layout = resource;
        this.listproduct = object;
    }


    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(mctx).inflate(R.layout.item_product,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, int position) {
        holder.textviewTen.setText(listproduct.get(position).getName());
        holder.textViewGia.setText(listproduct.get(position).getPrice()+"đ");
        Picasso.with(mctx).load(listproduct.get(position).getAnhSP().get(0))
                .placeholder(R.drawable.logo)
                .error(R.drawable.errror)
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return listproduct.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textviewTen,textViewGia;
        public ViewHolder( View itemView) {
            super(itemView);
            imageView=(ImageView) itemView.findViewById(R.id.ImageanhSP);
            textviewTen=(TextView)itemView.findViewById(R.id.textviewTenSP);
            textViewGia=(TextView)itemView.findViewById(R.id.textviewGiaSP);
        }
    }
}
