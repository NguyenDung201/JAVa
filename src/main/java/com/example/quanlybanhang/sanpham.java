package com.example.quanlybanhang;

import java.io.Serializable;

public class sanpham  implements Serializable {
    private String Masp;
    private String Tensp;
    private double Giasp;
    private double soluong;

    public sanpham() {}

    public sanpham(String masp, String tensp, double giasp, double soluong) {
        Masp = masp;
        Tensp = tensp;
        Giasp = giasp;
        this.soluong = soluong;
    }

    public String getMasp() {
        return Masp;
    }

    public void setMasp(String masp) {
        Masp = masp;
    }

    public String getTensp() {
        return Tensp;
    }

    public void setTensp(String tensp) {
        Tensp = tensp;
    }

    public double getGiasp() {
        return Giasp;
    }

    public void setGiasp(double giasp) {
        Giasp = giasp;
    }

    public double getSoluong() {
        return soluong;
    }

    public void setSoluong(double soluong) {
        this.soluong = soluong;
    }
}
