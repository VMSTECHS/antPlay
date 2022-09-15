package com.vms.antplay.api;


import com.vms.antplay.model.requestModal.LoginRequestModal;
import com.vms.antplay.model.responseModal.GetVMResponseModal;
import com.vms.antplay.model.responseModal.LoginResponseModel;
import com.vms.antplay.model.requestModal.RegisterRequestModal;
import com.vms.antplay.model.responseModal.RegisterResponseModal;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface RetrofitAPI {

    @POST("register/")
    Call<RegisterResponseModal> registerUser(@Body RegisterRequestModal dataModal);

    @POST("login/")
    Call<LoginResponseModel> loginUser(@Body LoginRequestModal dataModal);

    @GET("getvm")
    Call<GetVMResponseModal> getVM(@Header("Token") String token);
}
