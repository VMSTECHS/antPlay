package com.vms.antplay.model;

public class Computer_availableModal {

    private String ComName;
    private String CompDesc;
    private int Image;

    public Computer_availableModal(String comName, String compDesc, int image) {
        ComName = comName;
        CompDesc = compDesc;
        Image = image;
    }

    public String getComName() {
        return ComName;
    }

    public void setComName(String comName) {
        ComName = comName;
    }

    public String getCompDesc() {
        return CompDesc;
    }

    public void setCompDesc(String compDesc) {
        CompDesc = compDesc;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }
}
