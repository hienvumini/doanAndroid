package com.example.pandaapp.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pandaapp.Models.Category;
import com.example.pandaapp.R;
import com.example.pandaapp.Retrofit2.APIUltils;
import com.example.pandaapp.Retrofit2.DataClient;
import com.example.pandaapp.adapter.AdapterCategory;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentCategory extends Fragment {
    RecyclerView recyclerViewListCate;
    AdapterCategory adapterCategory;
    ArrayList<Category> categoryList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        recyclerViewListCate = (RecyclerView) view.findViewById(R.id.recycleviewListCate);
        categoryList = new ArrayList<>();

        getListCategory();
        adapterCategory = new AdapterCategory(getActivity(), R.id.recycleviewListCate, categoryList);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        LinearLayoutManager linnerlayout
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewListCate.setLayoutManager(layoutManager);
        recyclerViewListCate.setHasFixedSize(true);
        recyclerViewListCate.setAdapter(adapterCategory);


        return view;

    }

    private void init(View view) {


    }

public void getListCategory(){
    DataClient dataClientgetcate = APIUltils.getData();
    dataClientgetcate.getCategory();
    Call<ArrayList<Category>> arrayListCallCate = dataClientgetcate.getCategory();
    arrayListCallCate.enqueue(new Callback<ArrayList<Category>>() {
        @Override
        public void onResponse(Call<ArrayList<Category>> call, Response<ArrayList<Category>> response) {
            if (response.body().size() > 0) {
                categoryList = response.body();
                adapterCategory=new AdapterCategory(getActivity(),R.id.recycleviewListCate,categoryList);
                recyclerViewListCate.setAdapter(adapterCategory);
            }
        }

        @Override
        public void onFailure(Call<ArrayList<Category>> call, Throwable t) {
            Log.d("GetCate", "onFailure: " + t.toString());
        }
    });

}

}
