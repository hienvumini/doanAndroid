package com.example.pandaapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pandaapp.Models.Revenue;
import com.example.pandaapp.R;
import com.example.pandaapp.Retrofit2.APIUltils;
import com.example.pandaapp.Retrofit2.DataClient;
import com.example.pandaapp.Util.GlobalApplication;
import com.example.pandaapp.Util.OtherUltil;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RevenueActivity extends AppCompatActivity {
    LinearLayout linearLayoutFrom, linearLayoutTo;
    TextView textViewFrom, textViewTo, textViewdatestart, textViewdateend, textViewTotalRevenue;
    Calendar calendarnow;
    GlobalApplication globalApplication;
    ArrayList<Revenue> listRevenues;
    int idShop;
    CombinedChart combinedChartRevenue;
    static ArrayList<String> xLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revenue);
        init();
        setdatainit();
        listener();

       // getTotalRevenue(textViewFrom.getText().toString(), textViewTo.getText().toString());


        getListRevenue(idShop, textViewFrom.getText().toString(), textViewTo.getText().toString());


    }

    private void DrawChart(ArrayList<Revenue> revenues) {


        combinedChartRevenue.getDescription().setEnabled(false);
        combinedChartRevenue.setBackgroundColor(Color.WHITE);
        combinedChartRevenue.setDrawGridBackground(false);
        combinedChartRevenue.setDrawBarShadow(false);
        combinedChartRevenue.setHighlightFullBarEnabled(false);


        YAxis rightAxis = combinedChartRevenue.getAxisRight();
        rightAxis.setDrawGridLines(true);
        rightAxis.setAxisMinimum(0f);

        YAxis leftAxis = combinedChartRevenue.getAxisLeft();
        leftAxis.setDrawGridLines(true);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setEnabled(false);

        xLabel = new ArrayList<>();
        for (int i = 0; i < revenues.size(); i++) {
            String lable = revenues.get(i).getDate();
            lable = lable.split("-")[2] + "/" + lable.split("-")[1];
            xLabel.add(lable);

        }
        XAxis xAxis = combinedChartRevenue.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);

        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return xLabel.get((int) value % xLabel.size());
            }
        });

        CombinedData data = new CombinedData();
        LineData lineDatas = new LineData();
        lineDatas.addDataSet((ILineDataSet) dataChart(revenues));

        data.setData(lineDatas);

        xAxis.setAxisMaximum(data.getXMax() + 0.25f);

        combinedChartRevenue.setData(data);
        combinedChartRevenue.invalidate();
    }


    private static DataSet dataChart(ArrayList<Revenue> revenues1) {

        LineData d = new LineData();


        ArrayList<Entry> entries = new ArrayList<Entry>();

        for (int index = 0; index < revenues1.size(); index++) {
            entries.add(new Entry(index, (float) revenues1.get(index).getRevenue()));
        }

        LineDataSet set = new LineDataSet(entries, "Doanh thu (đơn vị VND) ");
        set.setColor(Color.BLUE);
        set.setLineWidth(1f);
        set.setCircleColor(Color.BLUE);
        set.setCircleRadius(1f);
        set.setFillColor(Color.BLUE);
        set.setMode(LineDataSet.Mode.LINEAR);
        set.setDrawValues(true);
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.BLUE);

        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        d.addDataSet(set);

        return set;
    }


    public void getListRevenue(int idshop, String from, String to) {

        from = OtherUltil.convertDatetoMysql(from);
        to = OtherUltil.convertDatetoMysql(to);
        Toast.makeText(getApplicationContext(), idshop + "-" + from + "-" + to, Toast.LENGTH_SHORT).show();
        DataClient dataClient = APIUltils.getData();
        Call<ArrayList<Revenue>> arrayListCall = dataClient.getListRevenueShop(idshop, from, to);
        arrayListCall.enqueue(new Callback<ArrayList<Revenue>>() {
            @Override
            public void onResponse(Call<ArrayList<Revenue>> call, Response<ArrayList<Revenue>> response) {
                Log.d("22222AA", "onResponse: " + response.body());
                ArrayList<Revenue> listrevenues = response.body();
                DrawChart(listrevenues);
                double TotalRevenue=0;
                for (int i = 0; i <listrevenues.size() ; i++) {
                    TotalRevenue +=listrevenues.get(i).getRevenue();
                }
                textViewTotalRevenue.setText(OtherUltil.fomattien.format(TotalRevenue) + "đ");
                textViewdatestart.setText(textViewFrom.getText());
                textViewdateend.setText(textViewTo.getText());


            }

            @Override
            public void onFailure(Call<ArrayList<Revenue>> call, Throwable t) {

            }
        });


    }


    private void getTotalRevenue(String from, String to) {


        if (globalApplication.account != null) {

            from = OtherUltil.convertDatetoMysql(from);
            to = OtherUltil.convertDatetoMysql(to);

            DataClient dataClient = APIUltils.getData();
            Call<String> stringCall = dataClient.getTotalRevenueShop(idShop, from + " 00:00:00", to + " 00:00:00");
            stringCall.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {


                    textViewTotalRevenue.setText(response.body() + "đ");

                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.d("2222", "Lỗi : " + t.toString());

                }
            });
        }

    }


    private void init() {
        linearLayoutFrom = (LinearLayout) findViewById(R.id.From_Revenue);
        linearLayoutTo = (LinearLayout) findViewById(R.id.To_Revenue);
        textViewFrom = (TextView) findViewById(R.id.textviewFROM_Revenue);
        textViewTo = (TextView) findViewById(R.id.textviewTO_Revenue);
        textViewdatestart = (TextView) findViewById(R.id.textview_date_start_Revenue);
        textViewdateend = (TextView) findViewById(R.id.textview_date_end_Revenue);
        globalApplication = (GlobalApplication) getApplicationContext();
        textViewTotalRevenue = (TextView) findViewById(R.id.revenue_pay_total_Revenue);
        listRevenues = new ArrayList<>();
        idShop = globalApplication.account.getIdShop();
        listRevenues = new ArrayList<>();
        combinedChartRevenue = (CombinedChart) findViewById(R.id.combinedChart_Revenue);
        combinedChartRevenue.setClickable(false);

    }

    private void setdatainit() {
        calendarnow = Calendar.getInstance(TimeZone.getDefault());

        Date date = calendarnow.getTime();
        int monthnow = calendarnow.get(Calendar.MONTH);
        int datenow = calendarnow.get(Calendar.DAY_OF_MONTH);
        int yearnow = calendarnow.get(Calendar.YEAR);

        textViewFrom.setText(datenow + "/" + monthnow + "/" + yearnow);
        textViewTo.setText(datenow + "/" + (monthnow + 1) + "/" + yearnow);

    }


    private void listener() {
        linearLayoutFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickdate(textViewFrom);
                getListRevenue(idShop, textViewFrom.getText().toString(), textViewTo.getText().toString());

            }
        });
        linearLayoutTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickdate(textViewTo);
                getListRevenue(idShop, textViewFrom.getText().toString(), textViewTo.getText().toString());


            }
        });
        combinedChartRevenue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void pickdate(final TextView textView) {
        final Calendar calendar = Calendar.getInstance();
        int month=calendar.get(Calendar.MONTH)-1;

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

                if (textView == textViewTo) {
                    if (calendar.compareTo(calendarnow) > 0) {

                        textView.setText(simpleDateFormat.format(calendarnow.getTime()));
                    } else {
                        textView.setText(simpleDateFormat.format(calendar.getTime()));

                    }
                    textViewdateend.setText(textViewTo.getText());

                } else {
                    textView.setText(simpleDateFormat.format(calendar.getTime()));

                }

                getTotalRevenue(textViewFrom.getText().toString(), textViewTo.getText().toString());
                getListRevenue(idShop, textViewFrom.getText().toString(), textViewTo.getText().toString());

            }
        }, calendar.get(Calendar.YEAR), (textView==textViewFrom)?calendar.get(Calendar.MONTH)-1:calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();


    }


}
