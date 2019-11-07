package com.example.pandaapp.Retrofit2;

import com.example.pandaapp.Models.Account;
import com.example.pandaapp.Models.Category;
import com.example.pandaapp.Models.Product;

import org.json.JSONArray;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface DataClient {
    @Multipart // Gửi dũ liệu dạng file không phải text
    @POST("UploadHinhAnh.php")
        // truyền vào tên file php thực thi
    Call<String> UploadPhoto(@Part MultipartBody.Part photo); //@Part dùng để gửi gửi dữ liệu lên server âm thanh, hình ảnh...

    @FormUrlEncoded
    @POST("InsertProduct.php")
    Call<String> InsertProduct(@Field("idcategory") int category,
                               @Field("idShop") int shop,
                               @Field("name") String tensanpham,
                               @Field("price") double gia,
                               @Field("detail") String mota,
                               @Field("discount") double giamgia
    );

    @FormUrlEncoded
    @POST("InsertImageProduct.php")
    Call<String> InsertImage(@Field("image") String linkanh,
                             @Field("productId") int idProduct

    );

    @GET("getCategory.php")
    Call<ArrayList<Category>> getCategory();

    @FormUrlEncoded
    @POST("getProductofShop.php")
    Call<ArrayList<Product>> getProductShop(@Field("idshop") int idShop);

    @FormUrlEncoded

    @POST("getallProductCategory.php")
    Call<ArrayList<Product>> getProductCategory(@Field("idcategory") int idcategory);

    @FormUrlEncoded
    @POST("checkAccount.php")
    Call<ArrayList<Account>> CheckAccount(@Field("usernameaccount") String username, @Field("passwordaccount") String password);

    @FormUrlEncoded
    @POST("addAccount.php")
    Call<String> RegisterAccount(@Field("txtusername") String username,
                                 @Field("txtpassword") String password,
                                 @Field("idrole") int idrole,
                                 @Field("txtnamefull") String txtnamefull,
                                 @Field("txtphone") String txtphone,
                                 @Field("txtaddress") String txtaddress,
                                 @Field("gender") String gender,
                                 @Field("txtemail") String txtemail,
                                 @Field("DateOfBirth") String DateOfBirth,
                                 @Field("shopName") String shopName);

    @POST("insertBill.php")
    Call<String> addOder(@Field("AccountId") int AccountId,@Field("totalPrice") Double totalPrice
    ,@Field("name") String name,@Field("address") String address,@Field("phone_number") String phone_number);

    @FormUrlEncoded
    @POST("insertOderItem.php")
    Call<String> addOderItem(@Field("oderId") String oderId,@Field("productId") int productId,@Field("amount") int amount,@Field("total") double total);
    @FormUrlEncoded
    @POST("checkAccount.php")
    Call<ArrayList<Account>> CheckAccount(@Field("usernameaccount") String username, @Field("passwordaccount") String password);

    @FormUrlEncoded
    @POST("addAccount.php")
    Call<String> RegisterAccount(@Field("txtusername") String username,
                                 @Field("txtpassword") String password,
                                 @Field("idrole") int idrole,
                                 @Field("txtnamefull") String txtnamefull,
                                 @Field("txtphone") String txtphone,
                                 @Field("txtaddress") String txtaddress,
                                 @Field("gender") String gender,
                                 @Field("txtemail") String txtemail,
                                 @Field("DateOfBirth") String DateOfBirth,
                                 @Field("shopName") String shopName);


}
