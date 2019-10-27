package com.example.pandaapp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.Toolbar;
import android.widget.ViewFlipper;

import com.example.pandaapp.fragment.FragmentCart;
import com.example.pandaapp.fragment.FragmentCategory;
import com.example.pandaapp.fragment.FragmentMain;
import com.example.pandaapp.fragment.FragmentProfile;
import com.example.pandaapp.fragment.FragmentSearch;
import com.example.pandaapp.Models.Account;
import com.example.pandaapp.Models.Product;
import com.example.pandaapp.R;
import com.example.pandaapp.adapter.AdapterProduct;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterProduct.AdapterItemClickListener {
    Toolbar toolbar;
    List<Product> ListProduct;
    SearchView searchView;
    ArrayList<Product> listproduct;
    AdapterProduct mainAdapter;
    RecyclerView recyclerView;
    ViewFlipper viewFlipper;
    ImageView imgmyCart;
    Account account0 = new Account();
    BottomNavigationView nav_bottom_MainActivity;
    FragmentCategory fragmentCategory = new FragmentCategory();
    FragmentSearch fragmentSearch = new FragmentSearch();
    FragmentProfile fragmentProfile = new FragmentProfile();
    FragmentCart fragmentCart = new FragmentCart();
    FragmentMain fragmentMain = new FragmentMain();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openFragment(fragmentMain);
        Intent intent = getIntent();
        account0 = (Account) intent.getSerializableExtra("account");
        Toast.makeText(this, "Chào mừng " + account0.getName(), Toast.LENGTH_SHORT).show();
        nav_bottom_MainActivity = findViewById(R.id.ctNavigationbotton);
        nav_bottom_MainActivity.setOnNavigationItemSelectedListener(categoryFragmentListennerItem);

    }


    private void openFragment(final Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.framMainActivity, fragment);
        transaction.commit();

    }

    public BottomNavigationView.OnNavigationItemSelectedListener categoryFragmentListennerItem = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment fragmentselect = null;
            switch (menuItem.getItemId()) {
                case R.id.menu_nav_home:
                    fragmentselect = fragmentMain;
                    break;
                case R.id.menu_nav_cate:
                    fragmentselect = fragmentCategory;

                    break;
                case R.id.menu_nav_seach:
                    fragmentselect = fragmentSearch;

                    break;
                case R.id.menu_nav_profile:

                    fragmentselect = fragmentProfile;
                    break;


            }
            openFragment(fragmentselect);

            return true;
        }
    };

    @Override
    public void onClick(View view, int position, boolean isLongClick) {

    }

    public void setGlobal() {


    }
}


