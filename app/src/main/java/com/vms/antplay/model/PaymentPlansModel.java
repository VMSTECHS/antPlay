package com.vms.antplay.model;

public class PaymentPlansModel {
    String planName;
    int amount;
    int durationInHours;
    int validityInDays;
    String description;

    public PaymentPlansModel(String planName, int amount, int durationInHours, int validityInDays, String description) {
        this.planName = planName;
        this.amount = amount;
        this.durationInHours = durationInHours;
        this.validityInDays = validityInDays;
        this.description = description;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getDurationInHours() {
        return durationInHours;
    }

    public void setDurationInHours(int durationInHours) {
        this.durationInHours = durationInHours;
    }

    public int getValidityInDays() {
        return validityInDays;
    }

    public void setValidityInDays(int validityInDays) {
        this.validityInDays = validityInDays;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
