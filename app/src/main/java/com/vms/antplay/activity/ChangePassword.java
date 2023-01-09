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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.vms.antplay.R;
import com.vms.antplay.api.APIClient;
import com.vms.antplay.api.RetrofitAPI;
import com.vms.antplay.model.requestModal.ChangePasswordRequestModal;
import com.vms.antplay.model.responseModal.ChangePasswordResponseModal;
import com.vms.antplay.model.responseModal.GetBillingPlanResponseModal;
import com.vms.antplay.utils.AppUtils;
import com.vms.antplay.utils.Const;
import com.vms.antplay.utils.SharedPreferenceUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePassword extends AppCompatActivity implements View.OnClickListener {

    LinearLayout backLinear, logoutLinear;
    boolean isAllFieldChecked = false;
    TextInputEditText edTxtOldPassword, edTxtNewPassword, edTxtConfirmPassword;
    Button btnUpdate;
    private ProgressBar progressBar;

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
        progressBar = findViewById(R.id.loading_changePass);

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
              //  isAllFieldsChecked = CheckAllLoginFields();
                if(CheckAllFields()){
                    callChangePasswordAPI();
                }
                break;
            case R.id.back_linear: {
                Intent i = new Intent(ChangePassword.this, ProfileActivity.class);
                startActivity(i);
                finish();
            }
        }
    }

    private boolean CheckAllFields() {
        if (edTxtOldPassword.getText().toString().trim().length() == 0) {
            edTxtOldPassword.setError(getString(R.string.error_old_password));
            return false;
        }

//        else if (!edTxtOldPassword.getText().toString().matches(Const.passwordRegex)) {
//            edTxtOldPassword.setError(getString(R.string.error_old_password));
//            return false;
//        }

        if (edTxtNewPassword.getText().toString().trim().length()<8) {
            edTxtNewPassword.setError(getString(R.string.error_pass_minimum));
            return false;
        } else if (!edTxtNewPassword.getText().toString().matches(Const.passwordRegex)) {
            edTxtNewPassword.setError(getString(R.string.pass_regex));
            return false;
        }

        if (!edTxtNewPassword.getText().toString().equals(edTxtConfirmPassword.getText().toString())) {
            edTxtConfirmPassword.setError(getString(R.string.error_password_not_match));
            return false;
        }

        return true;
    }

    private void callChangePasswordAPI() {
        progressBar.setVisibility(View.VISIBLE);
        String access_token = SharedPreferenceUtils.getString(ChangePassword.this, Const.ACCESS_TOKEN);
        RetrofitAPI retrofitAPI = APIClient.getRetrofitInstance().create(RetrofitAPI.class);
        ChangePasswordRequestModal changePasswordRequestModal = new ChangePasswordRequestModal(edTxtOldPassword.getText().toString(), edTxtNewPassword.getText().toString(), edTxtConfirmPassword.getText().toString());
        Call<ChangePasswordResponseModal> call = retrofitAPI.changePassword("Bearer "+access_token,changePasswordRequestModal);
        call.enqueue(new Callback<ChangePasswordResponseModal>() {
            @Override
            public void onResponse(Call<ChangePasswordResponseModal> call, Response<ChangePasswordResponseModal> response) {
                if (response.code() == 200) {
                    Log.d("BILLING_PLANjjj", "success" );
                    progressBar.setVisibility(View.GONE);
                    Intent intent = new Intent(ChangePassword.this, LoginScreenActivity.class);
                    startActivity(intent);
                    finish();

                }
                else if (response.code() == Const.ERROR_CODE_404){
                    progressBar.setVisibility(View.GONE);
                    AppUtils.showSnack(getWindow().getDecorView().getRootView(),R.color.black,response.message(),ChangePassword.this);
                }
                else if (response.code() == Const.ERROR_CODE_500){
                    progressBar.setVisibility(View.GONE);
                    AppUtils.showSnack(getWindow().getDecorView().getRootView(),R.color.black,response.message(),ChangePassword.this);
                }

                else if (response.code() == Const.ERROR_CODE_400){
                    progressBar.setVisibility(View.GONE);
                    AppUtils.showSnack(getWindow().getDecorView().getRootView(),R.color.black,getString(R.string.wrong_old_pass),ChangePassword.this);
                }
            }

            @Override
            public void onFailure(Call<ChangePasswordResponseModal> call, Throwable t) {
                Log.d("BILLING_PLAN", "Failure");
                progressBar.setVisibility(View.GONE);
                AppUtils.showSnack(getWindow().getDecorView().getRootView(),R.color.black,Const.something_went_wrong,ChangePassword.this);

            }
        });
    }
}