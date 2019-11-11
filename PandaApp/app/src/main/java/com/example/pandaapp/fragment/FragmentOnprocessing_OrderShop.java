package com.example.pandaapp.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.pandaapp.Models.Order;
import com.example.pandaapp.R;
import com.example.pandaapp.Retrofit2.APIUltils;
import com.example.pandaapp.Retrofit2.DataClient;
import com.example.pandaapp.Util.GlobalApplication;
import com.example.pandaapp.adapter.AdapterOrder;
import com.example.pandaapp.view.OrderManagerActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentOnprocessing_OrderShop extends Fragment {
    ListView listView;
    AdapterOrder adapterOrder;
    ArrayList<Order> lstOrder;
    GlobalApplication globalApplication;
    callbackActivity callbackActivity;
    int statusOrrder;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_onprocessing_order, container, false);
        init(view);


        callbackActivity = (callbackActivity) getActivity();
        Toast.makeText(getActivity(), "Tab đang chọn" + callbackActivity.gettab(), Toast.LENGTH_SHORT).show();
        switch (callbackActivity.gettab()) {
            case 0:
                statusOrrder = 1;
                break;
            case 1:
                statusOrrder = 2;
                break;
            case 2:
                statusOrrder = 3;
                break;
            case 3:
                statusOrrder = 4;
                break;


        }
        lstOrder.clear();
        adapterOrder=new AdapterOrder(getActivity(),R.id.listviewOrderDetail,lstOrder);
        adapterOrder.notifyDataSetChanged();
        getOrder(view, statusOrrder);
        return view;

    }


    private void init(View view) {
        listView = (ListView) view.findViewById(R.id.listviewOrder);
        lstOrder = new ArrayList<>();
        adapterOrder = new AdapterOrder(getActivity(), R.id.listviewOrder, lstOrder);

        globalApplication = (GlobalApplication) getActivity().getApplicationContext();
       adapterOrder.clear();

    }

    private void getOrder(View view, int stt) {
        DataClient dataClient = APIUltils.getData();
        Call<ArrayList<Order>> listCall = dataClient.getOrderShop(globalApplication.account.getIdShop(), stt);
        listCall.enqueue(new Callback<ArrayList<Order>>() {
            @Override
            public void onResponse(Call<ArrayList<Order>> call, Response<ArrayList<Order>> response) {
                Log.d("A1A1", "onResponse: " + response.body());

                lstOrder = response.body();
                adapterOrder = new AdapterOrder(getActivity(), R.id.listviewOrder, lstOrder);
                listView.setAdapter(adapterOrder);


            }

            @Override
            public void onFailure(Call<ArrayList<Order>> call, Throwable t) {
                Log.d("A2A3", "onResponse: " + t.toString());

            }
        });
    }

    public interface callbackActivity {
        public int gettab();

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }
}
