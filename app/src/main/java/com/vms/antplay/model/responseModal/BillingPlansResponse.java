
package com.vms.antplay.model.responseModal;


import java.util.ArrayList;

public class BillingPlansResponse {
private ArrayList<GetBillingPlanResponseModal> billingPlanList = new ArrayList<>();

    public ArrayList<GetBillingPlanResponseModal> getBillingPlanList() {
        return billingPlanList;
    }

    public void setBillingPlanList(ArrayList<GetBillingPlanResponseModal> billingPlanList) {
        this.billingPlanList = billingPlanList;
    }
}
