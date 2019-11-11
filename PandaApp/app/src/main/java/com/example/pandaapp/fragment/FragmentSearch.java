package com.example.pandaapp.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.pandaapp.R;
import com.example.pandaapp.view.ListProductSearchActivity;


public class FragmentSearch extends Fragment {
    SearchView searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_search, container, false);
        Menu menu;
        searchView = (SearchView) view.findViewById(R.id.SearchView_search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query == null) {
                    Toast.makeText(getActivity(), "Trống", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(getActivity(), ListProductSearchActivity.class);
                    intent.putExtra("key",query);
                    startActivity(intent);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return view;
    }



}
