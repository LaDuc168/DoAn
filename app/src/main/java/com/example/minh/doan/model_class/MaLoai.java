package com.example.minh.doan.model_class;

/**
 * Created by LaVanDuc on 5/22/2018.
 */

public class MaLoai {
    private String maloai;
    private String tenloai;

    public String getMaloai() {
        return maloai;
    }

    public void setMaloai(String maloai) {
        this.maloai = maloai;
    }

    public String getTenloai() {
        return tenloai;
    }

    public void setTenloai(String tenloai) {
        this.tenloai = tenloai;
    }

    public MaLoai(String maloai, String tenloai) {

        this.maloai = maloai;
        this.tenloai = tenloai;
    }
}
