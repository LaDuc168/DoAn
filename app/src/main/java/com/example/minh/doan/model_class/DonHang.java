package com.example.minh.doan.model_class;

/**
 * Created by LaVanDuc on 5/23/2018.
 */

public class DonHang {
    private String makhach;
    private String manh;
    private String mamon;
    private int soluong;

    public String getMakhach() {
        return makhach;
    }

    public void setMakhach(String makhach) {
        this.makhach = makhach;
    }

    public String getManh() {
        return manh;
    }

    public void setManh(String manh) {
        this.manh = manh;
    }

    public String getMamon() {
        return mamon;
    }

    public void setMamon(String mamon) {
        this.mamon = mamon;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public DonHang(String makhach, String manh, String mamon, int soluong) {

        this.makhach = makhach;
        this.manh = manh;
        this.mamon = mamon;
        this.soluong = soluong;
    }
}
