package com.vms.antplay.model.requestModal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateVmRequestModal {

    @SerializedName("vmid")
    @Expose
    private Integer vmId;

    @SerializedName("time_remaining")
    @Expose
    private Integer timeRemaining;

    @SerializedName("is_connected")
    @Expose
    private boolean isConnected = false;


    public UpdateVmRequestModal(Integer vmId, Integer timeRemaining) {
        this.vmId = vmId;
        this.timeRemaining = timeRemaining;
        this.isConnected = false;
    }

    public Integer getVmId() {
        return vmId;
    }

    public void setVmId(Integer vmId) {
        this.vmId = vmId;
    }

    public Integer getTimeRemaining() {
        return timeRemaining;
    }

    public void setTimeRemaining(Integer timeRemaining) {
        this.timeRemaining = timeRemaining;
    }
}
