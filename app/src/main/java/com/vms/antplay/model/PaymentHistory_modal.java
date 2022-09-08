package com.vms.antplay.model;

public class PaymentHistory_modal {

    private String PlanName;
    private String TransactionId;
    private String Amount;
    private String Date;
    private String Status;



    public PaymentHistory_modal(String planName, String transactionId, String amount, String date, String status) {
        PlanName = planName;
        TransactionId = transactionId;
        Amount = amount;
        Date = date;
        Status = status;
    }

    public String getPlanName() {
        return PlanName;
    }

    public void setPlanName(String planName) {
        PlanName = planName;
    }

    public String getTransactionId() {
        return TransactionId;
    }

    public void setTransactionId(String transactionId) {
        TransactionId = transactionId;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
