package com.example.pandaapp.Util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        }


        fmt = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = fmt.format(inputDate);
        return dateString;
    }
}
