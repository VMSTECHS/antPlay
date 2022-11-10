package com.vms.antplay.model.requestModal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForgotPassRequestModal {

    @SerializedName("email")
    @Expose
    private String email;


    public ForgotPassRequestModal(String email) {
        this.email = email;

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String username) {
        this.email = email;
    }


}
