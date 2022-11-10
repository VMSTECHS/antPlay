package com.vms.antplay.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.vms.antplay.R;
import com.vms.antplay.api.APIClient;
import com.vms.antplay.api.RetrofitAPI;
import com.vms.antplay.model.requestModal.LoginRequestModal;
import com.vms.antplay.model.responseModal.GetBillingPlanResponseModal;
import com.vms.antplay.model.responseModal.LoginResponseModel;
import com.vms.antplay.model.responseModal.UserDetailsModal;
import com.vms.antplay.utils.AppUtils;
import com.vms.antplay.utils.Const;
import com.vms.antplay.utils.SharedPreferenceUtils;
import com.vms.antplay.utils.TcpClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginScreenActivity extends AppCompatActivity {

    private String TAG = "ANT_PLAY";
    Button btnLetsGo;
    TextView tvForgetPass, tvSignupHere;
    EditText etEmail, etPass;
    boolean isAllFieldsChecked = false;
    String st_email, st_password;
    private ProgressBar loadingPB;
    //-----TCP
    private TcpClient mTcpClient = null;
    private connectTask conctTask = null;
    private String ipAddressOfServerDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login_screen);
        tvForgetPass = (TextView) findViewById(R.id.tv_forgetPass);
        tvSignupHere = (TextView) findViewById(R.id.tv_signupHere);
        etEmail = (EditText) findViewById(R.id.et_email);
        etPass = (EditText) findViewById(R.id.et_password);
        btnLetsGo = (Button) findViewById(R.id.btn_signup);
        loadingPB = (ProgressBar) findViewById(R.id.loadingLogin_progress_xml);

        // etEmail.setText("rakesh@gmail.com");
        // etPass.setText("123456788");
        // etEmail.setText("AntplayOrchestrator");
        // etPass.setText("Acro@#208a");
        etEmail.setText("royv");
        etPass.setText("Antplay@123");

        //---------TCP---------
        new connectTask().execute();
        //-----------------

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

                // isAllFieldsChecked = CheckAllLoginFields();
                isAllFieldsChecked = true;

                //-----TCP--
                String message = etEmail.getText().toString();

                //sends the message to the server
                if (mTcpClient != null) {
                    mTcpClient.sendMessage(message);
                }


                //----------------------

                if (isAllFieldsChecked) {
                    // we can call Api here
                    st_email = etEmail.getText().toString();
                    st_password = etPass.getText().toString();

                    callLoginAPI(st_email, st_password);
                   /* Intent i = new Intent(LoginScreenActivity.this, MainActivity.class);
                    startActivity(i);*/
                }
            }
        });

    }

    private void callLoginAPI(String email, String password) {
        loadingPB.setVisibility(View.VISIBLE);
        RetrofitAPI retrofitAPI = APIClient.getRetrofitInstance().create(RetrofitAPI.class);
        LoginRequestModal loginRequestModal = new LoginRequestModal(email, password);
        Call<LoginResponseModel> call = retrofitAPI.loginUser(loginRequestModal);
        call.enqueue(new Callback<LoginResponseModel>() {
            @Override
            public void onResponse(Call<LoginResponseModel> call, Response<LoginResponseModel> response) {
                if (response.code() == 200) {
                    loadingPB.setVisibility(View.GONE);
                    Log.d(TAG, "" + response.body().getAccess());
                    SharedPreferenceUtils.saveString(LoginScreenActivity.this, Const.ACCESS_TOKEN, response.body().getAccess());
                    Intent i = new Intent(LoginScreenActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();

                }
                else if(response.code() == 401){
                    loadingPB.setVisibility(View.GONE);
                    Log.e(TAG, "Else condition");
                    AppUtils.showToast(Const.password_error, LoginScreenActivity.this);
                }
                else
                {
                    AppUtils.showToast(Const.no_records, LoginScreenActivity.this);

                }
            }

            @Override
            public void onFailure(Call<LoginResponseModel> call, Throwable t) {
                Log.e(TAG, "" + t);
                loadingPB.setVisibility(View.GONE);
                AppUtils.showToast(Const.something_went_wrong, LoginScreenActivity.this);
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

    //----------------------TCP CLIENT
    private class connectTask extends AsyncTask<String, String, TcpClient> {
        @Override
        protected TcpClient doInBackground(String... strings) {
            //create a TCPClient object and
            mTcpClient = new TcpClient(new TcpClient.OnMessageReceived() {
                @Override
                //here the messageReceived method is implemented
                public void messageReceived(String message) {
                    try {
                        //this method calls the onProgressUpdate
                        publishProgress(message);
                        if (message != null) {
                            System.out.println("Return Message from Socket::::: >>>>> " + message);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, ipAddressOfServerDevice);
           // mTcpClient.run();
            if (mTcpClient != null) {
                mTcpClient.sendMessage("Initial Message when connected with Socket Server");
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

            Log.e("Hello OnProgressUpdate", "Hello Update");

            //in the arrayList we add the messaged received from server
            //  arrayList.add(values[0]);

            // notify the adapter that the data set has changed. This means that new message received
            // from server was added to the list
            //  mAdapter.notifyDataSetChanged();
        }


    }

    @Override
    protected void onDestroy() {
        try {
            Log.e("Hello On Destroy", "On Destroy");
            System.out.println("onDestroy.");
            mTcpClient.sendMessage("bye");
            mTcpClient.stopClient();
            conctTask.cancel(true);
            conctTask = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    //---------------------
}