package com.vms.antplay.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.vms.antplay.R;
import com.vms.antplay.adapter.PaymentHistory_Adapter;
import com.vms.antplay.model.PaymentHistory_modal;

import java.util.ArrayList;

public class PaymentHistory extends AppCompatActivity {

    RecyclerView recyclerView;
    private  ArrayList<PaymentHistory_modal> paymentHistory_modals;
    LinearLayout linear_back;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_history);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccentDark_light, this.getTheme()));

        recyclerView=(RecyclerView) findViewById(R.id.recyclerView_payment);
        linear_back= (LinearLayout) findViewById(R.id.back_linear_payment);

        linear_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        paymentHistory_modals = new ArrayList<>();

        paymentHistory_modals.add(new PaymentHistory_modal("Basic","#21321","233.00","02-03-1992","Completed"));
        paymentHistory_modals.add(new PaymentHistory_modal("Basic","#21321","233.00","02-03-1992","Completed"));
        paymentHistory_modals.add(new PaymentHistory_modal("Basic","#21321","233.00","02-03-1992","Completed"));
        paymentHistory_modals.add(new PaymentHistory_modal("Basic","#21321","233.00","02-03-1992","Completed"));

        PaymentHistory_Adapter adapter = new PaymentHistory_Adapter(this, paymentHistory_modals);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

    }
}