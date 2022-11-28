package com.vms.antplay.model.requestModal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShutDownVMRequestModal {

    @SerializedName("vmid")
    @Expose
    private String vmid;


    public ShutDownVMRequestModal(String vmid) {
        this.vmid = vmid;

    }

    public String getVmid() {
        return vmid;
    }

    public void setVmid(String vmid) {
        this.vmid = vmid;
    }
}
