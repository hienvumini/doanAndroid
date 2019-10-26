package com.example.pandaapp.Util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.ActivityCompat;

import com.example.pandaapp.CartActivity;

public class ChangeActivity {

   public static void toActivity(Context context, Class aClass){
        Intent intent=new Intent(context,aClass);
        context.startActivity(intent);

    }
}
