package com.example.pandaapp.Util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pandaapp.R;
import com.example.pandaapp.server.Server;
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
        if (url ==null || url.equalsIgnoreCase("")) {
            Toast.makeText(context, "Link ảnh trống", Toast.LENGTH_SHORT).show();
        }else {
            Picasso.with(context).load(Server.Link+url).error(R.drawable.errror).into(imageview);
        }

    }
}
