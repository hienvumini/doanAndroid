package com.example.pandaapp.Util;

import android.content.Context;

import com.example.pandaapp.Models.Product;
import com.example.pandaapp.Retrofit2.APIUltils;
import com.example.pandaapp.Retrofit2.DataClient;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderUltils {
    public static String result = "";


    public static void updateOrderStatus(final Context context, int orderID, final int status) {


        DataClient dataClient = APIUltils.getData();
        Call<String> stringCall = dataClient.setStatusOrderShop(orderID, status);
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {


                if (response.body().equalsIgnoreCase("Success")) {
                    switch (status) {
                        case 2:
                            Toasty.success(context.getApplicationContext(), "Xác nhận đơn hàng thành công", 3000, true).show();
                            break;
                        case 3:
                            Toasty.success(context.getApplicationContext(), "Xác nhận giao hàng thành công", 3000, true).show();
                            break;
                        case 4:
                            Toasty.success(context.getApplicationContext(), "Xác nhận hủy thành công", 3000, true).show();
                            break;

                    }
                } else {
                    if (response.body().equalsIgnoreCase("Fail")) {
                        switch (status) {
                            case 2:
                                Toasty.error(context.getApplicationContext(), "Xác nhận đơn hàng thất bại", 3000, true).show();
                                break;
                            case 3:
                                Toasty.error(context.getApplicationContext(), "Xác nhận giao hàng thất bại", 3000, true).show();
                                break;
                            case 4:
                                Toasty.error(context.getApplicationContext(), "Xác nhận hủy thất bại", 3000, true).show();
                                break;

                        }

                    }

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toasty.error(context.getApplicationContext(), "Có lỗi xảy ra", 2000, true);
            }
        });

    }

    public static double getDiscount(int productID) {
        DataClient dataClient = APIUltils.getData();
        Call<ArrayList<Product>> arrayListCall = dataClient.getProduct(productID);
        arrayListCall.enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {

            }

            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {

            }
        });
        return 1;
    }
}
