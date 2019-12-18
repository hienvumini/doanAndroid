package com.example.pandaapp.Util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class ChangeActivity {

    public static void toActivity(Activity context, Class aClass) {
        try {
            Intent intent = new Intent(context, aClass);
            context.startActivity(intent);
        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }

    public static void toActivity(Activity context, Class aClass, String key, int value) {

        try {
            Intent intent = new Intent(context, aClass);
            intent.putExtra(key, value);
            context.startActivity(intent);
        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }
}
