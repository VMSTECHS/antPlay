package com.vms.antplay.model.responseModal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StartPaymentResponseModal {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("transaction_id")
    @Expose
    private String transactionId;
    @SerializedName("transaction_date")
    @Expose
    private String transactionDate;
    @SerializedName("isPaid")
    @Expose
    private String isPaid;
    @SerializedName("payment_url")
    @Expose
    private String paymentUrl;
    @SerializedName("subscriptions")
    @Expose
    private String subscriptions;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(String isPaid) {
        this.isPaid = isPaid;
    }

    public String getPaymentUrl() {
        return paymentUrl;
    }

    public void setPaymentUrl(String paymentUrl) {
        this.paymentUrl = paymentUrl;
    }

    public String getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(String subscriptions) {
        this.subscriptions = subscriptions;
    }

}
