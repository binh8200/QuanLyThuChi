package com.example.quanlythuchi.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public final static String DBNAME = "QUANLYTHUCHI";
    public final static int DBVERSION = 1;

    public DBHelper(Context context) {
        super(context, DBNAME, null, DBVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql;
        sql = "CREATE TABLE LOAITHUCHI(" +
                "MALOAI TEXT PRIMARY KEY," +
                "TENLOAI TEXT," +
                "TRANGTHAI TEXT)";
        db.execSQL(sql);

        sql = "CREATE TABLE KHOANTHUCHI(" +
                "MATHUCHI INTEGER PRIMARY KEY AUTOINCREMENT," +
                "TENKHOANTHUCHI TEXT," +
                "NGAYTHUCHI DATE," +
                "SOTIEN INTEGER," +
                "GHICHU TEXT," +
                "MALOAI Text  REFERENCES LOAITHUCHI(MALOAI))";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String lenhsql3 = "DROP TABLE IF EXISTS LOAITHUCHI";
        db.execSQL(lenhsql3);
        String lenhsql4 = "DROP TABLE IF EXISTS KHOANTHUCHI";
        db.execSQL(lenhsql4);
        onCreate(db);
    }
}
