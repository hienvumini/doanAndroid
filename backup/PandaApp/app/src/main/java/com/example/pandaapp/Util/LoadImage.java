package com.example.pandaapp.Util;

import android.content.Context;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pandaapp.R;
import com.example.pandaapp.Retrofit2.APIUltils;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.squareup.picasso.Picasso;

import java.io.File;

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
//                Picasso.with(context).load(APIUltils.BaseURL+url).error(R.drawable.errror).into(imageview);

                ImageLoader imageLoader = ImageLoader.getInstance();
                File cacheDir = StorageUtils.getCacheDirectory(context);
                ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                        .memoryCacheExtraOptions(480, 800) // default = device screen dimensions
                        .diskCacheExtraOptions(480, 800, null)
                        .threadPriority(Thread.NORM_PRIORITY - 1) // default
                        .tasksProcessingOrder(QueueProcessingType.FIFO) // default
                        .denyCacheImageMultipleSizesInMemory()
                        .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                        .memoryCacheSize(2 * 1024 * 1024)
                        .memoryCacheSizePercentage(13) // default
                        .diskCacheSize(50 * 1024 * 1024)
                        .diskCacheFileCount(100)
                        .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
                        .imageDownloader(new BaseImageDownloader(context)) // default
                        .imageDecoder(new BaseImageDecoder(true)) // default
                        .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
                        .writeDebugLogs()
                        .build();
                imageLoader.init(config);

                imageLoader.displayImage(APIUltils.BaseURL+url,imageview);
            }
        }


    }
}
