package com.vms.antplay.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.vms.antplay.R;

public class AppUtils {

    public static void showToast(String msg, Context ctx) {
        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
    }
}
