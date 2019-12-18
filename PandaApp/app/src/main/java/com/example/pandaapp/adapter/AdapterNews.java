package com.example.pandaapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Size;

import com.example.pandaapp.Models.News;
import com.example.pandaapp.R;
import com.example.pandaapp.Util.GlobalApplication;
import com.example.pandaapp.Util.LoadImage;
import com.example.pandaapp.view.NewsDetailActivity;

import java.util.List;

public class AdapterNews extends ArrayAdapter {
    Context mctx;
    int layout;
    List<News> newsList;
    GlobalApplication globalApplication;
    public AdapterNews(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        this.mctx=context;
        this.layout=resource;
        this.newsList=objects;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view=convertView;
        if (view == null) {
            view = LayoutInflater.from(mctx).inflate(R.layout.itemnews,parent,false);
        }
        ImageView imageView=(ImageView) view.findViewById(R.id.img_News);
        TextView textViewttile=(TextView) view.findViewById(R.id.txt_title_News);
        TextView textViewContent=(TextView) view.findViewById(R.id.txt_content_News);
        TextView textViewDate=(TextView) view.findViewById(R.id.txt_date_News);
        LoadImage.getImageInServer(mctx,newsList.get(position).getImageNews(),imageView);
        textViewttile.setText(newsList.get(position).getNewstitle());
        textViewContent.setText((Html.fromHtml(Html.fromHtml(newsList.get(position).getDetailNews()).toString())));
        textViewContent.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
        textViewDate.setText(newsList.get(position).getDateCreated());
        LinearLayout linearLayout=(LinearLayout) view.findViewById(R.id.item_news);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mctx,NewsDetailActivity.class);
                globalApplication=(GlobalApplication) mctx.getApplicationContext();
                globalApplication.news=newsList.get(position);
                mctx.startActivity(intent);

            }
        });
        Animation animation_cycle = AnimationUtils.loadAnimation(mctx, R.anim.animation_listview);
        linearLayout.setAnimation(animation_cycle);
        return view;
    }
}
