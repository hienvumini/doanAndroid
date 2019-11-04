package com.example.pandaapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pandaapp.R;
import com.example.pandaapp.Retrofit2.APIUltils;
import com.example.pandaapp.Retrofit2.DataClient;
import com.example.pandaapp.adapter.AdapterAddImage;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProductActivity extends AppCompatActivity implements AdapterAddImage.IcallbackAddProductActivity {

    ImageView addmoreimage_AddProduct;
    int REQUEST_CODE_ADDMOREIMAGES = 123;
    private static final int READ_EXTENAL_STORAGE_PERMISION = 0;
    private static final int CAMERA_PERMISION = 1;
    ArrayList<Uri> imagesEncodedList;
    ArrayList<String> listLinkImage;

    AdapterAddImage adapterAddImage;
    GridView gridViewImage;
    ImageView btnsubmit;
    String realpath = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        init();
        setOnlisterner();


    }

    private void setOnlisterner() {
        addmoreimage_AddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_CODE_ADDMOREIMAGES);

            }
        });
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = null;
                Toast.makeText(AddProductActivity.this, imagesEncodedList.size() + "", Toast.LENGTH_SHORT).show();
                for (int i = 0; i < imagesEncodedList.size(); i++) {
                    Uri uri = imagesEncodedList.get(i);
                    realpath = getRealPathFromURI(uri);

                    file = new File(realpath);
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
                          if (response !=null){

                              listLinkImage.add("\\image\\ImageProduct\\"+response.body());

                          }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Log.d("AAA","Error"+t);
                        }
                    });
                }
                for (int i = 0; i <listLinkImage.size() ; i++) {
                    Log.d("AAA", "onClick: "+listLinkImage.get(i));
                }


            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_ADDMOREIMAGES && resultCode == RESULT_OK && data != null) {
            gridViewImage.setVisibility(View.VISIBLE);
            String[] filepath = {MediaStore.Images.Media.DATA};

            if (data.getClipData() != null) {
                int count = data.getClipData().getItemCount();
                for (int i = 0; i < count; i++) {
                    Uri uri = data.getClipData().getItemAt(i).getUri();
                    imagesEncodedList.add(uri);
                    realpath = getRealPathFromURI(uri);


                }
                gridViewImage.setAdapter(adapterAddImage);
            } else {
                Uri imgUri = data.getData();
                imagesEncodedList.add(imgUri);

            }


        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void init() {
        requestPermision();
        btnsubmit = (ImageView) findViewById(R.id.imageviewSubmit_AddProduct);
        addmoreimage_AddProduct = (ImageView) findViewById(R.id.addmoreimage_AddProduct);
        gridViewImage = (GridView) findViewById(R.id.gridview_Image_AddProduc);
        imagesEncodedList = new ArrayList<>();
        listLinkImage=new ArrayList<>();

        adapterAddImage = new AdapterAddImage(getApplicationContext(), R.id.gridview_Image_AddProduc, imagesEncodedList);
        gridViewImage.setAdapter(adapterAddImage);
        if (imagesEncodedList.size() == 0) {
            gridViewImage.setVisibility(View.GONE);

        }

    }


    private void requestPermision() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTENAL_STORAGE_PERMISION);
            }
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISION);
            }

        }
    }

    @Override
    public void removeImage(int position) {
        imagesEncodedList.remove(imagesEncodedList.get(position));
        adapterAddImage.notifyDataSetChanged();
    }

    public String getRealPathFromURI(Uri contentUri) {
        String path = null;
        String[] proj = {MediaStore.MediaColumns.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }
}



