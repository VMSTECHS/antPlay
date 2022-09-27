package com.vms.antplay.model.requestModal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterRequestModal {
    @SerializedName("first_name")
    @Expose
    public String firstName;
    @SerializedName("last_name")
    @Expose
    public String lastName;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("phone_number")
    @Expose
    public String phoneNumber;
    @SerializedName("last_login")
    @Expose
    public String lastLogin;
    @SerializedName("is_newuser")
    @Expose
    public String isNewuser;
    @SerializedName("is_subscribed")
    @Expose
    public String isSubscribed;
    @SerializedName("address")
    @Expose
    public String address;
    @SerializedName("age")
    @Expose
    public String age;
    @SerializedName("state")
    @Expose
    public String state;
    @SerializedName("middle_name")
    @Expose
    public String middleName;
    @SerializedName("password")
    @Expose
    public String password;
    @SerializedName("city")
    @Expose
    public String city;
    @SerializedName("pincode")
    @Expose
    public String pincode;

    public RegisterRequestModal(String firstName, String lastName, String email,
                                String phoneNumber,
                                String lastLogin,
                                String isNewuser,
                                String isSubscribed,
                                String address,
                                String age,
                                String state,
                                String middleName,
                                String password,
                                String city, String pincode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.lastLogin = lastLogin;
        this.isNewuser = isNewuser;
        this.isSubscribed = isSubscribed;
        this.address = address;
        this.age = age;
        this.state = state;
        this.middleName = middleName;
        this.password = password;
        this.city = city;
        this.pincode = pincode;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getIsNewuser() {
        return isNewuser;
    }

    public void setIsNewuser(String isNewuser) {
        this.isNewuser = isNewuser;
    }

    public String getIsSubscribed() {
        return isSubscribed;
    }

    public void setIsSubscribed(String isSubscribed) {
        this.isSubscribed = isSubscribed;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

}
