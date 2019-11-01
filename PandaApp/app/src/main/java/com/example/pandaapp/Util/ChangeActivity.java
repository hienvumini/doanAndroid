package com.example.pandaapp.Util;

import android.content.Context;
import android.content.Intent;

public class ChangeActivity {

   public static void toActivity(Context context, Class aClass){
        Intent intent=new Intent(context,aClass);
        context.startActivity(intent);

    }
}
