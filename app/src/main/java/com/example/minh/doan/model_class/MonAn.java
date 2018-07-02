package com.example.minh.doan.model_class;

import java.io.Serializable;

/**
 * Created by LaVanDuc on 5/22/2018.
 */

public class MonAn implements Serializable {
    private String mamon;
    private String tenmon;
    private String mota;
    private int dongia;
    private byte[]hinhanh;
    private String maloai;

    public String getMamon() {
        return mamon;
    }

    public void setMamon(String mamon) {
        this.mamon = mamon;
    }

    public String getTenmon() {
        return tenmon;
    }

    public void setTenmon(String tenmon) {
        this.tenmon = tenmon;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public int getDongia() {
        return dongia;
    }

    public void setDongia(int dongia) {
        this.dongia = dongia;
    }

    public byte[] getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(byte[] hinhanh) {
        this.hinhanh = hinhanh;
    }

    public String getMaloai() {
        return maloai;
    }

    public void setMaloai(String maloai) {
        this.maloai = maloai;
    }

    public MonAn(String mamon, String tenmon, String mota, int dongia, byte[] hinhanh, String maloai) {

        this.mamon = mamon;
        this.tenmon = tenmon;
        this.mota = mota;
        this.dongia = dongia;
        this.hinhanh = hinhanh;
        this.maloai = maloai;
    }
}
