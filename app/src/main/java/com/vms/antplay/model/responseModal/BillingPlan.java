package com.vms.antplay.model.responseModal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BillingPlan {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("plan_type")
    @Expose
    private String planType;
    @SerializedName("gpu")
    @Expose
    private String gpu;
    @SerializedName("cpu")
    @Expose
    private Integer cpu;
    @SerializedName("ram")
    @Expose
    private Integer ram;
    @SerializedName("ssd")
    @Expose
    private Integer ssd;
    @SerializedName("plan_name")
    @Expose
    private String planName;
    @SerializedName("term")
    @Expose
    private Integer term;
    @SerializedName("hour_limit")
    @Expose
    private Integer hourLimit;
    @SerializedName("price")
    @Expose
    private Double price;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
    }

    public String getGpu() {
        return gpu;
    }

    public void setGpu(String gpu) {
        this.gpu = gpu;
    }

    public Integer getCpu() {
        return cpu;
    }

    public void setCpu(Integer cpu) {
        this.cpu = cpu;
    }

    public Integer getRam() {
        return ram;
    }

    public void setRam(Integer ram) {
        this.ram = ram;
    }

    public Integer getSsd() {
        return ssd;
    }

    public void setSsd(Integer ssd) {
        this.ssd = ssd;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public Integer getHourLimit() {
        return hourLimit;
    }

    public void setHourLimit(Integer hourLimit) {
        this.hourLimit = hourLimit;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

}
