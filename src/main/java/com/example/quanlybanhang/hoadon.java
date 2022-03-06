package com.example.quanlybanhang;

import java.io.Serial;
import java.io.Serializable;

public class hoadon implements Serializable {
    private String Mahd;
    private String Tensp;
    private double ChietKhau;
    private double tongtien;

    public hoadon(){}

    public hoadon(String mahd, String tensp, double chietKhau, double tongtien) {
        Mahd = mahd;
        Tensp = tensp;
        ChietKhau = chietKhau;
        this.tongtien = tongtien;
    }

    public String getMahd() {
        return Mahd;
    }

    public void setMahd(String mahd) {
        Mahd = mahd;
    }

    public String getTensp() {
        return Tensp;
    }

    public void setTensp(String tensp) {
        Tensp = tensp;
    }

    public double getChietKhau() {
        return ChietKhau;
    }

    public void setChietKhau(double chietKhau) {
        ChietKhau = chietKhau;
    }

    public double getTongtien() {
        return tongtien;
    }

    public void setTongtien(double tongtien) {
        this.tongtien = tongtien;
    }
}

