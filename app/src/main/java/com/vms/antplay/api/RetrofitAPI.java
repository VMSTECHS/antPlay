package com.vms.antplay.api;


import com.vms.antplay.model.requestModal.ChangePasswordRequestModal;
import com.vms.antplay.model.requestModal.ForgotPassRequestModal;
import com.vms.antplay.model.requestModal.LoginRequestModal;
import com.vms.antplay.model.requestModal.ShutDownVMRequestModal;
import com.vms.antplay.model.requestModal.StartPaymentRequestModal;
import com.vms.antplay.model.requestModal.StartVMRequestModal;
import com.vms.antplay.model.requestModal.UpdateVmRequestModal;
import com.vms.antplay.model.requestModal.UserUpdateRequestModal;
import com.vms.antplay.model.responseModal.BillingPlansResponse;
import com.vms.antplay.model.responseModal.ChangePasswordResponseModal;
import com.vms.antplay.model.responseModal.ForgotPassResponseModal;
import com.vms.antplay.model.responseModal.GetBillingPlanResponseModal;
import com.vms.antplay.model.responseModal.GetPaymentHistoryResponseModal;
import com.vms.antplay.model.responseModal.GetVMResponseModal;
import com.vms.antplay.model.responseModal.LoginResponseModel;
import com.vms.antplay.model.requestModal.RegisterRequestModal;
import com.vms.antplay.model.responseModal.PaymentHistory_modal;
import com.vms.antplay.model.responseModal.RegisterResponseModal;
import com.vms.antplay.model.responseModal.SendOTPResponse;
import com.vms.antplay.model.responseModal.ShutDownVMResponseModal;
import com.vms.antplay.model.responseModal.StartPaymentResponseModal;
import com.vms.antplay.model.responseModal.StartVMResponseModal;
import com.vms.antplay.model.responseModal.UpdateVmResponseModal;
import com.vms.antplay.model.responseModal.UserDetailsModal;
import com.vms.antplay.model.responseModal.UserUpdateResponseModal;
import com.vms.antplay.model.responseModal.VerifyOTPResponseModal;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface RetrofitAPI {

    @POST("register/")
    Call<RegisterResponseModal> registerUser(@Body RegisterRequestModal dataModal);

    @POST("login/")
    Call<LoginResponseModel> loginUser(@Body LoginRequestModal dataModal);

    @GET("getvm")
    Call<GetVMResponseModal> getVM(@Header("Authorization") String token);

    @GET("getbillingplan")
    Call<GetBillingPlanResponseModal> getBillingPlan(@Header("Authorization") String Token);

    @GET("getpaymenthistory")
    Call<PaymentHistory_modal> getPaymentHistory(@Header("Authorization") String Token);

    @PUT("change_password")
    Call<ChangePasswordResponseModal> changePassword(@Body ChangePasswordRequestModal changePasswordRequestModal);

    @POST("start_payment/")
    Call<StartPaymentResponseModal> startPayment(@Header("Authorization") String Token,@Body StartPaymentRequestModal startPaymentRequestModal);

    @PUT("userupdate/")
    Call<UserUpdateResponseModal> userUpdate(@Header("Authorization") String Token,@Body UserUpdateRequestModal userUpdateRequestModal);

    @GET("userview")
    Call<UserDetailsModal> getUserDetails(@Header("Authorization") String Token);

    @POST("request-reset-email/")
    Call<ForgotPassResponseModal> forgotPassword(@Body ForgotPassRequestModal forgotPassRequestModal);

    @POST("shutdownvm/")
    Call<ShutDownVMResponseModal> shoutDownVM(@Body ShutDownVMRequestModal shutDownVMRequestModal);

    @POST("startvm/")
    Call<StartVMResponseModal> startVM(@Body StartVMRequestModal startVMRequestModal);

    @POST("verifyOTPView/")
    Call<VerifyOTPResponseModal> verifyOTP(@Query("phone_number") String phone, @Query("otp") String otp);

    @GET
    Call<SendOTPResponse> sendOTP(@Url String url);

    @POST("updatevm/")
    Call<UpdateVmResponseModal> updateVM(@Header("Authorization") String Token,@Body UpdateVmRequestModal updateVmRequestModal);




}
