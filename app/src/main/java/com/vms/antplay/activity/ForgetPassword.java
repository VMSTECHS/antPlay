package com.vms.antplay.activity;


import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.vms.antplay.R;

public class ForgetPassword extends AppCompatActivity {

    EditText etEmailForget;
    Button btnForgetPass;
    TextView tvTermsCondition;
    boolean isAllFieldChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_forget_password);

        etEmailForget =(EditText) findViewById(R.id.et_emailForget);
        btnForgetPass =(Button) findViewById(R.id.btn_resetPass);
        tvTermsCondition =(TextView) findViewById(R.id.tv_termsAndCondition_forget);

        btnForgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAllFieldChecked = checkEmail();
                if(isAllFieldChecked){
                    // Call Reset Password API
                    Toast.makeText(ForgetPassword.this, "Thank You", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean checkEmail() {
        String emailPattern = "^(?:\\d{10}|\\w+@\\w+\\.\\w{2,3})$";
        if (etEmailForget.length() == 0) {
            etEmailForget.setError(getString(R.string.error_email));
            return false;
        } else if (!etEmailForget.getText().toString().matches(emailPattern)) {
            etEmailForget.setError(getString(R.string.error_invalidEmail));
            return false;
        }
        return true;
    }
}