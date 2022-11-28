package com.vms.antplay.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vms.antplay.R;
import com.vms.antplay.utils.Const;
import com.vms.antplay.utils.SharedPreferenceUtils;

import org.w3c.dom.Text;

public class ProfileActivity extends AppCompatActivity {

    LinearLayout backLinear, logoutLinear, linear_Change, linearAgree, linearWebsite, linearAbout,
            linearPayment, linearEdit, linearDiscord, linearInstagram;

    TextView tv_changePassword, tv_manageSubs, txtUserID;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccentDark_light, this.getTheme()));

        backLinear = (LinearLayout) findViewById(R.id.back_linear);
        logoutLinear = (LinearLayout) findViewById(R.id.logout_linear);
        linearAgree = (LinearLayout) findViewById(R.id.linear_agreements);
        linear_Change = (LinearLayout) findViewById(R.id.linear_changePassword);
        linearWebsite = (LinearLayout) findViewById(R.id.linear_website);
        linearAbout = (LinearLayout) findViewById(R.id.linear_aboutUs);
        linearPayment = (LinearLayout) findViewById(R.id.linear_paymentHistory);
        linearDiscord = (LinearLayout) findViewById(R.id.linear_discord);
        linearInstagram = (LinearLayout) findViewById(R.id.linear_instagram);
        linearEdit = (LinearLayout) findViewById(R.id.linear_edit);
        tv_manageSubs = (TextView) findViewById(R.id.tv_manageSubscription);
        txtUserID = (TextView) findViewById(R.id.txtUserEmailID);

        setData();

        tv_manageSubs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this, PaymentPlanActivity.class);
                startActivity(i);

            }
        });


        backLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
        logoutLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        linear_Change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this, ChangePassword.class);
                startActivity(i);
                finish();
            }
        });

        linearAgree = (LinearLayout) findViewById(R.id.linear_agreements);

        linearAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProfileActivity.this, Agreement_User.class);
                startActivity(i);

            }
        });

        linearWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://antplay.tech/"));
                startActivity(browserIntent);
            }
        });
        linearDiscord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://discord.gg/vGHsh8MYXX"));
                startActivity(browserIntent);
            }
        });
        linearInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/antplay.tech/"));
                startActivity(browserIntent);
            }
        });


        linearAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProfileActivity.this, AboutUs.class);
                startActivity(i);
            }
        });
        linearPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProfileActivity.this, PaymentHistory.class);
                startActivity(i);
            }
        });
        linearEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProfileActivity.this, EditProfileActivity.class);
                startActivity(i);
            }
        });
    }

    private void setData() {
        String fullName = SharedPreferenceUtils.getString(ProfileActivity.this, Const.FULL_NAME);
        String email = SharedPreferenceUtils.getString(ProfileActivity.this, Const.USER_EMAIL);
        String phoneNumber = SharedPreferenceUtils.getString(ProfileActivity.this, Const.PHONE);
        String address = SharedPreferenceUtils.getString(ProfileActivity.this, Const.ADDRESS);
        String state = SharedPreferenceUtils.getString(ProfileActivity.this, Const.STATE);
        String city = SharedPreferenceUtils.getString(ProfileActivity.this, Const.CITY);
        String userName = SharedPreferenceUtils.getString(ProfileActivity.this, Const.USER_NAME);

        txtUserID.setText(userName);

    }
}