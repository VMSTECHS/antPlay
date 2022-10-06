package com.vms.antplay.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.vms.antplay.R;
import com.vms.antplay.api.APIClient;
import com.vms.antplay.api.RetrofitAPI;
import com.vms.antplay.model.requestModal.ChangePasswordRequestModal;
import com.vms.antplay.model.requestModal.UserUpdateRequestModal;
import com.vms.antplay.model.responseModal.ChangePasswordResponseModal;
import com.vms.antplay.model.responseModal.UserDetailsModal;
import com.vms.antplay.model.responseModal.UserUpdateResponseModal;
import com.vms.antplay.utils.AppUtils;
import com.vms.antplay.utils.Const;
import com.vms.antplay.utils.SharedPreferenceUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {

    private String TAG = "ANT_PLAY";
    LinearLayout linearLayout;
    EditText edTxtName, edTxtUserName, edTxtPhoneNumber, edTxtEmail, edTxtAge, edTxtCity, edTxtAddress, edTxtState;
    Button buttonUpdateProfile;
    private ProgressBar progressBar;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccentDark_light, this.getTheme()));

        edTxtName = findViewById(R.id.edTxtFullName);
        edTxtUserName = findViewById(R.id.edTxtUserName);
        edTxtPhoneNumber = findViewById(R.id.edTxtPhoneNumber);
        edTxtEmail = findViewById(R.id.edTxtEmail);
        edTxtAge = findViewById(R.id.edTxtAge);
        edTxtCity = findViewById(R.id.edTxtCity);
        edTxtAddress = findViewById(R.id.edTxtAddress);
        edTxtState = findViewById(R.id.edTxtState);

        buttonUpdateProfile = findViewById(R.id.buttonUpdateProfile);
        progressBar = (ProgressBar) findViewById(R.id.progressBarEditProfile);

        setData();
        linearLayout = (LinearLayout) findViewById(R.id.back_linear_edit);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserProfile();
            }
        });
    }

    private void setData() {
        String fullName = SharedPreferenceUtils.getString(EditProfileActivity.this, Const.FULL_NAME);
        String email = SharedPreferenceUtils.getString(EditProfileActivity.this, Const.USER_EMAIL);
        String phoneNumber = SharedPreferenceUtils.getString(EditProfileActivity.this, Const.PHONE);
        String address = SharedPreferenceUtils.getString(EditProfileActivity.this, Const.ADDRESS);
        String state = SharedPreferenceUtils.getString(EditProfileActivity.this, Const.STATE);
        String city = SharedPreferenceUtils.getString(EditProfileActivity.this, Const.CITY);
        String userName = SharedPreferenceUtils.getString(EditProfileActivity.this, Const.USER_NAME);

        edTxtName.setText(fullName);
        edTxtUserName.setText(userName);
        edTxtPhoneNumber.setText(phoneNumber);
        edTxtEmail.setText(email);
        edTxtAge.setText("28");
        edTxtCity.setText(city);
        edTxtAddress.setText(address);
        edTxtState.setText(state);
    }


    private void updateUserProfile() {
        progressBar.setVisibility(View.VISIBLE);
        String access_token = SharedPreferenceUtils.getString(EditProfileActivity.this, Const.ACCESS_TOKEN);
        RetrofitAPI retrofitAPI = APIClient.getRetrofitInstance().create(RetrofitAPI.class);
        UserUpdateRequestModal updateRequestModal = new UserUpdateRequestModal(edTxtEmail.getText().toString(),
                "9810000345",
                edTxtAddress.getText().toString(),
                edTxtState.getText().toString(),
                edTxtCity.getText().toString(),
                "110044");
        Call<UserUpdateResponseModal> call = retrofitAPI.userUpdate("Bearer "+access_token,updateRequestModal);
        call.enqueue(new Callback<UserUpdateResponseModal>() {
            @Override
            public void onResponse(Call<UserUpdateResponseModal> call, Response<UserUpdateResponseModal> response) {
                if (response.isSuccessful() && response.body() != null) {
                    //Log.d("BILLING_PLAN", "" + response.body().getPlanName());

                    getUserDetails();
                }
            }

            @Override
            public void onFailure(Call<UserUpdateResponseModal> call, Throwable t) {
                Log.d("BILLING_PLAN", "Failure");
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void getUserDetails() {
        String access_token = SharedPreferenceUtils.getString(EditProfileActivity.this, Const.ACCESS_TOKEN);
        RetrofitAPI retrofitAPI = APIClient.getRetrofitInstance().create(RetrofitAPI.class);
        Call<UserDetailsModal> call = retrofitAPI.getUserDetails("Bearer " + access_token);
        call.enqueue(new Callback<UserDetailsModal>() {
            @Override
            public void onResponse(Call<UserDetailsModal> call, Response<UserDetailsModal> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "" + response.body());
                    progressBar.setVisibility(View.GONE);
                    SharedPreferenceUtils.saveString(EditProfileActivity.this, Const.FULL_NAME, response.body().getFirstName() + " " + response.body().getLastName());
                    SharedPreferenceUtils.saveString(EditProfileActivity.this, Const.USER_EMAIL, response.body().getEmail());
                    SharedPreferenceUtils.saveString(EditProfileActivity.this, Const.PHONE, response.body().getPhoneNumber());
                    SharedPreferenceUtils.saveString(EditProfileActivity.this, Const.ADDRESS, response.body().getAddress());
                    SharedPreferenceUtils.saveString(EditProfileActivity.this, Const.STATE, response.body().getState());
                    SharedPreferenceUtils.saveString(EditProfileActivity.this, Const.CITY, response.body().getCity());
                    SharedPreferenceUtils.saveString(EditProfileActivity.this, Const.USER_NAME, response.body().getUsername());


                } else {
                    Log.e(TAG, "Else condition");
                    progressBar.setVisibility(View.GONE);
                    AppUtils.showToast(Const.no_records, EditProfileActivity.this);
                }
            }

            @Override
            public void onFailure(Call<UserDetailsModal> call, Throwable t) {
                Log.e(TAG, "" + t);
                progressBar.setVisibility(View.GONE);
                AppUtils.showToast(Const.something_went_wrong, EditProfileActivity.this);
            }
        });

    }

}