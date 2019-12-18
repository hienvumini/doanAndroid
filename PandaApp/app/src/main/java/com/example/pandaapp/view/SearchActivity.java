package com.example.pandaapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.pandaapp.R;

public class SearchActivity extends AppCompatActivity {
    SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchView = (SearchView) findViewById(R.id.SearchView_search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query == null) {
                    Toast.makeText(getBaseContext(), "Trống", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(getBaseContext(), ListProductSearchActivity.class);
                    intent.putExtra("key",query);
                    startActivity(intent);
                    finish();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


    }
}
