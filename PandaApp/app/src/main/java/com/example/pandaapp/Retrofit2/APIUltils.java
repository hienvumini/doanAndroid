package com.example.pandaapp.Retrofit2;

public class APIUltils {
    public static  final  String BaseURL="http://192.168.100.7/pandaapAPI/";
    public static DataClient getData(){
        return RetrofitClient.getClient(BaseURL).create(DataClient.class);
    }
}
