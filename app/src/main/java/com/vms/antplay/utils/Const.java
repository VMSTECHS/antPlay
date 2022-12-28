package com.vms.antplay.utils;

import java.util.ArrayList;
import java.util.List;

public class Const {
    public static String razorPayMerchantId = "KEVxW3g3K0GfQr";

    public static String ACCESS_TOKEN="access_token";
    public static String FULL_NAME="full_name";
    public static String USER_EMAIL="email";
    public static String PHONE="phone_number";
    public static String ADDRESS="address";
    public static String STATE="state";
    public static String CITY="city";
    public static String USER_NAME="username";
    public static String BASIC_SKU="antplay_30_hours";
    public static String PREMIUM_SKU="antplay_250_hours";
    public static String BASIC_PLAN_1="basic-plan-1";
    public static String SUBSCRIPTION = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjm7sjWPMIHx/i6fchZh+zftM8u3spnkb887DiEsGIh9g1d0ag6jr801i1Njfxf0iCDvNKrUeg9Bt3CSGP89YL7m3ToWtkAosM8lVetjYb9NBGazFmqZn4R2dq5WwSZRef3uMZTykJaakmV9EqBOFJkidupMr8+IHsfyDzMVG/cFnUZQA9z0e+mNi6ZDP56+ZzmBQgrlOkcmRiNBHtt2WFQjvBNBlkZWeq10KMc3iLR8t+pZapbhloVBivPAXeevZWhWq+dfIwRDlY8rjRB27+Cqq6js5aGUbgvo74fuBlC+kED7zz1uT3QdGrIBNMOpFQkMmcwwntEhj1ktq+u+fmwIDAQAB";

    /****
     * RazorPay Test Credentials
     * ****/
    //public static String razorPayTestKeyId = "rzp_test_gSzx0DgfSNdO3n";
    public static String razorPayTestKeyId = "rzp_test_1It0h0ZrKz4dOK";
    public static String razorPayTestKeySecret = "WakWnPSHkMmhaI6e0jRQbvnz";

    /****
     * RazorPay Live Credentials
     * ****/
    public static String razorPayLiveKeyId = "rzp_test_1It0h0ZrKz4dOK";
    public static String razorPayLiveKeySecret = "uJtGcQIpTQpcw2gubCKK7F33";

    /****
     * Errors Message
     * ****/
    public static String something_went_wrong = "Something went wrong";
    public static String try_again = "Try Again Later";
    public static String no_records = "No Records Found";
    public static String no_vm_found = "No VM Found";
    public static String password_error = "No active account found with the given credentials";
    public static String all_field_correct = "Please enter all fields correctly";
    public static String check_mobile_email_not_registered = "Please check your mobile number or email address. It might be already Registered.";
    public static String check_state_city_not_correct = "State, City and PinCode Should be correct. It might be wrong.";

    //Regex
    public static String passwordRegex = "^(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,16}$";




}
