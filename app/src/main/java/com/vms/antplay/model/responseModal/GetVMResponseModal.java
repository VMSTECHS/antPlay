package com.vms.antplay.model.responseModal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetVMResponseModal {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }


    public class Datum {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("vmid")
        @Expose
        private Integer vmid;
        @SerializedName("node")
        @Expose
        private Integer node;
        @SerializedName("vmname")
        @Expose
        private String vmname;
        @SerializedName("time_remaining")
        @Expose
        private Integer timeRemaining;
        @SerializedName("is_connected")
        @Expose
        private Boolean isConnected;
        @SerializedName("vmip")
        @Expose
        private String vmip;
        @SerializedName("status")
        @Expose
        private String status;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getVmid() {
            return vmid;
        }

        public void setVmid(Integer vmid) {
            this.vmid = vmid;
        }

        public Integer getNode() {
            return node;
        }

        public void setNode(Integer node) {
            this.node = node;
        }

        public String getVmname() {
            return vmname;
        }

        public void setVmname(String vmname) {
            this.vmname = vmname;
        }

        public Integer getTimeRemaining() {
            return timeRemaining;
        }

        public void setTimeRemaining(Integer timeRemaining) {
            this.timeRemaining = timeRemaining;
        }

        public Boolean getIsConnected() {
            return isConnected;
        }

        public void setIsConnected(Boolean isConnected) {
            this.isConnected = isConnected;
        }

        public String getVmip() {
            return vmip;
        }

        public void setVmip(String vmip) {
            this.vmip = vmip;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }


    }
}
