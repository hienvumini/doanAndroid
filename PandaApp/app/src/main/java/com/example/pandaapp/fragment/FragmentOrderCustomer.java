package com.example.pandaapp.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.pandaapp.Models.Order;
import com.example.pandaapp.Models.OrderCustomer;
import com.example.pandaapp.R;
import com.example.pandaapp.Retrofit2.APIUltils;
import com.example.pandaapp.Retrofit2.DataClient;
import com.example.pandaapp.Util.GlobalApplication;
import com.example.pandaapp.adapter.AdapterOrderCustomer;
import com.example.pandaapp.adapter.AdapterOrderShop;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentOrderCustomer extends Fragment {
    ListView listView;
    AdapterOrderCustomer adapterOrderCustomer;
    ArrayList<OrderCustomer> lstOrder;
    GlobalApplication globalApplication;
    callbackActivityCustomer callbackActivity;
    int statusOrrder;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragmentordercustomer, container, false);
        init(view);


        callbackActivity = (callbackActivityCustomer) getActivity();

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
        adapterOrderCustomer = new AdapterOrderCustomer(getActivity(), R.id.listviewCustomerOrder, lstOrder);
        adapterOrderCustomer.notifyDataSetChanged();
        getOrder(view, statusOrrder);
        return view;

    }


    private void init(View view) {
        listView = (ListView) view.findViewById(R.id.listviewCustomerOrder);
        lstOrder = new ArrayList<>();
        adapterOrderCustomer = new AdapterOrderCustomer(getActivity(), R.id.listviewCustomerOrder, lstOrder);

        globalApplication = (GlobalApplication) getActivity().getApplicationContext();
        adapterOrderCustomer.clear();

    }

    private void getOrder(View view, int stt) {
        DataClient dataClient = APIUltils.getData();
        Call<ArrayList<OrderCustomer>> listCall = dataClient.getOrderCustomer(globalApplication.account.getAccountId(),stt);
        listCall.enqueue(new Callback<ArrayList<OrderCustomer>>() {
            @Override
            public void onResponse(Call<ArrayList<OrderCustomer>> call, Response<ArrayList<OrderCustomer>> response) {
                Toast.makeText(getActivity(), response.body().size()+"SL", Toast.LENGTH_SHORT).show();
                Log.d("A2A2", "onResponse: " + response.body());


                lstOrder = response.body();
                adapterOrderCustomer = new AdapterOrderCustomer(getActivity(), R.id.listviewCustomerOrder, lstOrder);
                listView.setAdapter(adapterOrderCustomer);


            }

            @Override
            public void onFailure(Call<ArrayList<OrderCustomer>> call, Throwable t) {
                Log.d("A2A3", "onResponse: " + t.toString());

            }
        });
    }



    public interface callbackActivityCustomer {
        public int gettab();

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commitAllowingStateLoss();
        }
    }


}
