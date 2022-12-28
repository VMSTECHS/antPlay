package com.vms.antplay.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.vms.antplay.R;
import com.vms.antplay.interfaces.PaymentInterface;

public class Webview_forPayment extends AppCompatActivity {
    String getUrl;

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_for_payment);

        if (getIntent().getStringExtra("sendUrl") != null) {
            getUrl = getIntent().getStringExtra("sendUrl");
            Log.e("get Payment url Name", "" + getUrl);
        } else {
            getUrl = "";
        }
//https://api.antplay.tech/payment_status/?
        WebView mywebview = (WebView) findViewById(R.id.webView);
        mywebview.loadUrl("https://rzp.io/i/eKnhfNrD");

        mywebview.addJavascriptInterface(new PaymentInterface(), "PaymentInterface");


    }
}