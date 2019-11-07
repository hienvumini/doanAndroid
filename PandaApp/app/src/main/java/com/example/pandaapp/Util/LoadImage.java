package com.example.pandaapp.Util;

import android.content.Context;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pandaapp.R;
import com.example.pandaapp.Retrofit2.APIUltils;
import com.squareup.picasso.Picasso;

public class LoadImage {
    public static void getImageInternet(Context context, String url, ImageView imageview){
        if (url ==null || url.equalsIgnoreCase("")) {
            Toast.makeText(context, "Link ảnh trống", Toast.LENGTH_SHORT).show();
        }else {
            Picasso.with(context).load(url).error(R.drawable.errror).into(imageview);
        }

    }
    public static void getImageInServer(Context context, String url, ImageView imageview){
        if (url != null) {
            if (url ==null || url.equalsIgnoreCase("")) {
                Toast.makeText(context, "Link ảnh trống", Toast.LENGTH_SHORT).show();
            }else {
                Picasso.with(context).load(APIUltils.BaseURL+url).error(R.drawable.errror).into(imageview);
            }
        }


    }
}
