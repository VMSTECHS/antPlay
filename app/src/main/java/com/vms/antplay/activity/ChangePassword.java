package com.vms.antplay.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.vms.antplay.R;
import com.vms.antplay.api.APIClient;
import com.vms.antplay.api.RetrofitAPI;
import com.vms.antplay.model.requestModal.ChangePasswordRequestModal;
import com.vms.antplay.model.responseModal.ChangePasswordResponseModal;
import com.vms.antplay.model.responseModal.GetBillingPlanResponseModal;
import com.vms.antplay.utils.Const;
import com.vms.antplay.utils.SharedPreferenceUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePassword extends AppCompatActivity implements View.OnClickListener {

    LinearLayout backLinear, logoutLinear;

    TextInputEditText edTxtOldPassword, edTxtNewPassword, edTxtConfirmPassword;
    Button btnUpdate;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccentDark_light, this.getTheme()));
        backLinear = (LinearLayout) findViewById(R.id.back_linear);
        logoutLinear = (LinearLayout) findViewById(R.id.logout_linear);

        edTxtOldPassword = findViewById(R.id.etOldPassword);
        edTxtNewPassword = findViewById(R.id.etNewPassword);
        edTxtConfirmPassword = findViewById(R.id.etConPassword);
        btnUpdate = findViewById(R.id.btn_resetPass);

        setOnClickListener();

        backLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ChangePassword.this, ProfileActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void setOnClickListener() {
        backLinear.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_resetPass:
                callChangePasswordAPI();
                break;
            case R.id.back_linear: {
                Intent i = new Intent(ChangePassword.this, ProfileActivity.class);
                startActivity(i);
                finish();
            }
        }
    }

    private void callChangePasswordAPI() {
        String access_token = SharedPreferenceUtils.getString(ChangePassword.this, Const.ACCESS_TOKEN);
        RetrofitAPI retrofitAPI = APIClient.getRetrofitInstance().create(RetrofitAPI.class);
        ChangePasswordRequestModal changePasswordRequestModal = new ChangePasswordRequestModal(edTxtOldPassword.getText().toString(), edTxtNewPassword.getText().toString(), edTxtConfirmPassword.getText().toString());
        Call<ChangePasswordResponseModal> call = retrofitAPI.changePassword(changePasswordRequestModal);
        call.enqueue(new Callback<ChangePasswordResponseModal>() {
            @Override
            public void onResponse(Call<ChangePasswordResponseModal> call, Response<ChangePasswordResponseModal> response) {
                if (response.isSuccessful() && response.body() != null) {
                    //Log.d("BILLING_PLAN", "" + response.body().getPlanName());

                }
            }

            @Override
            public void onFailure(Call<ChangePasswordResponseModal> call, Throwable t) {
                Log.d("BILLING_PLAN", "Failure");
            }
        });
    }
}