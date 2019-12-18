package com.example.pandaapp.Util;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.DatePicker;

import androidx.annotation.NonNull;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class OtherUltil {

    public static DecimalFormat fomattien = new DecimalFormat("###,###.###");

    public static String convertTimeFromDB(String datetime) {
        SimpleDateFormat sdf = null;
        Date d = new Date();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            d = sdf1.parse(datetime);
        } catch (ParseException ex) {

        }
        String strDateFormat = "dd/MM/yyyy HH:mm:ss";
        sdf = new SimpleDateFormat(strDateFormat);
        return sdf.format(d);
    }

    public static  String convertDatetoMysql(String date){

        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
        Date inputDate = null;
        try {
            inputDate = fmt.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (NullPointerException n){


        }


        fmt = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = fmt.format(inputDate);
        return dateString;
    }
    public static  String convertDateFromMysql(String date){

        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        Date inputDate = null;
        try {
            inputDate = fmt.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        fmt = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = fmt.format(inputDate);
        return dateString;
    }
    public static  String convertDateToMysql(Date date){

        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

        String dateString = fmt.format(date);
        return dateString;
    }
    public static ArrayList<Date> getDates(String dateString1, String dateString2) {
        ArrayList<Date> dates = new ArrayList<Date>();
        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");

        Date date1 = null;
        Date date2 = null;

        try {
            date1 = df1.parse(dateString1);
            date2 = df1.parse(dateString2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);


        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        while (!cal1.after(cal2)) {
            dates.add(cal1.getTime());
            cal1.add(Calendar.DATE, 1);
        }
        return dates;
    }


}
