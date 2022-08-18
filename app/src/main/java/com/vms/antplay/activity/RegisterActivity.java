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

import com.vms.antplay.R;
import com.vms.antplay.api.APIClient;
import com.vms.antplay.api.RetrofitAPI;
import com.vms.antplay.model.requestModal.RegisterRequestModal;
import com.vms.antplay.model.responseModal.LoginResponseModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    EditText etEmail, etFirstname, etLastname, etPhone, etPassword,  etConfirmPass, etAge, etAddress, etCity, etState, etPincode;
    Button btnSignup;
    TextView tvAlreadyRegister;
    CheckBox checkBox;
    String st_fname,st_lastname,st_email, st_password, st_confirmPass, st_city, st_pincode, st_age, st_state, st_address;
    Long st_phone;
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
                    st_phone = Long.valueOf(etPhone.getText().toString());
                    Integer intValues = st_phone.intValue();
                    st_password = etConfirmPass.getText().toString();
                    st_address = etAddress.getText().toString();
                    st_age = etAge.getText().toString();
                    st_state = etState.getText().toString();
                    st_city = etCity.getText().toString();
                    st_pincode = etPincode.getText().toString();

                    callRegisterApi(st_fname,st_lastname,st_email,intValues,st_password,st_address,st_age,st_state,st_city,st_pincode);

                }
            }
        });

    }

    private void callRegisterApi(String st_fname, String st_lastname, String st_email,
                                 Integer intValues, String st_password, String st_address,
                                 String st_age, String st_state, String st_city, String st_pincode) {

        loadingPB.setVisibility(View.VISIBLE);

        RetrofitAPI retrofitAPI = APIClient.getRetrofitInstance().create(RetrofitAPI.class);
        RegisterRequestModal modal = new RegisterRequestModal(st_fname, st_lastname, st_email, intValues,st_password,st_address,st_age,st_state,st_city,Integer.parseInt(st_pincode));
        Log.e("hello modal", String.valueOf(modal));
        Call<LoginResponseModel> call = retrofitAPI.registerUser(modal);
        call.enqueue(new Callback<LoginResponseModel>() {
            @Override
            public void onResponse(Call<LoginResponseModel> call, Response<LoginResponseModel> response) {

                loadingPB.setVisibility(View.GONE);

                // LoginResponseModel responseFromAPI = response.body();
                if (response.isSuccessful() && response.body() != null) {

                    if (response.body().getFirstName()!=null) {
//                        et_name.setText("");
//                        et_mobile.setText("");
//                        et_date.setText("");
//                        et_month.setText("");
//                        et_year.setText("");
//                        et_city.setText("");
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


                    } else {
                        loadingPB.setVisibility(View.GONE);
                       Log.e("hello else", "Error Failure");
//                        Log.e("hello params", "" + "name-" + name + " -dob-" + dob + "mobile-" + mobile);
                       // Toast.makeText(RegisterActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onFailure(Call<LoginResponseModel> call, Throwable t) {
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
        }
        else if (etConfirmPass.length() < 8) {
            etConfirmPass.setError(getString(R.string.error_Confirmpass_minimum));
            return false;
        }
        if(!etPassword.getText().toString().equals(etConfirmPass.getText().toString())){
            etConfirmPass.setError(getString(R.string.error_password_not_match));
            return  false;
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