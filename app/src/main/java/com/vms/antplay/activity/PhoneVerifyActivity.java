package com.vms.antplay.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.vms.antplay.R;

import java.util.concurrent.TimeUnit;

public class PhoneVerifyActivity extends AppCompatActivity implements View.OnClickListener {


    private static final String TAG = "PhoneAuthActivity";

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    Button verifyPhoneNumber, btnVerifyOTP;
    EditText edtPhoneNumber, edtOtpDigitSix, edtOtpDigitFive, edtOtpDigitFour, edtOtpDigitThree, edtOtpDigitTwo, edtOtpDigitOne;
    LinearLayout llOTP;
    TextView txtResendOtp;
    RelativeLayout rlProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_verify);
        verifyPhoneNumber = findViewById(R.id.btnVerifyPhone);
        btnVerifyOTP = findViewById(R.id.btnVerifyOTP);
        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);

        edtOtpDigitSix = findViewById(R.id.otpDigitSix);
        edtOtpDigitFive = findViewById(R.id.otpDigitFive);
        edtOtpDigitFour = findViewById(R.id.otpDigitFour);
        edtOtpDigitThree = findViewById(R.id.otpDigitThree);
        edtOtpDigitTwo = findViewById(R.id.otpDigitTwo);
        edtOtpDigitOne = findViewById(R.id.otpDigitOne);

        llOTP = findViewById(R.id.llOTP);
        txtResendOtp = findViewById(R.id.resendOtp);
        rlProgress = findViewById(R.id.rlProgress);

        // [START initialize_auth]
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        mAuth.setLanguageCode("en");
        // [END initialize_auth]
        setOnClickListener();

        // Initialize phone auth callbacks
          initialisePhoneAuthCallback();

        otpBlockBehaviour();

    }

    private void setOnClickListener() {
        btnVerifyOTP.setOnClickListener(this);
        verifyPhoneNumber.setOnClickListener(this);
        txtResendOtp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnVerifyPhone:
                if (edtPhoneNumber.getText().toString() != null) {
                    authenticatePhoneNumber("+91" + edtPhoneNumber.getText().toString().trim());
                } else {
                    Toast.makeText(PhoneVerifyActivity.this, "Please Provide Phone Number", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnVerifyOTP:
                getOTPFromUser();
                break;
            case R.id.resendOtp:
                resendVerificationCode("+91" + edtPhoneNumber.getText().toString().trim(), mResendToken);
                break;
        }
    }

    private void setOtpOnBox(String otpCode) {
        int otp = Integer.parseInt(otpCode);
        int reminder = otp % 10;
        otp = otp / 10;
        edtOtpDigitOne.setText(String.valueOf(reminder));
        reminder = otp % 10;
        otp = otp / 10;
        edtOtpDigitTwo.setText(String.valueOf(reminder));
        reminder = otp % 10;
        otp = otp / 10;
        edtOtpDigitThree.setText(String.valueOf(reminder));
        reminder = otp % 10;
        otp = otp / 10;
        edtOtpDigitFour.setText(String.valueOf(reminder));
        reminder = otp % 10;
        otp = otp / 10;
        edtOtpDigitFive.setText(String.valueOf(reminder));
        reminder = otp % 10;
        edtOtpDigitSix.setText(String.valueOf(reminder));


    }

    private void initialisePhoneAuthCallback() {
        // [START phone_auth_callbacks]
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                Log.d(TAG, "onVerificationCompleted:" + phoneAuthCredential);

                String otpCode = phoneAuthCredential.getSmsCode();
                Log.d(TAG, "OTP :" + otpCode);

                if (otpCode != null) {
                    // if the code is not null then
                    // we are setting that code to
                    // our OTP edittext field.
                    setOtpOnBox(otpCode);
                    // edtOTP.setText(otpCode);

                    // after setting this code
                    // to OTP edittext field we
                    // are calling our verifyCode method.
                    verifyCode(otpCode);
                }

                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed", e);
                Toast.makeText(PhoneVerifyActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    Log.w(TAG, "onVerificationFailed", e);
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    Log.w(TAG, "onVerificationFailed", e);
                }

                // Show a message and update the UI
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:" + verificationId);

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;

                rlProgress.setVisibility(View.GONE);
                llOTP.setVisibility(View.VISIBLE);
                txtResendOtp.setVisibility(View.VISIBLE);
                verifyPhoneNumber.setVisibility(View.INVISIBLE);
                btnVerifyOTP.setVisibility(View.VISIBLE);

            }
        };

    }


    // [START send_verification]
    private void authenticatePhoneNumber(String phoneNumber) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(PhoneVerifyActivity.this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
        rlProgress.setVisibility(View.VISIBLE);
    }
    // [END send_verification]


    // [START resend_verification]
    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(PhoneVerifyActivity.this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .setForceResendingToken(token)     // ForceResendingToken from callbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    // [END resend_verification]

    private void getOTPFromUser() {
        rlProgress.setVisibility(View.VISIBLE);
        String digitOne, digitTwo, digitThree, digitFour, digitFive, digitSix, otpString;
        digitSix = edtOtpDigitSix.getText().toString().trim();
        digitFive = edtOtpDigitFive.getText().toString().trim();
        digitFour = edtOtpDigitFour.getText().toString().trim();
        digitThree = edtOtpDigitThree.getText().toString().trim();
        digitTwo = edtOtpDigitTwo.getText().toString().trim();
        digitOne = edtOtpDigitOne.getText().toString().trim();
        otpString = digitSix + digitFive + digitFour + digitThree + digitTwo + digitOne;

        verifyCode(otpString);
    }

    /***
     * Method : verifyCode
     * Param : String code
     * Return Type : Void
     * Description : Verify the OTP code with the firebase
     ***/
    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
        //After getting Verification Call SignIn
        signInWithPhoneAuthCredential(credential);
    }


    /***
     * Method : signInWithPhoneAuthCredential
     * Param : PhoneAuthCredential credential
     * Return Type : Void
     * Description : Sign in with the credentials to firebase
     ***/
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            Log.d(TAG, "User Contact Number: " + user.getPhoneNumber());
                            rlProgress.setVisibility(View.GONE);
                            FirebaseAuth.getInstance().signOut();
                            // Update UI
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }


    /***
     * Method : otpBlockBehaviour
     * Param : No Param
     * Return Type : Void
     * Description : To set the behaviour of the OTP boxes
     ***/
    private void otpBlockBehaviour() {
        edtOtpDigitSix.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (count == 1) {

                    edtOtpDigitFive.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edtOtpDigitFive.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 1) {

                    edtOtpDigitFour.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtOtpDigitFour.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 1) {

                    edtOtpDigitThree.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtOtpDigitThree.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 1) {

                    edtOtpDigitTwo.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtOtpDigitTwo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 1) {

                    edtOtpDigitOne.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

}