package com.vms.antplay.activity;


import static com.android.billingclient.api.BillingClient.SkuType.SUBS;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
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
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesResult;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;
import com.vms.antplay.R;
import com.vms.antplay.api.APIClient;
import com.vms.antplay.adapter.PaymentPlanAdapter;
import com.vms.antplay.interfaces.PaymentInitiationInterface;
import com.vms.antplay.api.RetrofitAPI;
import com.vms.antplay.model.responseModal.BillingPlan;
import com.vms.antplay.utils.AppUtils;
import com.vms.antplay.utils.Const;

import com.vms.antplay.model.responseModal.GetBillingPlanResponseModal;
import com.vms.antplay.utils.Security;
import com.vms.antplay.utils.SharedPreferenceUtils;

import java.io.IOException;
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
    //-----------Billing----------------
    private BillingClient billingClient;
    public static final String PREF_FILE = "MyPref";
    public static final String SUBSCRIBE_KEY = "subscribe";
    public static final String ITEM_SKU_SUBSCRIBE = Const.BASIC_SKU;


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

        //---------------------Billing0------
        billingClient = BillingClient.newBuilder(this)
                .enablePendingPurchases().setListener(this).build();

        billingClient = BillingClient.newBuilder(this).enablePendingPurchases().setListener(this).build();
        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(BillingResult billingResult) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    Purchase.PurchasesResult queryPurchase = billingClient.queryPurchases(SUBS);
                    List<Purchase> queryPurchases = queryPurchase.getPurchasesList();
                    if (queryPurchases != null && queryPurchases.size() > 0) {
                        handlePurchases(queryPurchases);
                    }
                    //if no item in purchase list means subscription is not subscribed
                    //Or subscription is cancelled and not renewed for next month
                    // so update pref in both cases
                    // so next time on app launch our premium content will be locked again
                    else {
                        saveSubscribeValueToPref(false);
                    }
                }
            }

            @Override
            public void onBillingServiceDisconnected() {
                Toast.makeText(getApplicationContext(), "Service Disconnected", Toast.LENGTH_SHORT).show();
            }
        });

        //item subscribed
        if (getSubscribeValueFromPref()) {
//            subscribe.setVisibility(View.GONE);
//            premiumContent.setVisibility(View.VISIBLE);
//            subscriptionStatus.setText("Subscription Status : Subscribed");
            Toast.makeText(this, "Subscription Status : Subscribed", Toast.LENGTH_SHORT).show();
        }
        //item not subscribed
        else {
//            premiumContent.setVisibility(View.GONE);
//            subscribe.setVisibility(View.VISIBLE);
//            subscriptionStatus.setText("Subscription Status : Not Subscribed");
            Toast.makeText(this, "Subscription Status : Not Subscribed", Toast.LENGTH_SHORT).show();
        }

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
        Log.e("hello hours", hours + "--" + amount);
//        if (billingFlowParams!=null) {
//            billingClient.launchBillingFlow(
//                    PaymentPlanActivity.this,
//                    billingFlowParams);
//        }else {
//            Toast.makeText(PaymentPlanActivity.this,"Something went wrong please try again after sometime",Toast.LENGTH_LONG).show();
//        }

        /****
         * Google Play Purchase Subscription
         * Integration Started -- Rakesh Jha
         ****/

        if (billingClient.isReady()) {
            initiatePurchase();
        }
        //else reconnect service
        else {
            billingClient = BillingClient.newBuilder(this).enablePendingPurchases().setListener(this).build();
            billingClient.startConnection(new BillingClientStateListener() {
                @Override
                public void onBillingSetupFinished(BillingResult billingResult) {
                    if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                        initiatePurchase();
                    } else {
                        Toast.makeText(getApplicationContext(), "Error " + billingResult.getDebugMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onBillingServiceDisconnected() {
                    Toast.makeText(getApplicationContext(), "Service Disconnected ", Toast.LENGTH_SHORT).show();
                }
            });
        }


    }

    private void initiatePurchase() {
        List<String> skuList = new ArrayList<>();
        skuList.add("test_s1");
        SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
        params.setSkusList(skuList).setType(SUBS);
        BillingResult billingResult = billingClient.isFeatureSupported(BillingClient.FeatureType.SUBSCRIPTIONS);
        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
            billingClient.querySkuDetailsAsync(params.build(),
                    new SkuDetailsResponseListener() {
                        @Override
                        public void onSkuDetailsResponse(BillingResult billingResult,
                                                         List<SkuDetails> skuDetailsList) {
                            if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                                if (skuDetailsList != null && skuDetailsList.size() > 0) {
                                    BillingFlowParams flowParams = BillingFlowParams.newBuilder()
                                            .setSkuDetails(skuDetailsList.get(0))
                                            .build();
                                    billingClient.launchBillingFlow(PaymentPlanActivity.this, flowParams);
                                } else {
                                    //try to add subscription item "sub_example" in google play console
                                    Toast.makeText(getApplicationContext(), "Item not Found", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(),
                                        " Error " + billingResult.getDebugMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(getApplicationContext(),
                    "Sorry Subscription not Supported. Please Update Play Store", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> purchases) {
        Toast.makeText(getApplicationContext(), "onPurchaseUpdate ", Toast.LENGTH_SHORT).show();
        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && purchases != null) {
            Toast.makeText(getApplicationContext(), "Handle Purchased ", Toast.LENGTH_SHORT).show();
            handlePurchases(purchases);
        }
        //if item already subscribed then check and reflect changes
        else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED) {
            Purchase.PurchasesResult queryAlreadyPurchasesResult = billingClient.queryPurchases(SUBS);
            List<Purchase> alreadyPurchases = queryAlreadyPurchasesResult.getPurchasesList();
            if (alreadyPurchases != null) {
                handlePurchases(alreadyPurchases);
            }
        }
        //if Purchase canceled
        else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.USER_CANCELED) {
            Toast.makeText(getApplicationContext(), "Purchase Canceled", Toast.LENGTH_SHORT).show();
        }
        // Handle any other error msgs
        else {
            Toast.makeText(getApplicationContext(), "Error " + billingResult.getDebugMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void handlePurchases(List<Purchase> purchases) {
        for (Purchase purchase : purchases) {
            //if item is purchased
            if (ITEM_SKU_SUBSCRIBE.equals(purchase.getSkus()) && purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED) {
                if (!verifyValidSignature(purchase.getOriginalJson(), purchase.getSignature())) {
                    // Invalid purchase
                    // show error to user
                    Toast.makeText(getApplicationContext(), "Error : invalid Purchase", Toast.LENGTH_SHORT).show();
                    return;
                }
                // else purchase is valid
                //if item is purchased and not acknowledged
                if (!purchase.isAcknowledged()) {
                    AcknowledgePurchaseParams acknowledgePurchaseParams =
                            AcknowledgePurchaseParams.newBuilder()
                                    .setPurchaseToken(purchase.getPurchaseToken())
                                    .build();
                    billingClient.acknowledgePurchase(acknowledgePurchaseParams, ackPurchase);
                }
                //else item is purchased and also acknowledged
                else {
                    // Grant entitlement to the user on item purchase
                    // restart activity
                    if (!getSubscribeValueFromPref()) {
                        saveSubscribeValueToPref(true);
                        Toast.makeText(getApplicationContext(), "Item Purchased", Toast.LENGTH_SHORT).show();
                        this.recreate();
                    }
                }
            }
            //if purchase is pending
            else if (ITEM_SKU_SUBSCRIBE.equals(purchase.getSkus()) && purchase.getPurchaseState() == Purchase.PurchaseState.PENDING) {
                Toast.makeText(getApplicationContext(),
                        "Purchase is Pending. Please complete Transaction", Toast.LENGTH_SHORT).show();
            }
            //if purchase is unknown mark false
            else if (ITEM_SKU_SUBSCRIBE.equals(purchase.getSkus()) && purchase.getPurchaseState() == Purchase.PurchaseState.UNSPECIFIED_STATE) {
                saveSubscribeValueToPref(false);
                Toast.makeText(getApplicationContext(), "Purchase Status Unknown", Toast.LENGTH_SHORT).show();
            }
        }
    }

    AcknowledgePurchaseResponseListener ackPurchase = new AcknowledgePurchaseResponseListener() {
        @Override
        public void onAcknowledgePurchaseResponse(BillingResult billingResult) {
            if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                //if purchase is acknowledged
                // Grant entitlement to the user. and restart activity
                saveSubscribeValueToPref(true);
                PaymentPlanActivity.this.recreate();
            }
        }
    };

    private boolean verifyValidSignature(String signedData, String signature) {
        try {
            // To get key go to Developer Console > Select your app > Development Tools > Services & APIs.
            String base64Key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjm7sjWPMIHx/i6fchZh+zftM8u3spnkb887DiEsGIh9g1d0ag6jr801i1Njfxf0iCDvNKrUeg9Bt3CSGP89YL7m3ToWtkAosM8lVetjYb9NBGazFmqZn4R2dq5WwSZRef3uMZTykJaakmV9EqBOFJkidupMr8+IHsfyDzMVG/cFnUZQA9z0e+mNi6ZDP56+ZzmBQgrlOkcmRiNBHtt2WFQjvBNBlkZWeq10KMc3iLR8t+pZapbhloVBivPAXeevZWhWq+dfIwRDlY8rjRB27+Cqq6js5aGUbgvo74fuBlC+kED7zz1uT3QdGrIBNMOpFQkMmcwwntEhj1ktq+u+fmwIDAQAB";
            return Security.verifyPurchase(base64Key, signedData, signature);
        } catch (IOException e) {
            return false;
        }
    }

    private SharedPreferences getPreferenceObject() {
        return getApplicationContext().getSharedPreferences(PREF_FILE, 0);
    }

    private SharedPreferences.Editor getPreferenceEditObject() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(PREF_FILE, 0);
        return pref.edit();
    }

    private boolean getSubscribeValueFromPref() {
        return getPreferenceObject().getBoolean(SUBSCRIBE_KEY, false);
    }

    private void saveSubscribeValueToPref(boolean value) {
        getPreferenceEditObject().putBoolean(SUBSCRIBE_KEY, value).commit();
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
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
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
                    // getProductDetails();
                }

                Log.d("PAYMENT", "Setup finished");
            }
        });
    }

//    private void getProductDetails() {
//        List<QueryProductDetailsParams.Product> productList = new ArrayList<>();
//        productList.add(
//                QueryProductDetailsParams.Product.newBuilder()
//                        .setProductId(Const.BASIC_PLAN_1) //Const.BASIC_SKU,  dummy_product_1
//                        .setProductType(SUBS)
//                        .build()
//        );
//        QueryProductDetailsParams queryProductDetailsParams =
//                QueryProductDetailsParams.newBuilder()
//                        .setProductList(productList)
//                        .build();
//
//        billingClient.queryProductDetailsAsync(queryProductDetailsParams,
//                new ProductDetailsResponseListener() {
//                    @Override
//                    public void onProductDetailsResponse(@NonNull BillingResult billingResult,
//                                                         @NonNull List<ProductDetails> productDetailsList) {
//                        // check billingResult
//                        // process returned productDetailsList
//
//                        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && productDetailsList != null && productDetailsList.size()!=0) {
//
//
//                            List<BillingFlowParams.ProductDetailsParams> productDetailsParamsList = new ArrayList<>();
//                            // Use a forLoop to add all the element of the List
//                            productDetailsParamsList.add(BillingFlowParams.ProductDetailsParams
//                                    .newBuilder()
//                                    .setProductDetails(productDetailsList.get(0))
//                                    .build());
//
//                            // Pass ProductDetailsParamList to the Billing flow param
//                            billingFlowParams = BillingFlowParams.newBuilder()
//                                    .setProductDetailsParamsList(productDetailsParamsList)
//                                    .build();
//                        }
//                       /* // Pass Billing flow param to billing client
//                        billingClient.launchBillingFlow(
//                                PaymentPlanActivity.this,
//                                billingFlowParams);*/
//                    }
//                });
//    }


}