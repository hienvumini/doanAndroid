package com.example.pandaapp.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pandaapp.Models.Order;
import com.example.pandaapp.Models.OrderCustomer;
import com.example.pandaapp.Models.OrderItemDetail;
import com.example.pandaapp.R;
import com.example.pandaapp.Retrofit2.APIUltils;
import com.example.pandaapp.Retrofit2.DataClient;
import com.example.pandaapp.Util.GlobalApplication;
import com.example.pandaapp.Util.OrderUltils;
import com.example.pandaapp.Util.OtherUltil;
import com.example.pandaapp.adapter.AdapterOrderItem;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetail extends AppCompatActivity {
    ImageView img_back_OrderDetail;
    Button btnProcess_OrderDeatil, btnCancel_Oder;
    TextView tvname, tvphone, tvaddress, tvpricetotal, tvdiscount, tvtotalPay, tvIdorder, tvDateCreat, tvtotalMoney;
    ListView listViewOrderItem;
    GlobalApplication globalApplication;
    Order orderdetail;
    int status, role;
    ArrayList<OrderItemDetail> listoItemDetails;
    AdapterOrderItem adapterOrderItem;
    double totalPay = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        init();

        globalApplication = (GlobalApplication) getApplicationContext();

        Intent intent = getIntent();
        role = intent.getIntExtra("role", 1);
        status = intent.getIntExtra("status", 1);
        if (role == 1) {
            btnCancel_Oder.setVisibility(View.VISIBLE);
            btnProcess_OrderDeatil.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "?????", Toast.LENGTH_SHORT).show();
            OrderCustomer order1 = globalApplication.orderCustomer;

            setdataview(order1.getPhoneNumber(), order1.getAddress(), order1.getName(), order1.getOderId(), order1.getTotalPrice(), order1.getDateCreated(), 0);
            totalPay = order1.getTotalPrice();
            Log.d("3333", "setdataview: mua " + totalPay);

        } else {
            btnCancel_Oder.setVisibility(View.GONE);
            btnProcess_OrderDeatil.setVisibility(View.VISIBLE);
            Order order1 = globalApplication.order;

            setdataview(order1.getPhone_number(), order1.getAddress(), order1.getName(), order1.getOderId(), order1.getTotalPrice(), order1.getDate_created(), 0);
            totalPay = order1.getTotalPrice();
            Log.d("3333", "setdataview: b??n " + totalPay);
        }
        Log.d("3333", "setdataview: b??n " + totalPay);

        if (status + 1 > 2) {
            btnProcess_OrderDeatil.setVisibility(View.GONE);
        }
        try {
            listener();
        } catch (Exception e) {
            e.printStackTrace();
        }


        getOrderItemDetail(globalApplication.orderID);


    }


    private void init() {
        img_back_OrderDetail = (ImageView) findViewById(R.id.img_back_OrderDetail);
        btnProcess_OrderDeatil = (Button) findViewById(R.id.btnProcess_OrderDeatil);
        tvname = (TextView) findViewById(R.id.tv_nameReceiver_OrderDetail);
        tvphone = (TextView) findViewById(R.id.tv_phoneReceiver_OrderDetail);
        tvaddress = (TextView) findViewById(R.id.tv_addressReceiver_OrderDetail);
        tvpricetotal = (TextView) findViewById(R.id.tv_Price_ItemOrder);
        tvdiscount = (TextView) findViewById(R.id.tv_discountMount_OrderDetail);
        tvtotalPay = (TextView) findViewById(R.id.tv_totalMoneyPay_OrderDetail);
        tvtotalMoney = (TextView) findViewById(R.id.tv_totalMoneur_OrderDetail);
        tvIdorder = (TextView) findViewById(R.id.tv_IDorder_OrderDetail);
        tvDateCreat = (TextView) findViewById(R.id.tv_dateCreat_OrderDetail);
        listViewOrderItem = (ListView) findViewById(R.id.listviewOrderDetail);
        listoItemDetails = new ArrayList<>();
        adapterOrderItem = new AdapterOrderItem(this, R.id.listviewOrderDetail, listoItemDetails);
        listViewOrderItem.setAdapter(adapterOrderItem);
        btnCancel_Oder = (Button) findViewById(R.id.btnCancel_OrderDeatil);

    }

    private void setdataview(String phone, String address, String name, int orderID, double TotalPay, String dateCreate, double discount) {
        tvname.setText(name);
        tvphone.setText(phone);
        tvaddress.setText(address);
        tvDateCreat.setText(OtherUltil.convertTimeFromDB(dateCreate));
        tvIdorder.setText(orderID + "");
        tvtotalPay.setText(OtherUltil.fomattien.format(TotalPay) + "??");

    }

    private void listener() throws NullPointerException, Exception {

        img_back_OrderDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnProcess_OrderDeatil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String t = "";
                switch (status + 1) {
                    case 1:
                        t = "X??c nh???n ????n h??ng: sau khi x??c nh???n ????n h??ng s??? chuy???n sang tr???ng th??i \"??ang giao\"" + "\n" + "N???u b???n mu???n h???y ????n h??ng n??y nh???p \"OK\" sau ???? ch???n n??t H???y";
                        break;
                    case 2:
                        t = "X??c nh???n giao h??ng: sau khi x??c nh???n ????n h??ng s??? chuy???n sang tr???ng th??i \"???? giao\"" + "\n" + "N???u b???n mu???n h???y ????n h??ng n??y nh???p \"OK\" sau ???? ch???n n??t H???y";
                        break;
                    default:
                        btnProcess_OrderDeatil.setVisibility(View.GONE);
                        break;


                }

                final EditText edtText = new EditText(getBaseContext());
                edtText.setWidth(100);
                LinearLayout linearLayout = new LinearLayout(OrderDetail.this);
                linearLayout.setGravity(Gravity.CENTER);

                linearLayout.addView(edtText);
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(OrderDetail.this);
                alertDialog.setTitle("X??? l?? ????n h??ng");
                alertDialog.setMessage(t);
                alertDialog.setView(linearLayout);
                alertDialog.setCancelable(false);
                alertDialog.setNeutralButton("H???y ????n h??ng", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (edtText.getText().toString().trim().equalsIgnoreCase("huy")) {

                            OrderUltils.updateOrderStatus(getApplicationContext(), globalApplication.orderID, 4);
                            Intent intent = new Intent();
                            setResult(RESULT_OK);
                            finish();

                        }
                    }
                });
                alertDialog.setPositiveButton("X??c nh???n", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if (edtText.getText().toString().trim().equalsIgnoreCase("ok")) {
                            OrderUltils.updateOrderStatus(getApplicationContext(), globalApplication.orderID, status + 2);

                            Intent intent = new Intent();
                            setResult(RESULT_OK);
                            finish();


                        } else {

                            Toasty.normal(getApplicationContext(), "Nh???p ok r???i b???m X??c nh???n ho???c nh???p huy r???i b???m h???y ????? h???y").show();
                        }

                    }
                });


                alertDialog.show();


            }
        });
        listViewOrderItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        btnCancel_Oder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText edtText = new EditText(getBaseContext());

                LinearLayout linearLayout = new LinearLayout(OrderDetail.this);
                linearLayout.setGravity(Gravity.CENTER);

                linearLayout.addView(edtText);
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(OrderDetail.this);
                alertDialog.setTitle("X??c nh???n h???y ????n h??ng");
                alertDialog.setMessage("Nh???p \"huy\" r???i nh???n X??c nh???n ????? h???y ????n h??ng");
                alertDialog.setView(linearLayout);
                alertDialog.setCancelable(false);

                alertDialog.setPositiveButton("X??c nh???n", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if (edtText.getText().toString().trim().equalsIgnoreCase("huy")) {
                            OrderUltils.updateOrderStatus(getApplicationContext(), globalApplication.orderID, 4);

                            Intent intent = new Intent();
                            setResult(RESULT_OK);
                            finish();


                        } else {

                            Toasty.normal(getApplicationContext(), "Nh???p \"huy\" r???i nh???n X??c nh???n ????? h???y ????n h??ng").show();
                        }

                    }
                });


                alertDialog.show();

            }
        });
    }

    public void getOrderItemDetail(int orderID) {
        DataClient dataClient = APIUltils.getData();
        Call<ArrayList<OrderItemDetail>> arrayListCall = dataClient.getOrderItemDetail(orderID);
        arrayListCall.enqueue(new Callback<ArrayList<OrderItemDetail>>() {
            @Override
            public void onResponse(Call<ArrayList<OrderItemDetail>> call, Response<ArrayList<OrderItemDetail>> response) {
                Log.d("22222", "onResponse: " + response.body().size());

                if (response.body().size() > 0) {
                    Log.d("22222", "onResponse: " + response.body().get(0).getImage());
                    listoItemDetails = response.body();
                    adapterOrderItem = new AdapterOrderItem(getApplicationContext(), R.id.listviewOrderDetail, listoItemDetails);
                    adapterOrderItem.notifyDataSetChanged();

                    listViewOrderItem.setAdapter(adapterOrderItem);


                    double totalPrice = 0;
                    for (int i = 0; i < listoItemDetails.size(); i++) {
                        totalPrice += listoItemDetails.get(i).getPrice() * listoItemDetails.get(i).getAmount();
                    }
                    tvtotalMoney.setText(OtherUltil.fomattien.format(totalPrice) + "??");
                    tvdiscount.setText("-" + OtherUltil.fomattien.format(totalPrice - totalPay) + "??");


                }
            }

            @Override
            public void onFailure(Call<ArrayList<OrderItemDetail>> call, Throwable t) {
                Log.d("22222", "L??i: " + t.toString());

            }
        });
    }

}
