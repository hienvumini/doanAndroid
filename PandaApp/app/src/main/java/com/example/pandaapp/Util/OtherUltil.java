package com.example.pandaapp.Util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OtherUltil {
    public static DecimalFormat fomattien = new DecimalFormat("###,###.###");

    public static String convertTime(String datetime) {
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

    ;
}
