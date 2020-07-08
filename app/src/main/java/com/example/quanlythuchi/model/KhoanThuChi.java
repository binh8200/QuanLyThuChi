package com.example.quanlythuchi.model;

public class KhoanThuChi {
    private int maThuChi;
    private String tenKhoanThuChi;
    private String ngayThuChi;
    private int soTien;
    private String ghiChu;
    private String maLoai;

    public KhoanThuChi() {
    }

    public KhoanThuChi(int maThuChi, String tenKhoanThuChi, String ngayThuChi, int soTien, String ghiChu, String maLoai) {
        this.maThuChi = maThuChi;
        this.tenKhoanThuChi = tenKhoanThuChi;
        this.ngayThuChi = ngayThuChi;
        this.soTien = soTien;
        this.ghiChu = ghiChu;
        this.maLoai = maLoai;
    }

    public int getMaThuChi() {
        return maThuChi;
    }

    public void setMaThuChi(int maThuChi) {
        this.maThuChi = maThuChi;
    }

    public String getTenKhoanThuChi() {
        return tenKhoanThuChi;
    }

    public void setTenKhoanThuChi(String tenKhoanThuChi) {
        this.tenKhoanThuChi = tenKhoanThuChi;
    }

    public String getNgayThuChi() {
        return ngayThuChi;
    }

    public void setNgayThuChi(String ngayThuChi) {
        this.ngayThuChi = ngayThuChi;
    }

    public int getSoTien() {
        return soTien;
    }

    public void setSoTien(int soTien) {
        this.soTien = soTien;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(String maLoai) {
        this.maLoai = maLoai;
    }
}
