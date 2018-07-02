package com.example.minh.doan.model_class;

/**
 * Created by LaVanDuc on 5/23/2018.
 */

public class KhachHang {
    private String ma;
    private String ten;
    private String sdt;

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public KhachHang(String ma, String ten, String sdt) {

        this.ma = ma;
        this.ten = ten;
        this.sdt = sdt;
    }
}
