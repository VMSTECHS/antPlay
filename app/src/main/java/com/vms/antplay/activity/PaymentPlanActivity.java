package com.vms.antplay.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.vms.antplay.R;
import com.vms.antplay.adapter.Computer_available_Adapter;
import com.vms.antplay.adapter.PaymentPlanAdapter;
import com.vms.antplay.api.APIClient;
import com.vms.antplay.api.RetrofitAPI;
import com.vms.antplay.model.Computer_availableModal;
import com.vms.antplay.model.PaymentPlansModel;
import com.vms.antplay.model.responseModal.GetBillingPlanResponseModal;
import com.vms.antplay.model.responseModal.GetVMResponseModal;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        callGetPlanAPI();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void callGetPlanAPI() {
        RetrofitAPI retrofitAPI = APIClient.getRetrofitInstance().create(RetrofitAPI.class);
        GetBillingPlanResponseModal getVMResponseModal = new GetBillingPlanResponseModal();
        Call<GetBillingPlanResponseModal> call = retrofitAPI.getBillingPlan("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNjYzMzA1MTc4LCJpYXQiOjE2NjMyMTg3NzgsImp0aSI6ImE4ZmU1YTIwZTg4NzRlZjc5MjdmNzE3Nzg1ZjljYTIwIiwidXNlcl9pZCI6MjY3fQ.7Aunx1A4d33cywl8Fxlk_YhKM58uu1kBfUhHwcuuDWk");
        call.enqueue(new Callback<GetBillingPlanResponseModal>() {
            @Override
            public void onResponse(Call<GetBillingPlanResponseModal> call, Response<GetBillingPlanResponseModal> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.e("Hello Get VM", "" + response.body().getPlanName());


                }
            }

            @Override
            public void onFailure(Call<GetBillingPlanResponseModal> call, Throwable t) {
                Log.e("Hello Get VM", "Failure");
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