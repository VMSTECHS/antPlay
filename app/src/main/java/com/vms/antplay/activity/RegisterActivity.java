package com.vms.antplay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.vms.antplay.R;

public class RegisterActivity extends AppCompatActivity {

    EditText etEmail, etUsername, etPassword;
    Button btnSignup;
    TextView tvAlreadyRegister;
    CheckBox checkBox;
    String st_email, st_username, st_password;
    boolean isAllFieldsChecked = false;
    Boolean checkBoxState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);
        etEmail = (EditText) findViewById(R.id.et_email);
        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);
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

                if (isAllFieldsChecked) {
                    // we can call Api here
                    Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(i);
                }
            }
        });

    }

    private boolean CheckAllFields() {
        //String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String emailPattern = "^(?:\\d{10}|\\w+@\\w+\\.\\w{2,3})$";

        if (etEmail.length() == 0) {
            etEmail.setError(getString(R.string.error_email));
            return false;
        } else if (!etEmail.getText().toString().matches(emailPattern)) {
            etEmail.setError(getString(R.string.error_invalidEmail));
            return false;
        }

        if (etUsername.length() == 0) {
            etUsername.setError(getString(R.string.error_username));
            return false;
        }

        if (etPassword.length() == 0) {
            etPassword.setError(getString(R.string.error_password));
            return false;
        } else if (etPassword.length() < 8) {
            etPassword.setError(getString(R.string.error_pass_minimum));
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