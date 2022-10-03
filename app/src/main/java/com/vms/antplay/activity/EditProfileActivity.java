package com.vms.antplay.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.vms.antplay.R;
import com.vms.antplay.utils.Const;
import com.vms.antplay.utils.SharedPreferenceUtils;

public class EditProfileActivity extends AppCompatActivity {

    LinearLayout linearLayout;
    EditText edTxtName, edTxtUserName, edTxtPhoneNumber, edTxtEmail, edTxtAge, edTxtCity, edTxtAddress, edTxtState;

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
        setData();
        linearLayout = (LinearLayout) findViewById(R.id.back_linear_edit);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
}