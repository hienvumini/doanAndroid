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

import com.example.pandaapp.view.ListProductCatagoryActivity;
import com.example.pandaapp.Models.Category;
import com.example.pandaapp.R;
import com.example.pandaapp.Util.GlobalApplication;
import com.example.pandaapp.Util.LoadImage;

import java.util.List;

public class AdapterCategory extends RecyclerView.Adapter<AdapterCategory.ViewHolder> {
    Context mctx;
    int layout;
    List<Category> categoryList;

    public AdapterCategory(Context context, int resource, List<Category> object) {
        this.mctx = context;
        this.layout = resource;
        this.categoryList = object;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mctx).inflate(R.layout.item_category, parent, false);

        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        LoadImage.getImageInServer(mctx, categoryList.get(position).getThumbnailCategory(), holder.imageView);
        holder.textView.setText(categoryList.get(position).getNameCategory());
        holder.linnerlayoutitemCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalApplication globalApplication = (GlobalApplication) mctx.getApplicationContext();
                if (globalApplication.category == null) {
                    globalApplication.category = new Category();

                }
                globalApplication.category = categoryList.get(position);

                Intent intent = new Intent(mctx, ListProductCatagoryActivity.class);
                mctx.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        LinearLayout linnerlayoutitemCategory;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageCate_Item);
            textView = (TextView) itemView.findViewById(R.id.textviewCate_Item);
            linnerlayoutitemCategory = (LinearLayout) itemView.findViewById(R.id.linnerlayoutitemCategory);

        }
    }
}
