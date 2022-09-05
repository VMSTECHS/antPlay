package com.vms.antplay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.vms.antplay.R;
import com.vms.antplay.api.APIClient;
import com.vms.antplay.api.RetrofitAPI;
import com.vms.antplay.model.requestModal.LoginRequestModal;
import com.vms.antplay.model.responseModal.LoginResponseModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginScreenActivity extends AppCompatActivity {

    Button btnLetsGo;
    TextView tvForgetPass, tvSignupHere;
    EditText etEmail,etPass;
    boolean isAllFieldsChecked = false;
    String st_email, st_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login_screen);
        tvForgetPass =(TextView) findViewById(R.id.tv_forgetPass);
        tvSignupHere =(TextView) findViewById(R.id.tv_signupHere);
        etEmail =(EditText) findViewById(R.id.et_email);
        etPass =(EditText) findViewById(R.id.et_password);
        btnLetsGo =(Button) findViewById(R.id.btn_signup);

        etEmail.setText("rakesh@gmail.com");
        etPass.setText("123456788");


        tvForgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginScreenActivity.this, ForgetPassword.class);
                startActivity(i);
            }
        });
        tvSignupHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginScreenActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
        btnLetsGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                isAllFieldsChecked = CheckAllLoginFields();

                if (isAllFieldsChecked) {
                    // we can call Api here
                   // st_email = etEmail.getText().toString();
                   // st_password = etPass.getText().toString();


                   // callLoginAPI( st_email, st_password);
                    Intent i = new Intent(LoginScreenActivity.this, MainActivity.class);
                    startActivity(i);
                }
            }
        });

    }

    private void callLoginAPI(String email, String password) {

        RetrofitAPI retrofitAPI = APIClient.getRetrofitInstance().create(RetrofitAPI.class);
        LoginRequestModal loginRequestModal = new LoginRequestModal(email,password);
        Call<LoginResponseModel> call = retrofitAPI.loginUser(loginRequestModal);
        call.enqueue(new Callback<LoginResponseModel>() {
            @Override
            public void onResponse(Call<LoginResponseModel> call, Response<LoginResponseModel> response) {
                if (response.isSuccessful() && response.body()!=null){

                }
            }

            @Override
            public void onFailure(Call<LoginResponseModel> call, Throwable t) {

            }
        });

    }

    private boolean CheckAllLoginFields() {
       // String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String emailPattern = "^(?:\\d{10}|\\w+@\\w+\\.\\w{2,3})$";

        if (etEmail.length() == 0) {
            etEmail.setError(getString(R.string.error_email));
            return false;
        } else if (!etEmail.getText().toString().matches(emailPattern)) {
            etEmail.setError(getString(R.string.error_invalidEmail));
            return false;
        }

        if (etPass.length() == 0) {
            etPass.setError(getString(R.string.error_password));
            return false;
        } else if (etPass.length() < 8) {
            etPass.setError(getString(R.string.error_pass_minimum));
            return false;
        }
        return true;
    }
}