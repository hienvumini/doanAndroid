package com.example.pandaapp.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pandaapp.Models.Category;
import com.example.pandaapp.Models.Product;
import com.example.pandaapp.R;
import com.example.pandaapp.Retrofit2.APIUltils;
import com.example.pandaapp.Retrofit2.DataClient;
import com.example.pandaapp.Util.CacheUltils;
import com.example.pandaapp.Util.FileUtils;
import com.example.pandaapp.Util.GlobalApplication;
import com.example.pandaapp.adapter.AdapterAddImage;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProductActivity extends AppCompatActivity {
    GlobalApplication globalApplication;
    ImageView addmoreimage_EditProduct, img_back_AddProduct;
    int REQUEST_CODE_ADDMOREIMAGES = 123;
    private static final int READ_EXTENAL_STORAGE_PERMISION = 0;
    private static final int CAMERA_PERMISION = 1;
    ArrayList<Uri> listUriImage;
    ArrayList<String> listLinkImage;
    ArrayList<String> imageNeedDelete;
    ArrayList<Uri> imageThem;
    AdapterAddImage adapterAddImage;
    GridView gridViewImage;
    ImageView btnsubmit, btnback;
    String realpath = "";
    EditText txtNameProduct, txtDiscProduct, txtPrice, txtDiscount;
    Spinner spinnerCategory;
    String name, disc;
    double price, discount;
    int idCate, idshop;
    Product product;
    ArrayList<Category> categoryArrayList = new ArrayList<>();
    ArrayAdapter adapterspinner;
    int idproductadd;
    TextView textViewNotifi;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);
        init();
        setbackProduct();
        onListener();



    }


    private void init() {
        progressBar = (ProgressBar) findViewById(R.id.process_EditProduct);
        globalApplication = (GlobalApplication) getApplicationContext();
        product = globalApplication.product;
        btnback = (ImageView) findViewById(R.id.img_back_black_EditProduct);
        textViewNotifi = (TextView) findViewById(R.id.textviewNoti_setImageProduct_EditProduct);
        img_back_AddProduct = (ImageView) findViewById(R.id.img_back_black_EditProduct);
        txtNameProduct = (EditText) findViewById(R.id.edittextName_EditProduct);
        txtDiscProduct = (EditText) findViewById(R.id.edittextdisc_EditProduct);
        txtPrice = (EditText) findViewById(R.id.edittextPrice_EditProduct);
        txtDiscount = (EditText) findViewById(R.id.edittextPriceDiscount_EditProduct);
        spinnerCategory = (Spinner) findViewById(R.id.spinnerCategory_EditProduct);

        getCategory();
        btnsubmit = (ImageView) findViewById(R.id.imageviewSubmit_EditProduct);
        addmoreimage_EditProduct = (ImageView) findViewById(R.id.addmoreimage_EditProduct);
        gridViewImage = (GridView) findViewById(R.id.gridview_Image_EditProduc);
        listUriImage = new ArrayList<>();
        listLinkImage = new ArrayList<>();
        imageNeedDelete = new ArrayList<>();
        imageThem = new ArrayList<>();
        adapterAddImage = new AdapterAddImage(getApplicationContext(), R.id.gridview_Image_EditProduc, listUriImage);
        gridViewImage.setAdapter(adapterAddImage);
        if (listUriImage.size() == 0) {
            gridViewImage.setVisibility(View.GONE);

        }


    }

    private void setbackProduct() {
        Toast.makeText(getApplicationContext(), product.toString(), Toast.LENGTH_SHORT).show();
        txtNameProduct.setText(product.getName());
        txtDiscProduct.setText(product.getDis());
        txtPrice.setText(product.getPrice() + "");
        txtDiscount.setText(product.getDiscount() + "");
        int sttcategory;
        for (int i = 0; i < categoryArrayList.size(); i++) {
            if (product.getIdcategory() == categoryArrayList.get(i).getIdcategory()) {
                spinnerCategory.setSelection(i);
            }
        }
        listLinkImage = globalApplication.product.getImages();

        for (int i = 0; i < listLinkImage.size(); i++) {
            listUriImage.add(Uri.parse(APIUltils.BaseURL + listLinkImage.get(i)));
        }
        for (int i = 0; i < listUriImage.size(); i++) {
            System.out.println(listUriImage.get(i));
        }
        Log.d("AAA", "setbackProduct: " + product.toString());
        adapterAddImage = new AdapterAddImage(getApplicationContext(), R.id.gridview_Image_EditProduc, listUriImage);
        gridViewImage.setVisibility(View.VISIBLE);
        gridViewImage.setAdapter(adapterAddImage);

    }

    private void getCategory() {

        DataClient dataClientgetcate = APIUltils.getData();
        dataClientgetcate.getCategory();
        Call<ArrayList<Category>> arrayListCallCate = dataClientgetcate.getCategory();
        arrayListCallCate.enqueue(new Callback<ArrayList<Category>>() {
            @Override
            public void onResponse(Call<ArrayList<Category>> call, Response<ArrayList<Category>> response) {
                if (response.body().size() > 0) {
                    categoryArrayList = response.body();
                    ArrayList<String> listCate = new ArrayList<>();
                    for (int i = 0; i < categoryArrayList.size(); i++) {
                        listCate.add(categoryArrayList.get(i).getCategoryName());

                    }
                    adapterspinner = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, listCate);
                    spinnerCategory.setAdapter(adapterspinner);

                }
            }

            @Override
            public void onFailure(Call<ArrayList<Category>> call, Throwable t) {
                Log.d("GetCate", "onFailure: " + t.toString());
            }
        });

    }

    public void onListener() {
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        addmoreimage_EditProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_CODE_ADDMOREIMAGES);
            }
        });
        adapterAddImage.setListener(new AdapterAddImage.IcallbackAddProductActivity() {
            @Override
            public void removeImage(int position) {
                Toast.makeText(getApplicationContext(), "Xoa anh thu " + position, Toast.LENGTH_SHORT).show();
                FileUtils fileUtils = new FileUtils();
                Toast.makeText(getApplicationContext(), listLinkImage.get(position), Toast.LENGTH_SHORT).show();

                imageNeedDelete.add(listLinkImage.get(position));
                listUriImage.remove(position);
                listLinkImage.remove(position);

                adapterAddImage.notifyDataSetChanged();
                //gridViewImage.setAdapter(adapterAddImage);


            }
        });
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateProduct();


                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                startActivity(intent);


            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_ADDMOREIMAGES && resultCode == RESULT_OK && data != null) {
            gridViewImage.setVisibility(View.VISIBLE);
            String[] filepath = {MediaStore.Images.Media.DATA};

            if (data.getClipData() != null) {
                int count = data.getClipData().getItemCount();
                for (int i = 0; i < count; i++) {
                    Uri uri = data.getClipData().getItemAt(i).getUri();
                    imageThem.add(uri);
                    realpath = FileUtils.getRealPathFromURI(uri, getApplicationContext());
                }
                listUriImage.addAll(imageThem);
                gridViewImage.setAdapter(adapterAddImage);
            } else {
                Uri imgUri = data.getData();
                imageThem.add(imgUri);

            }


        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void getdataFromUser() {

        name = txtNameProduct.getText().toString().trim();
        disc = txtDiscProduct.getText().toString().trim();
        if (!(txtPrice.getText().toString().trim().equalsIgnoreCase(""))) {
            price = Double.parseDouble(txtPrice.getText().toString().trim());
        } else {
            price = 0;
        }
        if (!(txtDiscount.getText().toString().trim().equalsIgnoreCase(""))) {
            discount = Double.parseDouble(txtDiscount.getText().toString().trim());
        } else {
            discount = 0;
        }
        idCate = categoryArrayList.get(spinnerCategory.getSelectedItemPosition()).getIdcategory();
        GlobalApplication globalApplication = (GlobalApplication) getApplicationContext();
        idshop = globalApplication.account.getIdShop();
        product.setName(name);
        product.setDis(disc);
        product.setPrice(price);
        product.setDiscount(discount);
        product.setIdcategory(idCate);

        //Toast.makeText(getApplicationContext(), product.toString(), Toast.LENGTH_SHORT).show();


    }

    private void UpdateProduct() {
        if (txtNameProduct.getText().toString().trim().equalsIgnoreCase("") || txtDiscProduct.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin sản phẩm", Toast.LENGTH_SHORT).show();
        } else {
            getdataFromUser();


            System.out.println(product.toString());
            DataClient DataclientUpdate = APIUltils.getData();
            Call<String> stringCall = DataclientUpdate.UpdateProduct(product.getIdcategory(), product.getProductId(), product.getName(), product.getPrice(), product.getDis(), product.getDiscount());
            stringCall.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    System.out.println("A123" + response.body());

                    for (int i = 0; i < imageThem.size(); i++) {
                        System.out.println(FileUtils.getRealPathFromURI(listUriImage.get(i), getApplicationContext()));
                        FileUtils.UploadImageProduct(getApplicationContext(), imageThem.get(i), product.getProductId());

                    }
                    for (int i = 0; i < imageNeedDelete.size(); i++) {
                        System.out.println("CONG" + imageNeedDelete.get(i));
                        FileUtils.DeleteFileonServer(FileUtils.convertUritoImage(imageNeedDelete.get(i)));
                    }
                    if (response.body().contains("Success")) {
                        CacheUltils cacheUltils = new CacheUltils(getApplicationContext());
                        cacheUltils.RefreshProduct(idproductadd);
                        Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                        startActivity(intent);
                        finish();
                    }


                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.d("FFF", "onResponse: " + t.toString());

                }
            });


        }

    }

}

