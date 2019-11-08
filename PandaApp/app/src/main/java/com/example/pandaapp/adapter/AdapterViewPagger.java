package com.example.pandaapp.adapter;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.pandaapp.Models.Product;
import com.example.pandaapp.R;
import com.example.pandaapp.Util.LoadImage;

import java.util.List;

public class AdapterViewPagger extends PagerAdapter {
    Activity activity;
    List<String> images;
    LayoutInflater layoutInflater;


    public AdapterViewPagger(Activity activity, List<String> images) {
        this.activity = activity;
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view=LayoutInflater.from(activity).inflate(R.layout.viewpager_item,container,false);
        ImageView imageView=(ImageView) view.findViewById(R.id.imageview_ViewPagerItem);
        DisplayMetrics displayMetrics=new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height=displayMetrics.heightPixels;
        int width=displayMetrics.widthPixels;
        imageView.setMinimumHeight(height);
        imageView.setMinimumHeight(width);
        LoadImage.getImageInServer(activity,images.get(position),imageView);
        container.addView(view);
        TextView textView=(TextView) view.findViewById(R.id.textviewSTTImage);
        textView.setText(position+1+"/"+images.size());
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ((ViewPager) container).removeView((View) object);
    }
}
