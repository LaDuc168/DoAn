package com.example.minh.doan.model_class;

import java.io.Serializable;

/**
 * Created by LaVanDuc on 5/22/2018.
 */

public class NhaHang implements Serializable{
    private String manh;
    private String tennh;
    private String mota;
    private String diachi;
    private byte[]hinhanh;

    public String getManh() {
        return manh;
    }

    public void setManh(String manh) {
        this.manh = manh;
    }

    public String getTennh() {
        return tennh;
    }

    public void setTennh(String tennh) {
        this.tennh = tennh;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public byte[] getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(byte[] hinhanh) {
        this.hinhanh = hinhanh;
    }

    public NhaHang(String manh, String tennh, String mota, String diachi, byte[] hinhanh) {

        this.manh = manh;
        this.tennh = tennh;
        this.mota = mota;
        this.diachi = diachi;
        this.hinhanh = hinhanh;
    }
}
