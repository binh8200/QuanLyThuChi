package com.example.quanlythuchi.adapter;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.quanlythuchi.R;
import com.example.quanlythuchi.dao.KhoanThuDAO;
import com.example.quanlythuchi.dao.LoaiThuDAO;
import com.example.quanlythuchi.model.KhoanThuChi;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class KhoanThuAdapter extends BaseAdapter {
    Context context;
    ArrayList<KhoanThuChi> list;
    LoaiThuDAO loaiThuDAO;
    KhoanThuDAO khoanThuDAO;
    KhoanThuAdapter khoanThuAdapter;
    ListView lvkhoanthu;
    EditText edtTenKhoanThu;
    TextView edtNgayThu;
    EditText edtSoTien;
    EditText edtGhiChu;
    Spinner spLoaiThu;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    static final String TAG = "QLSV";


    public KhoanThuAdapter(Context context, ArrayList<KhoanThuChi> list) {
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
            convertView = inflater.inflate(R.layout.khoanthuchi_layout, null);
            viewHolder.vhTvTenKhoanTC = convertView.findViewById(R.id.tvTenKhoanTC);
            viewHolder.vhNgayTC = convertView.findViewById(R.id.tvNgayTC);
            viewHolder.vhSoTien = convertView.findViewById(R.id.tvSoTien);
            viewHolder.vhGhiChu = convertView.findViewById(R.id.tvGhiChu);
            viewHolder.vhMaLoai=convertView.findViewById(R.id.tvMaLoai);
            viewHolder.vhIvEdit = convertView.findViewById(R.id.iv_edit_khoantc);
            viewHolder.vhIvDelete = convertView.findViewById(R.id.iv_delete_khoantc);

            lvkhoanthu = convertView.findViewById(R.id.lvkhoanthu);

            viewHolder.vhIvEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getContext());
                    //-----gán layout-----
                    LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                    v = inflater.inflate(R.layout.edit_khoanthu_layout, null);
                    alertDialog.setView(v);

                    //-----anhxa-----
                    edtTenKhoanThu = v.findViewById(R.id.edtTenKhoanThu);
                    edtNgayThu = v.findViewById(R.id.edtNgayThu);
                    edtSoTien = v.findViewById(R.id.edtSoTien);
                    edtGhiChu = v.findViewById(R.id.edtGhiChu);
                    spLoaiThu=v.findViewById(R.id.spLoaiThu);

                    //-----DatePicker-----
                    mDateSetListener = new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            month = month + 1;
                            Log.d(TAG, "onDateSet:mm/dd/yyyy: " + dayOfMonth + "/" + month + "/" + year);
                            String date = "   " + dayOfMonth + "/" + month + "/" + year;
                            edtNgayThu.setText(date);
                        }
                    };
                    edtNgayThu.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Calendar calendar = Calendar.getInstance();
                            int year = calendar.get(Calendar.YEAR);
                            int month = calendar.get(Calendar.MONTH);
                            int day = calendar.get(Calendar.DAY_OF_MONTH);
                            DatePickerDialog dialog = new DatePickerDialog(
                                    context, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                                    mDateSetListener,
                                    year, month, day
                            );
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            dialog.show();
                        }
                    });

                    //-----Spinner-----
                    loaiThuDAO = new LoaiThuDAO(context);
                    List<String> list2=loaiThuDAO.getAllMaTen();
                    ArrayAdapter<String> a = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, list2) {
                        @Override
                        public View getView(int position, View convertView, ViewGroup parent) {
                            View view = super.getView(position, convertView, parent);
                            TextView textView = view.findViewById(android.R.id.text1);
                            if (position % 2 == 0)
                                textView.setTextColor(Color.BLACK);
                            else
                                textView.setTextColor(Color.BLACK);
                            return view;
                        }
                    };
                    spLoaiThu.setAdapter(a);

                    //-----Lấy dữ liệu lên Edittext-----
                    KhoanThuChi ktc = list.get(position);
                    edtTenKhoanThu.setText(ktc.getTenKhoanThuChi());
                    edtNgayThu.setText(ktc.getNgayThuChi());
                    edtSoTien.setText(ktc.getSoTien() + "");
                    edtGhiChu.setText(ktc.getGhiChu());

                    //-----Sủa-----
                    alertDialog.setPositiveButton("Sửa", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            boolean erro = true;
                            if((edtTenKhoanThu.getText().toString().equals("") || edtTenKhoanThu.getText().toString() == null)
                                    && (edtNgayThu.getText().toString().equals("") || edtNgayThu.getText().toString() == null)
                                    && (edtSoTien.getText().toString().equals("") || edtSoTien.getText().toString() == null)
                                    && (edtGhiChu.getText().toString().equals("") || edtGhiChu.getText().toString() == null)) {
                                Toast.makeText(context, "Vui lòng nhập đủ thông tin !", Toast.LENGTH_SHORT).show();
                                erro = false;
                            } else if (edtTenKhoanThu.getText().toString().equals("") || edtTenKhoanThu.getText().toString() == null){
                                Toast.makeText(context, "Vui lòng nhập tên khoản thu !", Toast.LENGTH_SHORT).show();
                                erro = false;
                            }else if (edtNgayThu.getText().toString().equals("") || edtNgayThu.getText().toString() == null){
                                Toast.makeText(context, "Vui lòng chọn ngày thu !", Toast.LENGTH_SHORT).show();
                                erro = false;
                            }else if (edtSoTien.getText().toString().equals("") || edtSoTien.getText().toString() == null){
                                Toast.makeText(context, "Vui lòng nhập số tiền !", Toast.LENGTH_SHORT).show();
                                erro = false;
                            }else if (edtSoTien.getText().toString() != "" || edtSoTien.getText().toString() != null) {
                                try {
                                    Integer.parseInt(edtSoTien.getText().toString());
                                } catch (NumberFormatException e) {
                                    Toast.makeText(context, "Số tiền phải là số !", Toast.LENGTH_SHORT).show();
                                    erro = false;
                                }
                            }

                            if (erro == true){
                                KhoanThuChi ktc = list.get(position);
                                khoanThuDAO = new KhoanThuDAO(context);
                                ktc.setMaLoai(spLoaiThu.getSelectedItem().toString().substring(0,spLoaiThu.getSelectedItem().toString().indexOf(" - ")));
                                ktc.setTenKhoanThuChi(edtTenKhoanThu.getText().toString());
                                ktc.setNgayThuChi(edtNgayThu.getText().toString());
                                ktc.setSoTien(Integer.parseInt(edtSoTien.getText().toString()));
                                ktc.setGhiChu(edtGhiChu.getText().toString());
                                if (khoanThuDAO.suaKhoanThu(ktc) == -1) {
                                    Toast.makeText(context, "Sửa không thành công", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                                list.clear();
                                list.addAll(khoanThuDAO.getAllKhoanThu());
                                KhoanThuAdapter.this.notifyDataSetChanged();
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
                    Snackbar.make(((Activity) context).findViewById(R.id.khoanthu_layout), "Bạn có muốn xóa", 2000)
                            .setActionTextColor(Color.RED)
                            .setAction("yes", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    KhoanThuChi ktc = list.get(position);
                                    khoanThuDAO = new KhoanThuDAO(context);
                                    if (khoanThuDAO.xoaKhoanThu(ktc) == -1) {
                                        Toast.makeText(context, "Xóa không thành công", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                    list.clear();
                                    list.addAll(khoanThuDAO.getAllKhoanThu());
                                    KhoanThuAdapter.this.notifyDataSetChanged();
                                }
                            })
                            .show();
                }
            });

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        KhoanThuChi ktc = list.get(position);
        viewHolder.vhTvTenKhoanTC.setText(ktc.getTenKhoanThuChi().toString());
        viewHolder.vhNgayTC.setText(ktc.getNgayThuChi().toString());
        viewHolder.vhSoTien.setText(ktc.getSoTien() + "");
        viewHolder.vhGhiChu.setText(ktc.getGhiChu().toString());
        viewHolder.vhMaLoai.setText(ktc.getMaLoai().toString());
        return convertView;
    }

    class ViewHolder {
        TextView vhTvTenKhoanTC;
        TextView vhNgayTC;
        TextView vhSoTien;
        TextView vhGhiChu;
        TextView vhMaLoai;
        ImageView vhIvEdit, vhIvDelete;
    }
}
