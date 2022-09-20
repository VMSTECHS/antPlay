package com.vms.antplay.model.requestModal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StartPaymentRequestModal {

    @SerializedName("billingplan_id")
    @Expose
    private  String billingPlan_ID;

    public StartPaymentRequestModal(String billingPlan_ID) {
        this.billingPlan_ID = billingPlan_ID;
    }

    public String getBillingPlan_ID() {
        return billingPlan_ID;
    }

    public void setBillingPlan_ID(String billingPlan_ID) {
        this.billingPlan_ID = billingPlan_ID;
    }
}
