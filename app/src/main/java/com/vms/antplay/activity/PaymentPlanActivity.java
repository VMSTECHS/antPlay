package com.vms.antplay.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.billingclient.api.AcknowledgePurchaseParams;
import com.android.billingclient.api.AcknowledgePurchaseResponseListener;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ConsumeParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.ProductDetails;
import com.android.billingclient.api.ProductDetailsResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.QueryProductDetailsParams;
import com.vms.antplay.R;
import com.vms.antplay.api.APIClient;
import com.vms.antplay.adapter.PaymentPlanAdapter;
import com.vms.antplay.interfaces.PaymentInitiationInterface;
import com.vms.antplay.api.RetrofitAPI;
import com.vms.antplay.model.responseModal.BillingPlan;
import com.vms.antplay.utils.AppUtils;
import com.vms.antplay.utils.Const;

import com.vms.antplay.model.responseModal.GetBillingPlanResponseModal;
import com.vms.antplay.utils.SharedPreferenceUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PaymentPlanActivity extends AppCompatActivity implements PaymentInitiationInterface, PurchasesUpdatedListener {
    List<BillingPlan> paymentPlansList = new ArrayList<>();
    RecyclerView recyclerPaymentPlan;
    ImageView imgBack;
    private ProgressBar loadingPB;

    BillingClient billingClient;
    BillingFlowParams billingFlowParams;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_plan);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccentDark_light, this.getTheme()));
        recyclerPaymentPlan = findViewById(R.id.rvPaymentPlans);
        imgBack = findViewById(R.id.imgBackPayment);
        loadingPB = (ProgressBar) findViewById(R.id.loading_getPaymentPlanHistory);

        callGetPlanAPI();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //Step 2 Started
        PurchasesUpdatedListener purchasesUpdatedListener = new PurchasesUpdatedListener() {
            @Override
            public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> list) {
                // To be implemented later

            }
        };

        billingClient = BillingClient.newBuilder(PaymentPlanActivity.this)
                .setListener(purchasesUpdatedListener)
                .enablePendingPurchases()
                .build();

        startGoogleConnection();
        //Step 2 Ended
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

                } else {
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


    @Override
    public void onPaymentInitiated(Double hours, int amount) {
        // Perform click to purchase here
        // Pass Billing flow param to billing client
        if (billingFlowParams!=null) {
            billingClient.launchBillingFlow(
                    PaymentPlanActivity.this,
                    billingFlowParams);
        }else {
            Toast.makeText(PaymentPlanActivity.this,"Something went wrong please try againg after sometime",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> list) {
        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && list != null) {
            for (Purchase purchase : list) {
                if (purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED && !purchase.isAcknowledged()) {
                    // Call Api to verify your purchase from own server.
                    // In case of OneTime purchase product you need to consume that item as well
                    // after getting success response from the server.
                    Log.d("PAYMENT", "Purchased, Verify it on server");
                    verifyPurchases(purchase);

                }else if(purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED && !purchase.isAutoRenewing()){
                    Log.d("PAYMENT", "Purchased, Auto renewable");
                }else if(purchase.getPurchaseState() == Purchase.PurchaseState.PENDING){
                    Log.d("PAYMENT", "Purchase pending");
                }else {
                    Log.d("PAYMENT", "Some error occur");
                }
            }

        }else {
            Log.d("PAYMENT", "Error");
        }
    }

    private void verifyPurchases(Purchase purchase) {
        /*****
         * call API to Verify this purchase from the own server
         * and on success consume the product in case of OneTime payment
         * ****/
        ConsumeParams consumeParams = ConsumeParams.newBuilder()
                .setPurchaseToken(purchase.getPurchaseToken())
                .build();
        billingClient.consumeAsync(consumeParams, new ConsumeResponseListener() {
            @Override
            public void onConsumeResponse(@NonNull BillingResult billingResult, @NonNull String s) {
                if(billingResult.getResponseCode()==BillingClient.BillingResponseCode.OK){
                 Log.d("PAYMENT", "Product Consumed");
                }
            }
        });

        /*****
         * Or Acknowledge your purchase
         * ****/
    /*    AcknowledgePurchaseParams acknowledgePurchaseParams = AcknowledgePurchaseParams.newBuilder()
                .setPurchaseToken(purchase.getPurchaseToken())
                .build();

        billingClient.acknowledgePurchase(acknowledgePurchaseParams, new AcknowledgePurchaseResponseListener() {
            @Override
            public void onAcknowledgePurchaseResponse(@NonNull BillingResult billingResult) {
                if (billingResult.getResponseCode()==BillingClient.BillingResponseCode.OK){
                    Log.d("PAYMENT", "Purchase acknowledged");
                }
            }
        });*/
    }

    /****
     * Google Play Purchase
     * Integration Started
     ****/

   /* @Override
    public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> purchases) {
        // Google Play Purchase Update
        // To be implemented in a later section.

    }*/

    //Step 3
    private void startGoogleConnection() {
        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
                Log.d("PAYMENT", "Server disconnected");
                startGoogleConnection();

            }

            @Override
            public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
                // Check if the result is OK
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    // The BillingClient is ready. You can query purchases here.
                    getProductDetails();
                }

                Log.d("PAYMENT", "Setup finished");
            }
        });
    }

    private void getProductDetails() {
        List<QueryProductDetailsParams.Product> productList = new ArrayList<>();
        productList.add(
                QueryProductDetailsParams.Product.newBuilder()
                        .setProductId(Const.BASIC_PLAN_1) //Const.BASIC_SKU,  dummy_product_1
                        .setProductType(BillingClient.ProductType.SUBS)
                        .build()
        );
        QueryProductDetailsParams queryProductDetailsParams =
                QueryProductDetailsParams.newBuilder()
                        .setProductList(productList)
                        .build();

        billingClient.queryProductDetailsAsync(queryProductDetailsParams,
                new ProductDetailsResponseListener() {
                    @Override
                    public void onProductDetailsResponse(@NonNull BillingResult billingResult,
                                                         @NonNull List<ProductDetails> productDetailsList) {
                        // check billingResult
                        // process returned productDetailsList

                        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && productDetailsList != null && productDetailsList.size()!=0) {


                            List<BillingFlowParams.ProductDetailsParams> productDetailsParamsList = new ArrayList<>();
                            // Use a forLoop to add all the element of the List
                            productDetailsParamsList.add(BillingFlowParams.ProductDetailsParams
                                    .newBuilder()
                                    .setProductDetails(productDetailsList.get(0))
                                    .build());

                            // Pass ProductDetailsParamList to the Billing flow param
                            billingFlowParams = BillingFlowParams.newBuilder()
                                    .setProductDetailsParamsList(productDetailsParamsList)
                                    .build();
                        }
                       /* // Pass Billing flow param to billing client
                        billingClient.launchBillingFlow(
                                PaymentPlanActivity.this,
                                billingFlowParams);*/
                    }
                });
    }


}