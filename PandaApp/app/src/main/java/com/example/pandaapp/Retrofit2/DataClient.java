package com.example.pandaapp.Retrofit2;

import com.example.pandaapp.Models.Account;
import com.example.pandaapp.Models.Category;
import com.example.pandaapp.Models.News;
import com.example.pandaapp.Models.Order;
import com.example.pandaapp.Models.OrderCustomer;
import com.example.pandaapp.Models.OrderItemDetail;
import com.example.pandaapp.Models.Product;
import com.example.pandaapp.Models.Revenue;
import com.example.pandaapp.Models.Shop;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

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
    @POST("UpdateProduct.php")
    Call<String> UpdateProduct(@Field("idcategory") int category,
                               @Field("productId") int productId,
                               @Field("name") String tensanpham,
                               @Field("price") double gia,
                               @Field("detail") String mota,
                               @Field("discount") double giamgia
    );


    @FormUrlEncoded
    @POST("getallProductCategory.php")
    Call<ArrayList<Product>> getProductCategory(@Field("idcategory") int idcategory, @Field("offset") int position, @Field("sort") int sortID);

    @FormUrlEncoded
    @POST("getUpdateProduct.php")
    Call<ArrayList<Product>> getUpdateProduct(@Field("limit") int limit, @Field("offset") int offset);

    @FormUrlEncoded
    @POST("InsertImageProduct.php")
    Call<String> InsertImage(@Field("image") String linkanh,
                             @Field("productId") int idProduct

    );

    @FormUrlEncoded
    @POST("getFavoriteItem.php")
    Call<ArrayList<Product>> getFavoritesProduct(@Field("accID") int accID);

    @GET("getCategory.php")
    Call<ArrayList<Category>> getCategory();

    @FormUrlEncoded
    @POST("getProductofShop.php")
    Call<ArrayList<Product>> getProductShop(@Field("idShop") int idShop, @Field("limit") int limit, @Field("offset") int offset,@Field("sort") int sortID);


    @FormUrlEncoded
    @POST("InsertBill.php")
    Call<String> addOder(@Field("AccountId") int AccountId, @Field("totalPrice") Double totalPrice
            , @Field("name") String name, @Field("address") String address, @Field("phone_number") String phone_number);

    @FormUrlEncoded
    @POST("insertOderItem.php")
    Call<String> addOderItem(@Field("oderId") String oderId, @Field("productId") int productId, @Field("amount") int amount, @Field("total") double total);

    @FormUrlEncoded
    @POST("checkAccount.php")
    Call<ArrayList<Account>> CheckAccount(@Field("usernameaccount") String username, @Field("passwordaccount") String password);

    @FormUrlEncoded
    @POST("addAccount1.php")
    Call<String> RegisterAccount(@Field("txtusername") String username,
                                 @Field("txtpassword") String password,
                                 @Field("txtnamefull") String txtnamefull,
                                 @Field("txtphone") String txtphone,
                                 @Field("txtaddress") String txtaddress,
                                 @Field("gender") String gender,
                                 @Field("txtemail") String txtemail,
                                 @Field("DateOfBirth") String DateOfBirth);


    @FormUrlEncoded
    @POST("DeleteFileOnServer.php")
    Call<String> DeleteFileonServer(@Field("pathFile") String LinkFile);

    @FormUrlEncoded
    @POST("getProduct.php")
    Call<ArrayList<Product>> getProduct(@Field("productId") int productId);


    @FormUrlEncoded
    @POST("getProductBySearch.php")
    Call<ArrayList<Product>> getProductSearch(@Field("key") String key, @Field("sort") int sortID, @Field("offset") int position);

    @FormUrlEncoded
    @POST("getOrderShop.php")
    Call<ArrayList<Order>> getOrderShop(@Field("idShop") int idShop, @Field("statusId") int statusId);

    @FormUrlEncoded
    @POST("getOrderShop.php")
    Call<ArrayList<Order>> getOrder(@Field("idShop") int idShop, @Field("statusId") int statusId);

    @FormUrlEncoded
    @POST("setStatusOrder.php")
    Call<String> setStatusOrderShop(@Field("oderId") int oderId, @Field("statusId") int statusId);

    @FormUrlEncoded
    @POST("getOrderofCustomers.php")
    Call<ArrayList<OrderCustomer>> getOrderCustomer(@Field("AccountId") int AccountId, @Field("statusId") int statusId);

    @FormUrlEncoded
    @POST("checkAccountFB.php")
    Call<ArrayList<Account>> CheckAccountFB(@Field("usernameaccount") String username);

    @FormUrlEncoded
    @POST("getNews.php")
    Call<ArrayList<News>> getNews(@Field("offset") int offset);


    @FormUrlEncoded
    @POST("insertFavorite.php")
    Call<String> insertFavorite(@Field("AccountId") int AccountId, @Field("productId") int productId, @Field("isfavorite") int isfavorite);

    @FormUrlEncoded
    @POST("checkFavorite.php")
    Call<String> checkFavorite(@Field("AccountId") int AccountId, @Field("productId") int productId);

    @FormUrlEncoded
    @POST("getTotalRevenueShop.php")
    Call<String> getTotalRevenueShop(@Field("idShop") int idShop, @Field("fromdate") String fromdate, @Field("todate") String todate);

    @FormUrlEncoded
    @POST("getDetailRevenueShop.php")
    Call<String> getDetailRevenueShop(@Field("idShop") int idShop, @Field("date") String date);

    @FormUrlEncoded
    @POST("getListRevenueShop.php")
    Call<ArrayList<Revenue>> getListRevenueShop(@Field("idShop") int idShop, @Field("fromdate") String fromdate, @Field("todate") String todate);


    @FormUrlEncoded
    @POST("getOrderItemDetail.php")
    Call<ArrayList<OrderItemDetail>> getOrderItemDetail(@Field("oderId") int oderId);

    @FormUrlEncoded
    @POST("OpenShop.php")
    Call<String> OpenShop(@Field("shopName") String shopName,
                          @Field("introduceshop") String introduceshop,
                          @Field("addressshop") String addressshop,
                          @Field("phoneshop") String phoneshop,
                          @Field("emailshop") String emailshop,
                          @Field("AccountId") int AccountId);

    @FormUrlEncoded
    @POST("EditAccount.php")
    Call<String> EditAccount(
            @Field("txtnamefull") String Name,
            @Field("txtphone") String Phone,
            @Field("txtaddress") String Address,
            @Field("txtemail") String Email,
            @Field("DateOfBirth") String DateofBirth,
            @Field("gender") int Gioitinh,
            @Field("AccountId") int AccountId);

    @FormUrlEncoded
    @POST("Changepassword.php")
    Call<String> ChangePass(
            @Field("AccountId") int AccountId,
            @Field("oldPass") String oldPass,
            @Field("NewsPass") String NewsPass
    );

    @FormUrlEncoded
    @POST("getInfoShop.php")
    Call<ArrayList<Shop>> getInfoShop(
            @Field("idShop") int idShop
    );

    @FormUrlEncoded
    @POST("EditShop.php")
    Call<String> EditShop(
            @Field("idShop") int idShop,
            @Field("shopName") String shopName,
            @Field("introduce") String introduce,
            @Field("address") String address,
            @Field("phone") String phone,
            @Field("email") String email);

    @FormUrlEncoded
    @POST("DeleteProduct.php")
    Call<String> DeleteProduct(
            @Field("productId") int productId);

}
