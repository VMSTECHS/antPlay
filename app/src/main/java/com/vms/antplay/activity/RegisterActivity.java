package com.vms.antplay.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.vms.antplay.R;
import com.vms.antplay.api.APIClient;
import com.vms.antplay.api.RetrofitAPI;
import com.vms.antplay.model.requestModal.RegisterRequestModal;
import com.vms.antplay.model.responseModal.LoginResponseModel;
import com.vms.antplay.model.responseModal.RegisterResponseModal;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    EditText etEmail, etFirstname, etLastname, etPhone, etPassword, etConfirmPass, etAge, etAddress, etCity, etState, etPincode;
    Button btnSignup;
    TextView tvAlreadyRegister;
    CheckBox checkBox;
    String st_fname, st_lastname, st_email, st_password, st_confirmPass, st_city, st_pincode, st_age, st_state, st_address, st_middleName,st_isNewUser, st_isSubscribed;
    String st_phone, st_LastLogin;
    boolean isAllFieldsChecked = false;
    Boolean checkBoxState;
    private ProgressBar loadingPB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);

        etFirstname = (EditText) findViewById(R.id.et_firstname);
        etLastname = (EditText) findViewById(R.id.et_lastname);
        etPhone = (EditText) findViewById(R.id.et_phone);
        etEmail = (EditText) findViewById(R.id.et_email);
        etPassword = (EditText) findViewById(R.id.et_password);
        etConfirmPass = (EditText) findViewById(R.id.et_confirmPassword);
        etAge = (EditText) findViewById(R.id.et_age);
        etAddress = (EditText) findViewById(R.id.et_address);
        etPincode = (EditText) findViewById(R.id.et_pinCode);
        etState = (EditText) findViewById(R.id.et_state);
        etCity = (EditText) findViewById(R.id.et_city);
        loadingPB = (ProgressBar) findViewById(R.id.loading_progress_xml);

        btnSignup = (Button) findViewById(R.id.btn_signup);
        tvAlreadyRegister = (TextView) findViewById(R.id.tv_alreadyRegister);
        checkBox = (CheckBox) findViewById(R.id.simpleCheckBox);

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check current state of a check box (true or false)
                checkBoxState = checkBox.isChecked();
            }
        });
        tvAlreadyRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAllFieldsChecked = CheckAllFields();
                //  Integer intValue = myLong.intValue();
                if (isAllFieldsChecked) {
                    // we can call Api here
                    st_fname = etFirstname.getText().toString();
                    st_lastname = etLastname.getText().toString();
                    st_email = etEmail.getText().toString();
                    st_phone = etPhone.getText().toString();
                    st_middleName = etFirstname.getText().toString();
                    st_isSubscribed = "true";
                    st_isNewUser ="true";
                    st_LastLogin = "";
                    st_password = etConfirmPass.getText().toString();
                    st_address = etAddress.getText().toString();
                    st_age = etAge.getText().toString();
                    st_state = etState.getText().toString();
                    st_city = etCity.getText().toString();
                    st_pincode = etPincode.getText().toString();


                    Log.e("Request Params", "" + st_fname + ", " + st_lastname + "," + st_email + "," + st_phone + "," +
                            st_password + "," + st_address + "," + st_age + "," + st_state + "," + st_city + "," + st_pincode);

                    callRegisterApi(st_fname, st_lastname, st_email, st_phone, st_middleName, st_LastLogin, st_isNewUser,st_isSubscribed,st_password, st_address, st_age, st_state, st_city, st_pincode);

                }
            }
        });

    }

    private void callRegisterApi(String st_fname, String st_lastname, String st_email,
                                 String st_phone,String st_middleName,String st_LastLogin, String st_isNewUser, String st_isSubscribed, String st_password, String st_address,
                                 String st_age, String st_state, String st_city, String st_pincode) {

        loadingPB.setVisibility(View.VISIBLE);

        RetrofitAPI retrofitAPI = APIClient.getRetrofitInstance().create(RetrofitAPI.class);
        RegisterRequestModal modal = new RegisterRequestModal(st_fname, st_lastname, st_email, st_phone,st_LastLogin, st_isNewUser,st_isSubscribed, st_address, st_age, st_state,st_middleName, st_password,st_city, st_pincode);
        Call<RegisterResponseModal> call = retrofitAPI.registerUser(modal);
        call.enqueue(new Callback<RegisterResponseModal>() {
            @Override
            public void onResponse(Call<RegisterResponseModal> call, Response<RegisterResponseModal> response) {

                loadingPB.setVisibility(View.GONE);
                Log.e("Hello in",""+response.body());

                // LoginResponseModel responseFromAPI = response.body();
                if (response.isSuccessful()) {
                    Log.e("Hello in2222","Hello innnn2222");
                    if (response.body().getMessage().equals("User Register Successfully")) {
                        etFirstname.setText("");
                        etLastname.setText("");
                        etEmail.setText("");
                        etPhone.setText("");
                        etConfirmPass.setText("");
                        etAddress.setText("");
                        etAge.setText("");
                        etState.setText("");
                        etCity.setText("");
                        etPincode.setText("");
                        Toast.makeText(RegisterActivity.this, "Successfully Register", Toast.LENGTH_SHORT).show();
//                        String userid = String.valueOf(response.body().getData().getId());
//                        SharedPreferences shared = getSharedPreferences("Login", MODE_PRIVATE);
//                        SharedPreferences.Editor editor = shared.edit();
//                        editor.putString(Const.USERS_ID, userid);
//                        editor.commit();
                        //   Log.e("Hello Userid register", "" + userid);

                        Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(i);
                        finish();


                    }
                    else {
                        Log.e("Hello in33333","Hello innnn33333");
                        loadingPB.setVisibility(View.GONE);
                        Log.e("hello else", "Error Failure");
//                        Log.e("hello params", "" + "name-" + name + " -dob-" + dob + "mobile-" + mobile);
                        Toast.makeText(RegisterActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onFailure(Call<RegisterResponseModal> call, Throwable t) {
                // responseTV.setText("Error found is : " + t.getMessage());
                loadingPB.setVisibility(View.GONE);
                Toast.makeText(RegisterActivity.this, R.string.something_wrong, Toast.LENGTH_SHORT).show();
            }
        });

    }


    private boolean CheckAllFields() {
        //String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String emailPattern = "^(?:\\d{10}|\\w+@\\w+\\.\\w{2,3})$";

        if (etFirstname.length() == 0) {
            etFirstname.setError(getString(R.string.error_firstname));
            return false;
        }
        if (etLastname.length() == 0) {
            etLastname.setError(getString(R.string.error_lastname));
            return false;
        }

        if (etPhone.length() == 0) {
            etPhone.setError(getString(R.string.error_phone));
            return false;
        }
        if (etEmail.length() == 0) {
            etEmail.setError(getString(R.string.error_email));
            return false;
        }

        if (etPassword.length() == 0) {
            etPassword.setError(getString(R.string.error_password));
            return false;
        } else if (etPassword.length() < 8) {
            etPassword.setError(getString(R.string.error_pass_minimum));
            return false;
        }

        if (etConfirmPass.length() == 0) {
            etConfirmPass.setError(getString(R.string.error_confirmPass));
            return false;
        } else if (etConfirmPass.length() < 8) {
            etConfirmPass.setError(getString(R.string.error_Confirmpass_minimum));
            return false;
        }
        if (!etPassword.getText().toString().equals(etConfirmPass.getText().toString())) {
            etConfirmPass.setError(getString(R.string.error_password_not_match));
            return false;
        }
        if (etAge.length() == 0) {
            etAge.setError(getString(R.string.error_age));
            return false;
        }
        if (etAddress.length() == 0) {
            etAddress.setError(getString(R.string.error_address));
            return false;
        }
        if (etPincode.length() == 0) {
            etPincode.setError(getString(R.string.error_pinCode));
            return false;
        }
        if (etState.length() == 0) {
            etState.setError(getString(R.string.error_state));
            return false;
        }
        if (etEmail.length() == 0) {
            etEmail.setError(getString(R.string.error_email));
            return false;
        } else if (!etEmail.getText().toString().matches(emailPattern)) {
            etEmail.setError(getString(R.string.error_invalidEmail));
            return false;
        }

        if (!checkBox.isChecked()) {
            Toast.makeText(this, getString(R.string.error_checkbox), Toast.LENGTH_SHORT).show();
            return false;
        }
        // after all validation return true.
        return true;
    }

}