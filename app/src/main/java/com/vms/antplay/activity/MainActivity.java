package com.vms.antplay.activity;


import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;


import com.google.android.material.tabs.TabLayout;
import com.razorpay.ExternalWalletListener;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultListener;
import com.razorpay.PaymentResultWithDataListener;
import com.vms.antplay.R;
import com.vms.antplay.adapter.PagerAdapter;
import com.vms.antplay.api.APIClient;
import com.vms.antplay.api.RetrofitAPI;
import com.vms.antplay.fragments.ArcadeFragment;
import com.vms.antplay.fragments.ComputerFragment;
import com.vms.antplay.fragments.FaqFargment;
import com.vms.antplay.fragments.HomeFragment;
import com.vms.antplay.fragments.SettingsFragment;
import com.vms.antplay.interfaces.VmTimeListener;
import com.vms.antplay.model.ImageModel;
import com.vms.antplay.model.responseModal.UserDetailsModal;
import com.vms.antplay.utils.AppUtils;
import com.vms.antplay.utils.Const;
import com.vms.antplay.utils.SharedPreferenceUtils;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements PaymentResultWithDataListener, ExternalWalletListener {

    private String TAG = "ANT_PLAY";
    ViewPager simpleViewPager;
    FrameLayout simpleFrameLayout;
    TabLayout tabLayout;
    private AlertDialog.Builder alertDialogBuilder;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccentDark_light, this.getTheme()));
        simpleFrameLayout = (FrameLayout) findViewById(R.id.simpleFrameLayout);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        //  simpleViewPager = (ViewPager) findViewById(R.id.viewPager);
        loadFragment(new HomeFragment());

        getUserDetails();
        alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setTitle("Payment Result");
        alertDialogBuilder.setPositiveButton("Ok", (dialog, which) -> {
            //do nothing
        });


        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // get the current selected tab's position and replace the fragment accordingly
                Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new HomeFragment();
                        break;
                    case 1:
                        fragment = new ComputerFragment();
                        break;
                    case 2:
                        fragment = new SettingsFragment();
                        break;
                    case 3:
                        fragment = new FaqFargment();
                        break;
                    case 4:
                        fragment = new ArcadeFragment();
                        break;
                    case 5:
                        logout();
                        //fragment = new ArcadeFragment();
                        break;

                    default:
                        fragment = new HomeFragment();
                }
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.simpleFrameLayout, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    private void logout() {
        Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
    }


    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.simpleFrameLayout, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public void onExternalWalletSelected(String s, PaymentData paymentData) {
        try {
            alertDialogBuilder.setMessage("External Wallet Selected:\nPayment Data: " + paymentData.getData());
            alertDialogBuilder.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPaymentSuccess(String razorpayPaymentID, PaymentData paymentData) {
        try {
            alertDialogBuilder.setMessage("Payment Successful :\nPayment ID: " + razorpayPaymentID + "\nPayment Data: " + paymentData.getData());
            alertDialogBuilder.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPaymentError(int code, String response, PaymentData paymentData) {
        try {
            alertDialogBuilder.setMessage("Payment Failed:\nPayment Data: " + paymentData.getData());
            alertDialogBuilder.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getUserDetails() {
        String access_token = SharedPreferenceUtils.getString(MainActivity.this, Const.ACCESS_TOKEN);
        RetrofitAPI retrofitAPI = APIClient.getRetrofitInstance().create(RetrofitAPI.class);
        Call<UserDetailsModal> call = retrofitAPI.getUserDetails("Bearer " + access_token);
        call.enqueue(new Callback<UserDetailsModal>() {
            @Override
            public void onResponse(Call<UserDetailsModal> call, Response<UserDetailsModal> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "" + response.body());
                    SharedPreferenceUtils.saveString(MainActivity.this, Const.FULL_NAME, response.body().getFirstName() + " " + response.body().getLastName());
                    SharedPreferenceUtils.saveString(MainActivity.this, Const.USER_EMAIL, response.body().getEmail());
                    SharedPreferenceUtils.saveString(MainActivity.this, Const.PHONE, response.body().getPhoneNumber());
                    SharedPreferenceUtils.saveString(MainActivity.this, Const.ADDRESS, response.body().getAddress());
                    SharedPreferenceUtils.saveString(MainActivity.this, Const.STATE, response.body().getState());
                    SharedPreferenceUtils.saveString(MainActivity.this, Const.CITY, response.body().getCity());
                    SharedPreferenceUtils.saveString(MainActivity.this, Const.USER_NAME, response.body().getUsername());
                    Log.d(TAG, "" + response.body().getPhoneNumber()+" "+response.body().getEmail());

                } else {
                    Log.e(TAG, "Else condition");
                    AppUtils.showToast(Const.no_records, MainActivity.this);
                }
            }

            @Override
            public void onFailure(Call<UserDetailsModal> call, Throwable t) {
                Log.e(TAG, "" + t);
                AppUtils.showToast(Const.something_went_wrong, MainActivity.this);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("TIME","MainActivity Resumed");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("TIME","MainActivity Stopped");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("TIME","MainActivity Destroyed");
    }
}