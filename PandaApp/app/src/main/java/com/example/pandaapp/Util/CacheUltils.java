package com.example.pandaapp.Util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.pandaapp.Models.Product;
import com.example.pandaapp.R;
import com.example.pandaapp.Retrofit2.APIUltils;
import com.example.pandaapp.Retrofit2.DataClient;
import com.example.pandaapp.adapter.AdapterProduct;

import java.io.File;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CacheUltils {
    Context mctx;
    GlobalApplication globalApplication;

    public CacheUltils(Context context) {
        this.mctx = context;
    }

    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if (dir != null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

    public int RefreshProduct(int idproduct) {
        DataClient getProductCategory = APIUltils.getData();
        Call<Product> productCall = getProductCategory.getProduct(idproduct);
        productCall.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (globalApplication == null) {
                    globalApplication = (GlobalApplication) mctx.getApplicationContext();
                }
                globalApplication.product = response.body();
                System.out.println("000"+response.body().toString());
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {

            }
        });

        return 1;
    }

}
