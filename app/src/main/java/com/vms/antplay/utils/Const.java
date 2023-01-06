package com.vms.antplay.utils;

import java.util.ArrayList;
import java.util.List;

public class Const {
    public static String razorPayMerchantId = "KEVxW3g3K0GfQr";

    public static String ACCESS_TOKEN="access_token";
    public static String IS_LOGGED_IN="logged_in";
    public static String FULL_NAME="full_name";
    public static String USER_EMAIL="email";
    public static String PHONE="phone_number";
    public static String ADDRESS="address";
    public static String STATE="state";
    public static String CITY="city";
    public static String USER_NAME="username";
    public static String PIN_CODE="pincode";
    public static String USER_EXPIRY_DATE="userExpiryDate";
    public static String BASIC_SKU="antplay_30_hours";
    public static String PREMIUM_SKU="antplay_250_hours";
    public static String BASIC_PLAN_1="basic-plan-1";
    public static String SUBSCRIPTION = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjm7sjWPMIHx/i6fchZh+zftM8u3spnkb887DiEsGIh9g1d0ag6jr801i1Njfxf0iCDvNKrUeg9Bt3CSGP89YL7m3ToWtkAosM8lVetjYb9NBGazFmqZn4R2dq5WwSZRef3uMZTykJaakmV9EqBOFJkidupMr8+IHsfyDzMVG/cFnUZQA9z0e+mNi6ZDP56+ZzmBQgrlOkcmRiNBHtt2WFQjvBNBlkZWeq10KMc3iLR8t+pZapbhloVBivPAXeevZWhWq+dfIwRDlY8rjRB27+Cqq6js5aGUbgvo74fuBlC+kED7zz1uT3QdGrIBNMOpFQkMmcwwntEhj1ktq+u+fmwIDAQAB";
    public static final String LIVE_BASE_URL = "https://api.antplay.tech/api/";
    public static final Integer ERROR_CODE_500 = 500;
    public static final String ERROR_CODE_500_SERVER_ERROR = "500 Internal Server Error";
    public static final Integer ERROR_CODE_400 = 400;
    public static final Integer ERROR_CODE_404 = 404;
    public static final Integer SUCCESS_CODE_200 = 200;
    public static final String city_should_not_empty = "City should not be empty";
    public static final String address_should_not_empty = "Address should not be empty";
    public static final String enter_valid_picCode = "Enter 6 digit Valid PinCode ";
    public static final String enter_valid_data = "Enter Valid Data ";
    public static final String profile_updated_success = "Profile Updated Successfully ";


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
    public static String already_have_plan = "You already have an Active Subscription, To Subscribe to a New Plan, Please Cancel your Active Subscription and Try Again";

    public static String NOT_REGISTERED_EMAIL="Entered e-mail is not registered with us, Please provide a valid e-mail";
    /*****
     * Regex
     * ****/
    //public static String passwordRegex = "^(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,16}$";
    public static String passwordRegex = "^(?=.*\\d)(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z]).{8,}$";
    public static String pinCodeRegex = "^[1-9][0-9]{5}$";


    public static String phoneRegex = "^[1-9][0-9]{9,10}$";

    /*****
     * Intent Constants
     * ****/
    public static String REDIRECT_URL="redirectURL";
    public static String COUNTDOWN_INTENT="countdown";
    public static final String COUNTDOWN_BR = "com.vms.antplay.countdown_timer";

    public static String COUNTDOWN_TIMER_INTENT="countdownTimer";
    public static String COUNTDOWN_TIMER_RUNNING="countdownTimerRunning";
    public static String COUNTDOWN_TIMER_FINISHED="countdownTimerFinished";


    /*****
     * Intent Values
     * ****/
    public static String TERMS_AND_CONDITION_URL="https://antplay.tech/termsAndConditionsForApp";
    public static String PRIVACY_POLICY_URL="https://antplay.tech/privacyPolicyForApp";
    public static String ABOUT_US_URL="https://antplay.tech/aboutUsForApp";
    public static String FAQ_URL="https://antplay.tech/faqForApp";


    /*****
     * Preference Constants
     * ****/
    public static String REMAINING_TIME="remainingTime";
    public static String VM_ID="vmId";


    /*****
     * Timer Constants
     * ****/
    public static long MAX_TIME = 432000000; // 5x24x60x60x1000 = 432,000,000 milliseconds = 5 days
    public static int MAX_PROGRESS = 30;

    /*****
     * Notification Message
     * ****/
    public static String NOTIFICATION_TITLE = "Cloud Machine Running"; // 5x24x60x60x1000 = 432,000,000 milliseconds = 5 days
    public static String NOTIFICATION_MESSAGE = "Cloud machine is running, don't forgot to disconnect your machine if you are not using it";

}
