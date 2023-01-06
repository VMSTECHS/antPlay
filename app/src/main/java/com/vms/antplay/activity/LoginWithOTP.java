package com.vms.antplay.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.vms.antplay.R;
import com.vms.antplay.api.APIClient;
import com.vms.antplay.api.RetrofitAPI;
import com.vms.antplay.model.responseModal.SendOTPResponse;
import com.vms.antplay.utils.AppUtils;
import com.vms.antplay.utils.Const;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginWithOTP extends AppCompatActivity {

    EditText edt_phone;
    Button btn_lestGo;
    boolean isPhoneNumberEntered = false;
    ProgressBar progressBar;
    TextView tv_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login_with_otp);

        edt_phone = findViewById(R.id.et_phone_otp);
        btn_lestGo = findViewById(R.id.btn_letsGo_otp);
        progressBar = findViewById(R.id.progress_sendOTP);
        tv_register = findViewById(R.id.tv_signupHere_loginWithOTP);

        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginWithOTP.this, RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });

        btn_lestGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPhoneNumberEntered = checkPhoneNumberEntered();
                if (isPhoneNumberEntered) {
                    //Call Send OTP API
                    callSendOTP();
                }
            }
        });
    }

    private void callSendOTP() {

        progressBar.setVisibility(View.VISIBLE);
        RetrofitAPI retrofitAPI = APIClient.getRetrofitInstance().create(RetrofitAPI.class);
        Call<SendOTPResponse> call = retrofitAPI.sendOTP(Const.LIVE_BASE_URL+"getuserbyphone/"+edt_phone.getText().toString());
        call.enqueue(new Callback<SendOTPResponse>() {
            @Override
            public void onResponse(Call<SendOTPResponse> call, Response<SendOTPResponse> response) {

                if (response.code() == Const.SUCCESS_CODE_200) {
                    progressBar.setVisibility(View.GONE);
                    Intent intent = new Intent(LoginWithOTP.this, VerifyOTP.class);
                    intent.putExtra("mobile",edt_phone.getText().toString());
                    startActivity(intent);
                } else if (response.code() == Const.ERROR_CODE_404) {
                    progressBar.setVisibility(View.GONE);
                    Log.e("hi 404--", response.message());
                    Log.e("hi 404--", edt_phone.getText().toString());
                    AppUtils.showSnack(getWindow().getDecorView().getRootView(), R.color.black, getString(R.string.enter_registered_mobile), LoginWithOTP.this);
                }
            }

            @Override
            public void onFailure(Call<SendOTPResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.e("hi else--", t.getMessage());
                AppUtils.showSnack(getWindow().getDecorView().getRootView(), R.color.black, t.getMessage(), LoginWithOTP.this);

            }
        });
    }

    private boolean checkPhoneNumberEntered() {
        if (edt_phone.length() == 0) {
            edt_phone.setError(getString(R.string.error_phone));
            return false;
        } else if (!edt_phone.getText().toString().matches(Const.phoneRegex)) {
            edt_phone.setError(getString(R.string.error_invalidPhone));
            return false;
        }
        return true;

    }
}