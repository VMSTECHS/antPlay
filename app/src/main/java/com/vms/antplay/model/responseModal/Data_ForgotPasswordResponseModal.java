package com.vms.antplay.model.responseModal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data_ForgotPasswordResponseModal {
    @SerializedName("email_body")
    @Expose
    private String emailBody;
    @SerializedName("to_email")
    @Expose
    private String toEmail;
    @SerializedName("email_subject")
    @Expose
    private String emailSubject;

    public String getEmailBody() {
        return emailBody;
    }

    public void setEmailBody(String emailBody) {
        this.emailBody = emailBody;
    }

    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

    public String getEmailSubject() {
        return emailSubject;
    }

    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }

}
