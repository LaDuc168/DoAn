package com.example.minh.doan.model_class;

/**
 * Created by LaVanDuc on 5/22/2018.
 */

public class NhaHangMonAn {
    private String mannh;
    private String maloai;

    public String getMannh() {
        return mannh;
    }

    public void setMannh(String mannh) {
        this.mannh = mannh;
    }

    public String getMaloai() {
        return maloai;
    }

    public void setMaloai(String maloai) {
        this.maloai = maloai;
    }

    public NhaHangMonAn(String mannh, String maloai) {

        this.mannh = mannh;
        this.maloai = maloai;
    }
}
