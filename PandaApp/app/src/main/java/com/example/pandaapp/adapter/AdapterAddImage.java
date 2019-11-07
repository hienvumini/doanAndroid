package com.example.pandaapp.adapter;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.pandaapp.R;
import com.example.pandaapp.Util.ImageProcess;
import com.example.pandaapp.view.AddProductActivity;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public class AdapterAddImage extends ArrayAdapter {
    Context mctx;
    int layout;
    List<Uri> uriListImage;

    public AdapterAddImage(Context context, int resource, List objects) {

        super(context, resource, objects);
        this.mctx = context;
        this.layout = resource;
        this.uriListImage = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(mctx).inflate(R.layout.item_images, parent, false);
        }
        ImageView imageView = (ImageView) view.findViewById(R.id.imageImageProduct_ItemImage);
        TextView textView = (TextView) view.findViewById(R.id.textviewremoveImage_ItemImage);
//        try {
//            InputStream inputStream = getContext().getContentResolver().openInputStream(uriListImage.get(position));
//            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//           // bitmap=ImageProcess.getResizedBitmap(bitmap,150);
//            imageView.setImageBitmap(bitmap);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
        Glide.with(mctx.getApplicationContext()).load(uriListImage.get(position)).override(150, 150).centerCrop().into(imageView);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uriListImage.remove(position);
                AddProductActivity  addProductActivity=new AddProductActivity();
                addProductActivity.removeImage(position);




            }
        });

        return view;
    }
    public static interface IcallbackAddProductActivity{
        public  void removeImage(int position);

    }

}
