package com.example.quanlythuchi.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.quanlythuchi.database.DBHelper;
import com.example.quanlythuchi.model.KhoanThuChi;

import java.util.ArrayList;

public class KhoanThuDAO {
    DBHelper helper;

    public KhoanThuDAO(Context context) {
        helper = new DBHelper(context);
    }

    //-----Lấy all danh sách thu-----
    public ArrayList<KhoanThuChi> getAllKhoanThu() {
        ArrayList<KhoanThuChi> list = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "SELECT * FROM KHOANTHUCHI JOIN LOAITHUCHI ON KHOANTHUCHI.MALOAI=LOAITHUCHI.MALOAI WHERE LOAITHUCHI.TRANGTHAI='thu'";
        Cursor c = db.rawQuery(sql,null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            int mathuchi = c.getInt(0);
            String tenkhoanthuchi = c.getString(1);
            String ngaythuchi = c.getString(2);
            Integer sotien = c.getInt(3);
            String ghichu = c.getString(4);
            String maloai=c.getString(5);
            list.add(new KhoanThuChi(mathuchi, tenkhoanthuchi, ngaythuchi,sotien,ghichu,maloai));
            c.moveToNext();
        }
        return list;
    }

    public long themKhoanThu(KhoanThuChi ktc)
    {
        SQLiteDatabase db;
        db=helper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("TENKHOANTHUCHI",ktc.getTenKhoanThuChi());
        values.put("NGAYTHUCHI",ktc.getNgayThuChi());
        values.put("SOTIEN",ktc.getSoTien());
        values.put("GHICHU",ktc.getGhiChu());
        values.put("MALOAI",ktc.getMaLoai());
        return db.insert("KHOANTHUCHI",null,values);
    }

    public int suaKhoanThu(KhoanThuChi ktc)
    {
        SQLiteDatabase db;
        db=helper.getReadableDatabase();
        ContentValues values=new ContentValues();
        values.put("TENKHOANTHUCHI",ktc.getTenKhoanThuChi());
        values.put("NGAYTHUCHI",ktc.getNgayThuChi());
        values.put("SOTIEN",ktc.getSoTien());
        values.put("GHICHU",ktc.getGhiChu());
        values.put("MALOAI",ktc.getMaLoai());
        return db.update("KHOANTHUCHI",values,"MATHUCHI=?",new String[]{String.valueOf(ktc.getMaThuChi())});
    }

    public int xoaKhoanThu(KhoanThuChi ktc)
    {
        SQLiteDatabase db;
        db=helper.getReadableDatabase();
        return db.delete("KHOANTHUCHI","MATHUCHI=?",new String[]{String.valueOf(ktc.getMaThuChi())});
    }

    public ArrayList<KhoanThuChi> sapXepTheoSoTien(){
        ArrayList<KhoanThuChi> list=new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "SELECT * FROM KHOANTHUCHI JOIN LOAITHUCHI ON KHOANTHUCHI.MALOAI=LOAITHUCHI.MALOAI WHERE LOAITHUCHI.TRANGTHAI='thu' ORDER BY SOTIEN";
        Cursor c = db.rawQuery(sql,null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            int mathuchi = c.getInt(0);
            String tenkhoanthuchi = c.getString(1);
            String ngaythuchi = c.getString(2);
            Integer sotien = c.getInt(3);
            String ghichu = c.getString(4);
            String maloai=c.getString(5);
            list.add(new KhoanThuChi(mathuchi, tenkhoanthuchi, ngaythuchi,sotien,ghichu,maloai));
            c.moveToNext();
        }
        return list;
    }

    public ArrayList<KhoanThuChi> sapXepTheoSoTenKhoanThu(){
        ArrayList<KhoanThuChi> list=new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "SELECT * FROM KHOANTHUCHI JOIN LOAITHUCHI ON KHOANTHUCHI.MALOAI=LOAITHUCHI.MALOAI WHERE LOAITHUCHI.TRANGTHAI='thu' ORDER BY TENKHOANTHU";
        Cursor c = db.rawQuery(sql,null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            int mathuchi = c.getInt(0);
            String tenkhoanthuchi = c.getString(1);
            String ngaythuchi = c.getString(2);
            Integer sotien = c.getInt(3);
            String ghichu = c.getString(4);
            String maloai=c.getString(5);
            list.add(new KhoanThuChi(mathuchi, tenkhoanthuchi, ngaythuchi,sotien,ghichu,maloai));
            c.moveToNext();
        }
        return list;
    }

    public ArrayList<KhoanThuChi> khoanThuNhieuNhat(){
        ArrayList<KhoanThuChi> list=new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "SELECT * FROM KHOANTHUCHI JOIN LOAITHUCHI ON KHOANTHUCHI.MALOAI=LOAITHUCHI.MALOAI WHERE LOAITHUCHI.TRANGTHAI='thu' ORDER BY SOTIEN DESC LIMIT 1";
        Cursor c = db.rawQuery(sql,null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            int mathuchi = c.getInt(0);
            String tenkhoanthuchi = c.getString(1);
            String ngaythuchi = c.getString(2);
            Integer sotien = c.getInt(3);
            String ghichu = c.getString(4);
            String maloai=c.getString(5);
            list.add(new KhoanThuChi(mathuchi, tenkhoanthuchi, ngaythuchi,sotien,ghichu,maloai));
            c.moveToNext();
        }
        return list;
    }

    public int tongSoTienThu(){
        int tongSoTien=0;
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "SELECT SOTIEN FROM KHOANTHUCHI JOIN LOAITHUCHI ON KHOANTHUCHI.MALOAI=LOAITHUCHI.MALOAI WHERE LOAITHUCHI.TRANGTHAI='thu'";
        Cursor c=db.rawQuery(sql,null);
        c.moveToFirst();
        while (c.isAfterLast()==false)
        {
            KhoanThuChi ktc=new KhoanThuChi();
            ktc.setSoTien(Integer.parseInt(c.getString(c.getColumnIndex("SOTIEN"))));
            tongSoTien+=ktc.getSoTien();
            c.moveToNext();
        }
        c.close();
        return tongSoTien;
    }
}
