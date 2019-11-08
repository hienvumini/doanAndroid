package com.example.pandaapp.Util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.example.pandaapp.Retrofit2.APIUltils;
import com.example.pandaapp.Retrofit2.DataClient;
import com.example.pandaapp.view.DetailActivity;
import com.example.pandaapp.view.ListProductShopActivity;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FileUtils {
    String string;

    public static String getRealPathFromURI(Uri contentUri, Context context) {

        String path = null;
        String[] proj = {MediaStore.MediaColumns.DATA};
        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                path = cursor.getString(column_index);
            }
            cursor.close();

        }
        return path;
    }

    public static   void DeleteFileonServer(String url) {

        DataClient dataClient = APIUltils.getData();
        Call<String> stringCall = dataClient.DeleteFileonServer(url);
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });


    }

    public static void UploadImageProduct (final Context context, Uri uri, final int idProduct) {
        File file = null;
            String realpath = FileUtils.getRealPathFromURI(uri, context);

        if (uri != null) {
            file = new File(FileUtils.getRealPathFromURI(uri, context));
            String file_path = file.getAbsolutePath();
            String[] mangtenfile = file_path.split("\\.");
            file_path = mangtenfile[0] + System.currentTimeMillis() + "." + mangtenfile[1];

            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/from-data"), file); //  xác định loai file tải lên
            MultipartBody.Part part = MultipartBody.Part.createFormData("upload_file", file_path, requestBody);
            DataClient dataClient = APIUltils.getData();
            Call<String> callback = dataClient.UploadPhoto(part);
            callback.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    DataClient insertImages = APIUltils.getData();
                    Call<String> stringCall = insertImages.InsertImage("image/ImageProduct/" + response.body(), idProduct);
                    stringCall.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            Toast.makeText(context, response.body(), Toast.LENGTH_SHORT).show();
                            if (response.body().contains("Success")) {
                                Toast.makeText(context, "Chỉnh sửa sản phẩm thành công", Toast.LENGTH_SHORT).show();
                                CacheUltils cacheUltils=new CacheUltils(context);
                                cacheUltils.RefreshProduct(idProduct);
                                Intent intent = new Intent(context, DetailActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                                (new Activity()).finish();

                            }


                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {


                        }
                    });

                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.d("BBB", "Error" + t);
                }
            });
        }
        }

    public static String convertUritoImage(String s) {

        String t = s.replace(APIUltils.BaseURL, "");
        return t;
    }


}
