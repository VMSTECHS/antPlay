package com.vms.antplay.model.responseModal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForgotPassResponseModal {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("data")
    @Expose
    private Data_ForgotPasswordResponseModal data;
    @SerializedName("message")
    @Expose
    private String message;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Data_ForgotPasswordResponseModal getData() {
        return data;
    }

    public void setData(Data_ForgotPasswordResponseModal data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
