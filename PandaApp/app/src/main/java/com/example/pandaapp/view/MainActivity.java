package com.example.pandaapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.Toolbar;
import android.widget.ViewFlipper;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.pandaapp.CartActivity;
import com.example.pandaapp.DAO.ProductDAO;
import com.example.pandaapp.Models.Product;
import com.example.pandaapp.R;
import com.example.pandaapp.adapter.MainAdapter;
import com.example.pandaapp.server.Server;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    List<Product> ListProduct;
    SearchView searchView;
    ArrayList<Product> listproduct;
    MainAdapter mainAdapter;
    RecyclerView recyclerView;
    ViewFlipper viewFlipper;
    ImageView imgmyCart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fakedata();
        init();
        recyclerView.setAdapter(mainAdapter);
        imgmyCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), CartActivity.class);
                int idaccount=1;
                intent.putExtra("idaccount",idaccount);
                startActivity(intent);
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this, "say hello", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Toast.makeText(MainActivity.this, "Abc", Toast.LENGTH_SHORT).show();
                return false;
            }
        });


    }
    public void fakedata(){
        listproduct=new ArrayList<>();
        ArrayList<String> manganh=new ArrayList<>();
        manganh.add("https://cf.shopee.vn/file/b094ce2dc84d13b302e147e1b3cfa6d8");
        manganh.add("https://cf.shopee.vn/file/b094ce2dc84d13b302e147e1b3cfa6d8");
        manganh.add("https://cf.shopee.vn/file/b094ce2dc84d13b302e147e1b3cfa6d8");
        manganh.add("https://cf.shopee.vn/file/b094ce2dc84d13b302e147e1b3cfa6d8");
listproduct.add(new Product("Ao 1",150000,"Tu",manganh));
listproduct.add(new Product("Ao 1",150000,"Tu",manganh));
listproduct.add(new Product("Ao 1",150000,"Tu",manganh));
listproduct.add(new Product("Ao 1",150000,"Tu",manganh));
listproduct.add(new Product("Ao 1",150000,"Tu",manganh));

    }
    public void  init(){

        mainAdapter=new MainAdapter(getApplicationContext(),R.id.recycleviewLastlate,listproduct);
        recyclerView=(RecyclerView) findViewById(R.id.recycleviewLastlate);
        RecyclerView.LayoutManager layoutManager= new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        viewFlipper=(ViewFlipper) findViewById(R.id.viewflipperquangcao);
        ActionViewflipper();
        imgmyCart=(ImageView) findViewById(R.id.imgcartMain);
        searchView=(SearchView) findViewById(R.id.SearchView);



    }

    public void ActionViewflipper() {

        ArrayList<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://cdn.tgdd.vn/2019/10/banner/V17-800-300-800x300-(1).png");
        mangquangcao.add("https://cdn.tgdd.vn/2019/10/banner/800-300-800x300-(2).png");
        mangquangcao.add("https://cdn.tgdd.vn/2019/10/banner/800-300-800x300-(7).png");
        mangquangcao.add("https://cdn.tgdd.vn/2019/10/banner/Realme-5-Teaser-800-300-800x300.png");
        mangquangcao.add("https://cdn.tgdd.vn/2019/10/banner/800-300-800x300-(1).png");
        mangquangcao.add("https://cdn.tgdd.vn/2019/10/banner/Phu-kien-online--800-300-800x300.png");

        for (int i = 0; i < mangquangcao.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(mangquangcao.get(i))
                    .into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);


        }
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);


        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);


    }




    }


