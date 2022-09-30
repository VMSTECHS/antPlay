package com.vms.antplay.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.ExternalWalletListener;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;
import com.vms.antplay.R;
import com.vms.antplay.api.APIClient;
import com.vms.antplay.adapter.PaymentPlanAdapter;
import com.vms.antplay.interfaces.PaymentInitiationInterface;
import com.vms.antplay.api.RetrofitAPI;
import com.vms.antplay.model.PaymentPlansModel;
import com.vms.antplay.model.responseModal.BillingPlan;
import com.vms.antplay.utils.AppUtils;
import com.vms.antplay.utils.Const;

import org.json.JSONObject;

import com.vms.antplay.model.responseModal.GetBillingPlanResponseModal;
import com.vms.antplay.utils.SharedPreferenceUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PaymentPlanActivity extends AppCompatActivity implements PaymentInitiationInterface, PaymentResultWithDataListener, ExternalWalletListener {
    List<BillingPlan> paymentPlansList = new ArrayList<>();
    RecyclerView recyclerPaymentPlan;
    ImageView imgBack;
    private AlertDialog.Builder alertDialogBuilder;
    private ProgressBar loadingPB;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_plan);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccentDark_light, this.getTheme()));
        recyclerPaymentPlan = findViewById(R.id.rvPaymentPlans);
        imgBack = findViewById(R.id.imgBackPayment);
        loadingPB = (ProgressBar) findViewById(R.id.loading_getPaymentPlanHistory);

       // setPlanList();
        // setPaymentPlanAdapter();
        callGetPlanAPI();

        alertDialogBuilder = new AlertDialog.Builder(PaymentPlanActivity.this);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setTitle("Payment Result");
        alertDialogBuilder.setPositiveButton("Ok", (dialog, which) -> {
            //do nothing
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void callGetPlanAPI() {
        loadingPB.setVisibility(View.VISIBLE);
        String access_token = SharedPreferenceUtils.getString(PaymentPlanActivity.this, Const.ACCESS_TOKEN);
        RetrofitAPI retrofitAPI = APIClient.getRetrofitInstance().create(RetrofitAPI.class);
        Call<GetBillingPlanResponseModal> call = retrofitAPI.getBillingPlan("Bearer " + access_token);
        call.enqueue(new Callback<GetBillingPlanResponseModal>() {
            @Override
            public void onResponse(Call<GetBillingPlanResponseModal> call, Response<GetBillingPlanResponseModal> response) {
                if (response.isSuccessful() && response.body() != null) {
                    loadingPB.setVisibility(View.GONE);
                    paymentPlansList = response.body().getData();
                    PaymentPlanAdapter planAdapter = new PaymentPlanAdapter(PaymentPlanActivity.this, paymentPlansList, PaymentPlanActivity.this);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(PaymentPlanActivity.this);
                    recyclerPaymentPlan.setLayoutManager(layoutManager);
                    recyclerPaymentPlan.setAdapter(planAdapter);

                }
                else {
                    loadingPB.setVisibility(View.GONE);
                    AppUtils.showToast(Const.no_records, PaymentPlanActivity.this);
                }
            }

            @Override
            public void onFailure(Call<GetBillingPlanResponseModal> call, Throwable t) {
                Log.d("BILLING_PLAN", "Failure");
                loadingPB.setVisibility(View.GONE);
                AppUtils.showToast(Const.something_went_wrong, PaymentPlanActivity.this);
            }
        });
    }

    public void startPayment(Double hours, int amount) {
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        int amountInPaise = amount * 100;
        final Activity activity = PaymentPlanActivity.this;

        final Checkout checkout = new Checkout();


        checkout.setKeyID(Const.razorPayTestKeyId);

        // EditText etCustomOptions = findViewById(R.id.et_custom_options);
       /* if (!TextUtils.isEmpty(etCustomOptions.getText().toString())){
            try{
                JSONObject options = new JSONObject(etCustomOptions.getText().toString());
                checkout.open(activity, options);
            }catch (JSONException e){
                Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                        .show();
                e.printStackTrace();
            }
        }else{*/
        try {
            JSONObject options = new JSONObject();
            options.put("name", "Razorpay Corp");
            options.put("description", "Demoing Charges");
            options.put("send_sms_hash", true);
            options.put("allow_rotation", true);
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("currency", "INR");
            options.put("amount", String.valueOf(amountInPaise));

            JSONObject preFill = new JSONObject();
            preFill.put("email", "test@razorpay.com");
            preFill.put("contact", "9876543210");

            options.put("prefill", preFill);

            checkout.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }


    @Override
    public void onExternalWalletSelected(String s, PaymentData paymentData) {
        try {
            alertDialogBuilder.setMessage("External Wallet Selected:\nPayment Data: " + paymentData.getData());
            alertDialogBuilder.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPaymentSuccess(String razorpayPaymentID, PaymentData paymentData) {
        try {
            alertDialogBuilder.setMessage("Payment Successful :\nPayment ID: " + razorpayPaymentID + "\nPayment Data: " + paymentData.getData());
            alertDialogBuilder.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        try {
            alertDialogBuilder.setMessage("Payment Failed:\nPayment Data: " + paymentData.getData());
            alertDialogBuilder.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPaymentInitiated(Double hours, int amount) {
        startPayment(Double.valueOf(String.valueOf(hours)), amount);
       /* String url = "https://rzp.io/i/r3CTRh6CG";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);*/
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}