package com.vms.antplay.api;


import com.vms.antplay.model.responseModal.LoginResponseModel;
import com.vms.antplay.model.requestModal.RegisterRequestModal;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitAPI {

    @POST("register/")
    Call<LoginResponseModel> registerUser(@Body RegisterRequestModal dataModal);

//    @POST("game.php")
//    Call<GameSuccessResponse> gameOver(@Body GameSuccessRequestModel dataModal);
}
