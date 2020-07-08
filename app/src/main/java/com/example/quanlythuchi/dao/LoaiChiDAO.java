package com.example.quanlythuchi.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.quanlythuchi.database.DBHelper;
import com.example.quanlythuchi.model.LoaiThuChi;

import java.util.ArrayList;
import java.util.List;

public class LoaiChiDAO {
    DBHelper helper;
    public LoaiChiDAO(Context context){
        helper=new DBHelper(context);
    }

    //-----Lấy danh sách loại chi-----
    public ArrayList<LoaiThuChi> getAll(){
        ArrayList<LoaiThuChi>list=new ArrayList<>();
        SQLiteDatabase db=helper.getReadableDatabase();
        String sql="SELECT * FROM LOAITHUCHI WHERE TRANGTHAI='chi'";
        Cursor c=db.rawQuery(sql,null);
        c.moveToFirst();
        while (!c.isAfterLast()){
            String ma=c.getString(0);
            String ten=c.getString(1);
            String tt=c.getString(2);
            list.add(new LoaiThuChi(ma,ten,tt));
            c.moveToNext();
        }
        return list;
    }

    public List<String> getAllMaTen()
    {
        SQLiteDatabase db=helper.getReadableDatabase();
        List<String> list=new ArrayList<>();
        String sql="SELECT MALOAI,TENLOAI FROM LOAITHUCHI WHERE TRANGTHAI='chi'";
        Cursor c=db.rawQuery(sql,null);
        c.moveToFirst();
        while (c.isAfterLast()==false)
        {
            String maloai_tenloai="";
            LoaiThuChi loaiThuChi=new LoaiThuChi();
            loaiThuChi.setMaLoai(c.getString(c.getColumnIndex("MALOAI")));
            loaiThuChi.setTenLoai(c.getString(c.getColumnIndex("TENLOAI")));
            loaiThuChi.setTrangThai("chi");
            maloai_tenloai=loaiThuChi.getMaLoai()+" - "+loaiThuChi.getTenLoai();
            list.add(maloai_tenloai);
            c.moveToNext();
        }
        c.close();
        return list;
    }

    public long themLoaiChi(LoaiThuChi ltc)
    {
        SQLiteDatabase db;
        db=helper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("MALOAI",ltc.getMaLoai());
        values.put("TENLOAI",ltc.getTenLoai());
        values.put("TRANGTHAI","chi");
        return db.insert("LOAITHUCHI",null,values);
    }

    public int suaLoaiChi(LoaiThuChi ltc)
    {
        SQLiteDatabase db;
        db=helper.getReadableDatabase();
        ContentValues values=new ContentValues();
        values.put("MALOAI",ltc.getMaLoai());
        values.put("TENLOAI",ltc.getTenLoai());
        values.put("TRANGTHAI","chi");
        return db.update("LOAITHUCHI",values,"MALOAI=?",new String[]{ltc.getMaLoai()});
    }

    public int xoaLoaiChi(LoaiThuChi ltc)
    {
        SQLiteDatabase db;
        db=helper.getReadableDatabase();
        return db.delete("LOAITHUCHI","MALOAI=?",new String[]{ltc.getMaLoai()});
    }
}

