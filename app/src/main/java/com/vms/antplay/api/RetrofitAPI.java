package com.vms.antplay.api;


import com.vms.antplay.model.requestModal.ChangePasswordRequestModal;
import com.vms.antplay.model.requestModal.LoginRequestModal;
import com.vms.antplay.model.requestModal.StartPaymentRequestModal;
import com.vms.antplay.model.requestModal.UserUpdateRequestModal;
import com.vms.antplay.model.responseModal.BillingPlansResponse;
import com.vms.antplay.model.responseModal.ChangePasswordResponseModal;
import com.vms.antplay.model.responseModal.GetBillingPlanResponseModal;
import com.vms.antplay.model.responseModal.GetPaymentHistoryResponseModal;
import com.vms.antplay.model.responseModal.GetVMResponseModal;
import com.vms.antplay.model.responseModal.LoginResponseModel;
import com.vms.antplay.model.requestModal.RegisterRequestModal;
import com.vms.antplay.model.responseModal.PaymentHistory_modal;
import com.vms.antplay.model.responseModal.RegisterResponseModal;
import com.vms.antplay.model.responseModal.StartPaymentResponseModal;
import com.vms.antplay.model.responseModal.UserUpdateResponseModal;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface RetrofitAPI {

    @POST("register/")
    Call<RegisterResponseModal> registerUser(@Body RegisterRequestModal dataModal);

    @POST("login/")
    Call<LoginResponseModel> loginUser(@Body LoginRequestModal dataModal);

    @GET("getvm")
    Call<ArrayList<GetVMResponseModal>> getVM(@Header("Authorization") String token);

    @GET("getbillingplan")
    Call<GetBillingPlanResponseModal> getBillingPlan(@Header("Authorization") String Token);

    @GET("getpaymenthistory")
    Call<PaymentHistory_modal> getPaymentHistory(@Header("Authorization") String Token);

    @PUT("change_password")
    Call<ChangePasswordResponseModal> changePassword(@Body ChangePasswordRequestModal changePasswordRequestModal);

    @POST("start_payment/")
    Call<StartPaymentResponseModal> startPayment(@Body StartPaymentRequestModal startPaymentRequestModal);

    @PUT("userupdate/")
    Call<UserUpdateResponseModal> userUpdate(@Body UserUpdateRequestModal userUpdateRequestModal);

//    @GET("getbillingplan")
//    Call<BillingPlansResponse> getBillingPlans(@Header("Token") String Token);


}
