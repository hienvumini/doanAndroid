package com.example.pandaapp.Util;

import com.example.pandaapp.Retrofit2.APIUltils;
import com.example.pandaapp.Retrofit2.DataClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderUltils {
    public static String result = "";

    public static String updateOrderStatus(int orderID, int status) {

        DataClient dataClient = APIUltils.getData();
        Call<String> stringCall = dataClient.setStatusOrderShop(orderID, status);
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                result = response.body();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                result = t.toString();
            }
        });
        return result;
    }
}
