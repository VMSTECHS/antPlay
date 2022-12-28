package com.vms.antplay.interfaces;

import android.util.Log;
import android.webkit.JavascriptInterface;

public class PaymentInterface {
    @JavascriptInterface
    public  void success(String data){
        Log.e("hello success--","Success");
    }

    @JavascriptInterface
    public  void error(String data){
        Log.e("hello failure--","failure");
    }
}
