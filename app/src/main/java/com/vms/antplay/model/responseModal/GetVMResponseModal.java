package com.vms.antplay.model.responseModal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetVMResponseModal {
    @SerializedName("vmid")
    @Expose
    private Integer vmid;
    @SerializedName("vmname")
    @Expose
    private String vmname;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("vmip")
    @Expose
    private String vmip;
    @SerializedName("time_remaining")
    @Expose
    private Integer timeRemaining;

    public Integer getVmid() {
        return vmid;
    }

    public void setVmid(Integer vmid) {
        this.vmid = vmid;
    }

    public String getVmname() {
        return vmname;
    }

    public void setVmname(String vmname) {
        this.vmname = vmname;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVmip() {
        return vmip;
    }

    public void setVmip(String vmip) {
        this.vmip = vmip;
    }

    public Integer getTimeRemaining() {
        return timeRemaining;
    }

    public void setTimeRemaining(Integer timeRemaining) {
        this.timeRemaining = timeRemaining;
    }
}
