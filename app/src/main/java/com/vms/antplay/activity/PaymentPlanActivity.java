package com.vms.antplay.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.vms.antplay.R;
import com.vms.antplay.adapter.PaymentPlanAdapter;
import com.vms.antplay.model.PaymentPlansModel;

import java.util.ArrayList;

public class PaymentPlanActivity extends AppCompatActivity {
    ArrayList<PaymentPlansModel> paymentPlansList = new ArrayList<>();
    RecyclerView recyclerPaymentPlan;
    ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_plan);
        recyclerPaymentPlan = findViewById(R.id.rvPaymentPlans);
        imgBack = findViewById(R.id.imgBackPayment);


        setPlanList();
        // setPaymentPlanAdapter();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void setPlanList() {
        paymentPlansList.add(new PaymentPlansModel("Light", 1, 2, 1, "Geforce GTX 1660 6GB GPU"));
        paymentPlansList.add(new PaymentPlansModel("Basic", 899, 30, 15, "Geforce GTX 1660 6GB GPU"));
        paymentPlansList.add(new PaymentPlansModel("Premium", 1699, 100, 30, "Geforce GTX 1660 6GB GPU"));
        paymentPlansList.add(new PaymentPlansModel("Ultima", 3199, 250, 30, "Geforce GTX 1660 6GB GPU"));
        setPaymentPlanAdapter();
    }

    private void setPaymentPlanAdapter() {
        PaymentPlanAdapter planAdapter = new PaymentPlanAdapter(PaymentPlanActivity.this, paymentPlansList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerPaymentPlan.setLayoutManager(layoutManager);
        recyclerPaymentPlan.setAdapter(planAdapter);
    }
}