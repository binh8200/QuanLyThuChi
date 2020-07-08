
package com.example.quanlythuchi.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.quanlythuchi.R;
import com.example.quanlythuchi.dao.LoaiThuDAO;
import com.example.quanlythuchi.model.LoaiThuChi;

import java.util.ArrayList;

public class LoaiThuAdapter extends BaseAdapter {
    Context context;
    ArrayList<LoaiThuChi> list;
    LoaiThuDAO loaiThuDAO;
    LoaiThuAdapter loaiThuAdapter;
    ListView lvLoaithu;
    EditText edtMaloai,edtTenloai;

    public LoaiThuAdapter(Context context, ArrayList<LoaiThuChi> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.cell_loaithu, null);
            viewHolder.vhTvTenLoai = convertView.findViewById(R.id.tv_lt_tenloai);
            viewHolder.vhIvEdit = convertView.findViewById(R.id.iv_edit_loaithu);
            viewHolder.vhIvDelete = convertView.findViewById(R.id.iv_delete_loaithu);

            lvLoaithu = convertView.findViewById(R.id.lvLoaithu);


            viewHolder.vhIvEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getContext());
                    //-----gán Layout-----
                    LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                    v = inflater.inflate(R.layout.edit_loaithu_layout, null);
                    alertDialog.setView(v);

                    edtMaloai=v.findViewById(R.id.edtMaloai);
                    edtTenloai = v.findViewById(R.id.edtTenloai);

                    //-----Lấy dữ liệu lên EditText-----
                    LoaiThuChi ltc = list.get(position);
                    edtMaloai.setText(ltc.getMaLoai());
                    edtTenloai.setText(ltc.getTenLoai());
                    edtTenloai.requestFocus();

                    //-----Sủa-----
                    alertDialog.setPositiveButton("Sửa", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            boolean error =true;
                            if ((edtTenloai.getText().toString().equals("") || edtTenloai.getText().toString() == null) && (edtMaloai.getText().toString().equals("") || edtMaloai.getText().toString() == null)) {
                                Toast.makeText(context, "Vui lòng nhập đủ thông tin !", Toast.LENGTH_SHORT).show();
                                error = false;
                            }else if (edtTenloai.getText().toString().equals("") || edtTenloai.getText().toString() == null) {
                                Toast.makeText(context, "Vui lòng nhập tên loại !", Toast.LENGTH_SHORT).show();
                                error = false;
                            }else if (edtMaloai.getText().toString().equals("") || edtMaloai.getText().toString() == null) {
                                Toast.makeText(context, "Vui lòng nhập mã loại !", Toast.LENGTH_SHORT).show();
                                error = false;
                            }

                            if (error ==true){
                                LoaiThuChi ltc = list.get(position);
                                loaiThuDAO = new LoaiThuDAO(context);
                                ltc.setMaLoai(edtMaloai.getText().toString());
                                ltc.setTenLoai(edtTenloai.getText().toString());
                                ltc.setTrangThai("thu");
                                if (loaiThuDAO.suaLoaiThu(ltc) == -1) {
                                    Toast.makeText(context, "Sửa không thành công", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                                list.clear();
                                list.addAll(loaiThuDAO.getAll());
                                LoaiThuAdapter.this.notifyDataSetChanged();
                            }

                        }
                    });
                    alertDialog.setNegativeButton("Không sửa", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // dialog.cancel();
                        }
                    });
                    alertDialog.show();
                }
            });

            //-----Xóa-----
            viewHolder.vhIvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snackbar.make(((Activity) context).findViewById(R.id.loaithu_layout), "Bạn có muốn xóa", 2000)
                            .setActionTextColor(Color.RED)
                            .setAction("yes", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    LoaiThuChi ltc = list.get(position);
                                    loaiThuDAO = new LoaiThuDAO(context);
                                    if (loaiThuDAO.xoaLoaiThu(ltc) == -1) {
                                        Toast.makeText(context, "Xóa không thành công", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                    list.clear();
                                    list.addAll(loaiThuDAO.getAll());
                                    LoaiThuAdapter.this.notifyDataSetChanged();
                                }
                            })
                            .show();
                }
            });

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        LoaiThuChi ltc = list.get(position);
        viewHolder.vhTvTenLoai.setText(ltc.getMaLoai() +" - "+ ltc.getTenLoai());
        return convertView;
    }

    class ViewHolder {
        TextView vhTvTenLoai;
        ImageView vhIvEdit, vhIvDelete;
    }
}
