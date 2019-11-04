package com.example.pandaapp.Retrofit2;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface DataClient {
@Multipart // Gửi dũ liệu dạng file không phải text
    @POST("UploadHinhAnh.php") // truyền vào tên file php thực thi
    Call<String> UploadPhoto(@Part MultipartBody.Part photo); //@Part dùng để gửi gửi dữ liệu lên server âm thanh, hình ảnh...
}
